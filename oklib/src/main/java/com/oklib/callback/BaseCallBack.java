package com.oklib.callback;

import com.oklib.core.Dispatcher;
import com.oklib.core.ReQuest;

import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallBack<T> implements CallBack<T, ReQuest<T>> {
    protected ReQuest<T> reQuest;

    @Override
    public void onBefore(Request.Builder request) {
    }

    @Override
    public void onAfter() {
    }

    @Override
    public void onProgress(float progress, long total) {
    }

    @Override
    public T onParse(Response response, ReQuest<T> extra) throws Exception {
        this.reQuest = extra;
        return onParse(response);
    }

    /**
     * 分发UI Thread
     *
     * @param run
     */
    protected void dispatch(Runnable run) {
        Dispatcher.get().dispatch(run);
    }

    public abstract T onParse(Response response) throws Exception;
}
