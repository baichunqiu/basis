package com.business;

import com.business.parse.IParse;
import com.business.parse.IWrap;
import com.business.parse.IProcess;
import com.oklib.OCallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: BaiCQ
 * @ClassName: GeneralProcessCallBack
 * @Description: 实现基本数据处理回调
 * IBusiCallback与CallBack的转换器
 */
public abstract class GeneralWrapperCallBack<R, E> extends OCallBack<Object[]> {
    protected final String TAG = this.getClass().getSimpleName();
    private IParse parser;
    private IProcess<R, E> processor;
    public IBusiCallback<R, E> callback;
    private IWrap wrapper;
    private ILoadTag iLoadTag;

    /**
     * @param iLoadTag      loadTag
     * @param parser        解析器
     * @param iBusiCallback 业务回调
     */
    public GeneralWrapperCallBack(ILoadTag iLoadTag, IParse parser, IBusiCallback iBusiCallback) {
        this.callback = iBusiCallback;
        this.iLoadTag = iLoadTag;
        this.processor = onSetProcessor();
        this.parser = null == parser ? OkHelper.get().getDefaultParser() : parser;
    }

    @Override
    public void onBefore(Request.Builder builder) {
        wrapper = null;
        if (null != iLoadTag) iLoadTag.show();
        if (null != OkHelper.get().getCacheHeader()) { //添加header
            Map<String, String> hs = OkHelper.get().getCacheHeader().onAddHeader();
            if (null == hs || hs.isEmpty()) return;
            for (Map.Entry<String, String> en : hs.entrySet()) {
                builder.addHeader(en.getKey(), en.getValue());
            }
        }
    }

    @Override
    public final Object[] onParse(Response response) throws Exception {
        if (null != OkHelper.get().getCacheHeader()) {//缓存header
            OkHelper.get().getCacheHeader().onCacheHeader(response.headers());
        }
        String res = response.body().string();
        int httpCode = response.code();
        OkUtil.e(TAG, "url = " + get().url+"\nhttpCode = " + httpCode + " res = " + res);
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
        return new Object[]{processor.parseResult(wrapper),
                processor.parseExtra(wrapper)};
    }

    @Override
    public void onResult(Object[] result) {
        if (null != result && 2 == result.length) {
            if (null != callback) callback.onSuccess((R) result[0], (E) result[1]);
        } else {
            if (null != callback) callback.onError(-1, "No Result！");
        }
    }

    @Override
    public void onError(Exception e) {
        if (null != callback) callback.onError(-1, e.getLocalizedMessage());
    }

    @Override
    public void onAfter() {
        if (null != iLoadTag) iLoadTag.dismiss();
        if (null != callback) {
            if (null == wrapper) {
                callback.onAfter(-1, "");
            } else {
                callback.onAfter(wrapper.getCode(), wrapper.getMessage());
            }
        }
    }

    /**
     * 强制获取数据处理器
     *
     * @return
     */
    public abstract IProcess<R, E> onSetProcessor();
}
