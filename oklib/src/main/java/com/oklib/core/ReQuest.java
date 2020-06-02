package com.oklib.core;

import com.business.OkHelper;
import com.kit.Logger;
import com.oklib.callback.CallBack;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class ReQuest<T> implements CallBack<T, Object> {
    private final static String TAG = "ReQuest";
    public String url;
    public Map<String, Object> param;
    public CallBack<T, ReQuest<T>> callBack;
    private Method method;

    private ReQuest() {
    }

    public Map<String, Object> param() {
        return param;
    }

    public ReQuest request() {
        if (OkHelper.debug()) {
            logParams(url, method.name(), param);
        }
        if (Method.post == method) {
            Core.core().post(url, param, this);
        } else if (Method.get == method) {
            Core.core().get(url, param, this);
        }
        return this;
    }

    /**
     * 输出参数
     *
     * @param url
     * @param method
     * @param params
     */
    private static void logParams(String url, String method, Map<String, Object> params) {
        Logger.e(TAG, " : ------------- start --------------------");
        Logger.e(TAG, " : url = " + url + "  method = " + method);
        int size = null == params ? 0 : params.size();
        if (size > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Logger.e(TAG, " : " + entry.getKey() + " = " + entry.getValue());
            }
        } else {
            Logger.e(TAG, " : 参数【无】");
        }
        Logger.e(TAG, " : -------------   end --------------------");
    }

    @Override
    public String toString() {
        return "ReQuest{" +
                "url='" + url + '\'' +
                ", param=" + param +
                ", callBack=" + callBack +
                ", method='" + method + '\'' +
                '}';
    }

    @Override
    public void onBefore(Request.Builder builder) {
        if (null != callBack) callBack.onBefore(builder);
    }

    @Override
    public T onParse(Response response, @Nullable Object extra) throws Exception {
        //注意：此处extra未null
        return null != callBack ? callBack.onParse(response, this) : null;
    }

    @Override
    public void onResponse(T result) {
        if (null != callBack) callBack.onResponse(result);
    }

    @Override
    public void onProgress(float progress, long total) {
        if (null != callBack) callBack.onProgress(progress, total);
    }

    @Override
    public void onError(Exception e) {
        if (null != callBack) callBack.onError(e);
    }

    @Override
    public void onAfter() {
        if (null != callBack) callBack.onAfter();
    }

    /**
     * ReQuest.Builder
     *
     * @param <T>
     */
    public static class Builder<T> {
        private String url;
        private Map<String, Object> param;
        private CallBack<T, ReQuest<T>> callBack;
        private Method method;

        private Builder() {
        }

        public static Builder get() {
            Builder builder = new Builder();
            builder.method = Method.get;
            return builder;
        }

        public static Builder post() {
            Builder builder = new Builder();
            builder.method = Method.post;
            return builder;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder param(Map<String, Object> param) {
            this.param = param;
            return this;
        }

        public Builder callback(CallBack<T, ReQuest<T>> callBack) {
            this.callBack = callBack;
            return this;
        }

        public ReQuest build() {
            ReQuest reQuest = new ReQuest();
            reQuest.method = method;
            reQuest.url = url;
            reQuest.param = param;
            reQuest.callBack = callBack;
            return reQuest;
        }
    }
}
