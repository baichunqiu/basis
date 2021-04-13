package com.business;

import com.business.interfaces.IParse;
import com.business.interfaces.IResult;
import com.business.interfaces.IWrap;
import com.business.interfaces.IProcess;
import com.oklib.OCallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @param <R>  Integer 或 List<T>
 * @param <E>  String  或 Boolen
 * @param <T>  Void    或 实体
 * @param <RE> IResult<R,E>的类型
 *             注意：BaseProcessor 仅支持 StatueResult的类型 和 ObjResult<List<T>>的结果
 */
public class GeneralWrapperCallBack<R, E, T, RE extends IResult<R, E>> extends OCallBack<RE> {
    protected final String TAG = this.getClass().getSimpleName();
    private IParse parser;
    private IProcess<R, E, T, RE> processor;
    public BsiCallback<R, E, T, RE> bsiCallback;
    private IWrap wrapper;
    private ILoadTag iLoadTag;

    /**
     * @param iLoadTag    loadTag
     * @param parser      解析器
     * @param bsiCallback 业务回调
     */
    public GeneralWrapperCallBack(ILoadTag iLoadTag, IParse parser, BsiCallback bsiCallback) {
        this.bsiCallback = bsiCallback;
        this.iLoadTag = iLoadTag;
        this.processor = OkHelper.get().getProcessor();
        this.parser = null == parser ? OkHelper.get().getParser() : parser;
    }

    /**
     * requestAgain修改callback使用
     */
    public void setBsiCallback(BsiCallback<R, E, T, RE> bsiCallback) {
        this.bsiCallback = bsiCallback;
    }

    @Override
    public void onBefore(Request.Builder builder) {
        wrapper = null;
        if (null != iLoadTag) iLoadTag.show();
        if (null != OkHelper.get().getHeadCacher()) { //添加header
            Map<String, String> hs = OkHelper.get().getHeadCacher().onAddHeader();
            if (null == hs || hs.isEmpty()) return;
            for (Map.Entry<String, String> en : hs.entrySet()) {
                builder.addHeader(en.getKey(), en.getValue());
            }
        }
    }

    @Override
    public final RE onParse(Response response) throws Exception {
        if (null != OkHelper.get().getHeadCacher()) {//缓存header
            OkHelper.get().getHeadCacher().onCacheHeader(response.headers());
        }
        String res = response.body().string();
        int httpCode = response.code();
        OkUtil.e(TAG, "url = " + get().url + "\nhttpCode = " + httpCode + " res = " + res);
        if (null == res || "".equals(res)) {
            OkUtil.e(TAG, "未知错误：Response No Body ！");
            return null;
        }
        if (null == parser) {
            OkUtil.e(TAG, "No Set Parser ！");
            return null;
        }
        wrapper = parser.parse(httpCode, res);
        if (null == processor) {
            OkUtil.e(TAG, "No Set Processor ！");
            return null;
        }
        if (!parser.ok(wrapper.getCode())) {
            if (null != processor) processor.process(wrapper.getCode(), get());
            return null;
        }
        return processor.processResult(wrapper, bsiCallback.onGetType());
    }

    @Override
    public void onResult(RE result) {
        if (null != result) {
            if (null != bsiCallback) bsiCallback.onSuccess(result);
        } else {
            if (null != bsiCallback) bsiCallback.onError(-1, "No Result！");
        }
    }

    @Override
    public void onError(Exception e) {
        if (null != bsiCallback) bsiCallback.onError(-1, e.getLocalizedMessage());
    }

    @Override
    public void onAfter() {
        if (null != iLoadTag) iLoadTag.dismiss();
        if (null != bsiCallback) {
            if (null == wrapper) {
                bsiCallback.onAfter(-1, "");
            } else {
                bsiCallback.onAfter(wrapper.getCode(), wrapper.getMessage());
            }
        }
    }
}
