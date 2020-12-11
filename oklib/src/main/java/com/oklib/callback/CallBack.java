package com.oklib.callback;

import com.oklib.core.ReQuest;

import org.jetbrains.annotations.Nullable;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public interface CallBack<T, E> {

    /**
     * UI Thread
     * 请求Request在build（）前执行，此处可自主添加本次请求header
     *
     * @param builder request相关的操作
     */
    void onBefore(Request.Builder builder);

    /**
     * 非UI Thread
     *
     * @param response
     * @return
     * @throws Exception
     */
    T onParse(Response response) throws Exception;

    /**
     * UI Thread
     *
     * @param progress 当前进度
     * @param total    总进度
     */
    void onProgress(float progress, long total);

    /**
     * UI Thread
     *
     * @param result
     */
    void onResponse(T result);

    /**
     * UI Thread
     *
     * @param e
     */
    void onError(Exception e);

    /**
     * UI Thread
     */
    void onAfter();


    CallBack<T, E> set(E e);

    E get();
}