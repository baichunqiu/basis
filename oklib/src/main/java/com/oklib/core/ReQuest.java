package com.oklib.core;

import com.business.OkUtil;
import com.oklib.callback.CallBack;

import java.util.Map;

//import com.kit.utils.Logger;

public class ReQuest<T> {
    private final static String TAG = "ReQuest";
    public String url;
    public Map<String, Object> param;
    public CallBack<T, Object> callBack;
    private Method method;

    private ReQuest() {
    }

    public Map<String, Object> param() {
        return param;
    }

    public ReQuest<T> request() {
        logParams(url, method.name(), param);
        if (Method.post == method) {
            Core.core().post(url, param, callBack.set(this));
        } else if (Method.get == method) {
            Core.core().get(url, param, callBack.set(this));
        } else if (Method.delete == method) {
            Core.core().delete(url, param, callBack.set(this));
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
        OkUtil.e(TAG, " : ------------- start --------------------");
        OkUtil.e(TAG, " : url = " + url + "  method = " + method);
        int size = null == params ? 0 : params.size();
        if (size > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                OkUtil.e(TAG, " : " + entry.getKey() + " = " + OkUtil.obj2Json(entry.getValue()));
            }
        } else {
            OkUtil.e(TAG, " : 参数【无】");
        }
        OkUtil.e(TAG, " : -------------   end --------------------");
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

        public static Builder method(Method method) {
            Builder builder = new Builder();
            builder.method = method;
            return builder;
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

        public static Builder delete() {
            Builder builder = new Builder();
            builder.method = Method.delete;
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
