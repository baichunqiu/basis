package com.oklib.core;

import android.util.Log;

import com.oklib.callback.CallBack;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Core {
    private static final Core core = new Core();
    private static OkHttpClient mClient;
    private OkHttpClient.Builder mBuilder;

    private Core() {
    }

    public static Core core() {
        return core;
    }

    private OkHttpClient client() {
        if (null == mClient) {
             mClient = null == mBuilder ? new OkHttpClient() : mBuilder.build();
        }
        return mClient;
    }

    public void builder(OkHttpClient.Builder builder) {
        this.mBuilder = builder;
        mClient = null;
    }

    /**
     * @param url      请求地址
     * @param params   参数
     *                 key：String value：object（Ibody）
     * @param callBack 回调
     */
    public <T> void post(String url, Map<String, Object> params, CallBack<T, Object> callBack) {
        post(url, Transform.param2Body(params), callBack);
    }


    /**
     * @param url      请求地址
     * @param params   参数
     * @param callBack 回调
     */
    public <T> void get(String url, Map<String, Object> params, CallBack<T, Object> callBack) {
        Request.Builder builder = new Request.Builder()
                .url(Transform.urlAppendParam(url, params));
        callBack.onBefore(builder);
        request(builder.build(), callBack);
    }

    /**
     * @param url      请求地址
     * @param body     body
     * @param callBack 回调
     */
    protected <T> void post(String url, RequestBody body, CallBack<T, Object> callBack) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        callBack.onBefore(builder);
        builder.addHeader("content-type", "application/json");
        request(builder.build(), callBack);
    }

    /**
     * 真实执行请求的封装
     *
     * @param request  请求封装体
     * @param callback 回调
     */
    private final <T> void request(Request request, final CallBack<T, Object> callback) {
//        if (request != null) {
//            Headers headers = request.headers();
//            for (String name : headers.names()) {
//                Log.e("request", "name = " + name + " value = " + headers.get(name));
//            }
//        }
        client().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                dispatchFail(e, callback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        dispatchFail(new IOException("Canceled!"), callback);
                        return;
                    }
                    T o = callback.onParse(response, null);
                    dispatchSuccess(o, callback);
                } catch (Exception e) {
                    dispatchFail(e, callback);
                } finally {
                    if (response.body() != null) {
                        response.body().close();
                    }
                }

            }
        });
    }

    private void dispatchFail(final Exception e, final CallBack callback) {
        Dispatcher.get().dispatch(new Runnable() {
            @Override
            public void run() {
                callback.onError(e);
                callback.onAfter();
            }
        });
    }

    private <T> void dispatchSuccess(final T obj, final CallBack callback) {
        Dispatcher.get().dispatch(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(obj);
                callback.onAfter();
            }
        });
    }

}
