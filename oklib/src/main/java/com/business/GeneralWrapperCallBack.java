package com.business;

import com.business.parse.Parser;
import com.business.parse.Processor;
import com.business.parse.Wrapper;
import com.oklib.callback.BaseCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: BaiCQ
 * @ClassName: GeneralProcessCallBack
 * @Description: 实现基本数据处理回调
 * IBusiCallback与CallBack的转换器
 */
public abstract class GeneralWrapperCallBack<R, E> extends BaseCallBack<Object[]> {
    protected final String TAG = this.getClass().getSimpleName();
    private Parser parser;
    private Processor<R, E> processor;
    private IBusiCallback<R, E> iBusiCallback;
    private Wrapper wrapper;
    private ILoadTag iLoadTag;

    public IBusiCallback<R, E> getiBusiCallback() {
        return iBusiCallback;
    }

    /**
     * @param iLoadTag      loadTag
     * @param parser        解析器
     * @param iBusiCallback 业务回调
     */
    public GeneralWrapperCallBack(ILoadTag iLoadTag, Parser parser, IBusiCallback iBusiCallback) {
        this.iBusiCallback = iBusiCallback;
        this.parser = null == parser ? OkHelper.get().getParser() : parser;
        this.iLoadTag = iLoadTag;
        this.processor = onSetProcessor();
    }

    @Override
    public void onBefore(Request.Builder builder) {
        wrapper = null;
        if (null != iLoadTag) iLoadTag.show();
    }

    private void onResponseHeader(Response response) {
        String[] headerKeys = null == parser ? null : parser.headerKeys();
        int len = null == headerKeys ? 0 : headerKeys.length;
        Map<String, String> headers = new HashMap<>();
        for (int i = 0; i < len; i++) {
            String key = headerKeys[i];
            headers.put(key, response.header(key));
        }
        onParseHeaders(headers);
    }

    /**
     * haead回调
     *
     * @param headers
     */
    protected void onParseHeaders(Map<String, String> headers) {
    }

    @Override
    public final Object[] onParse(Response response) throws Exception {
        onResponseHeader(response);
        String res = response.body().string();
        int httpCode = response.code();
        OkUtil.e(TAG, "httpCode = " + httpCode + " res = " + res);
        if (null == res || "".equals(res)) {
            OkUtil.e(TAG, "未知错误：Response No Body ！");
            return null;
        }
        if (null == parser) {
            OkUtil.e(TAG, "No Set Parser ！");
            return null;
        }
        wrapper = parser.parse(httpCode, res);
        OkUtil.e(TAG, "wrapper = " + OkUtil.obj2Json(wrapper));
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
    public void onResponse(Object[] result) {
        if (null != result && 2 == result.length) {
            if (null != iBusiCallback) iBusiCallback.onSuccess((R) result[0], (E) result[1]);
        } else {
            if (null != iBusiCallback) iBusiCallback.onError(-1, "No Result！");
        }
    }

    @Override
    public void onError(Exception e) {
        if (null != iBusiCallback) iBusiCallback.onError(-1, e.getLocalizedMessage());
    }

    @Override
    public void onAfter() {
        if (null != iLoadTag) iLoadTag.dismiss();
        if (null != iBusiCallback) {
            if (null == wrapper) {
                iBusiCallback.onAfter(-1, "");
            } else {
                iBusiCallback.onAfter(wrapper.getCode(), wrapper.getMessage());
            }
        }
    }

    /**
     * 强制获取数据处理器
     *
     * @return
     */
    public abstract Processor<R, E> onSetProcessor();
}
