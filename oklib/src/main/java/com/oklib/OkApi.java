package com.oklib;

import com.oklib.body.BitmapBody;
import com.oklib.body.FileBody;
import com.oklib.callback.CallBack;
import com.oklib.callback.FileCallBack;
import com.oklib.core.Core;
import com.oklib.core.ReQuest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class OkApi {

    /**
     * 设置配置信息
     *
     * @param builder
     */
    public static void config(OkHttpClient.Builder builder) {
        Core.core().builder(builder);
    }

    public static ReQuest<File> download(String url, Map<String, Object> params, FileCallBack fileCallBack) {
        return get(url, params, fileCallBack);
    }

    public static <T> ReQuest<T> bitmap(String url, String key, BitmapBody bitmap, CallBack<T, ReQuest<T>> callBack) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(key, bitmap);
        return post(url, params, callBack);
    }

    public static <T> ReQuest<T> file(String url, String key, FileBody fileBody, CallBack<T, ReQuest<T>> callBack) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(key, fileBody);
        return post(url, params, callBack);
    }

    public static <T> ReQuest<T> get(String url, Map<String, Object> params, CallBack<T, ReQuest<T>> callBack) {
        return ReQuest.Builder.get()
                .url(url)
                .param(params)
                .callback(callBack)
                .build()
                .request();
    }

    public static <T> ReQuest<T> post(String url, Map<String, Object> params, CallBack<T, ReQuest<T>> callBack) {
        return ReQuest.Builder.post()
                .url(url)
                .param(params)
                .callback(callBack)
                .build()
                .request();
    }

    public static <T> ReQuest<T> delete(String url, Map<String, Object> params, CallBack<T, ReQuest<T>> callBack) {
        return ReQuest.Builder.delete()
                .url(url)
                .param(params)
                .callback(callBack)
                .build()
                .request();
    }

}
