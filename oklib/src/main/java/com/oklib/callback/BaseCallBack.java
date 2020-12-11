package com.oklib.callback;

import com.oklib.core.Dispatcher;
import com.oklib.core.ReQuest;

import okhttp3.Request;
import okhttp3.Response;

public class BaseCallBack<R> implements CallBack<R, ReQuest<R>> {
    private ReQuest<R> reQuest;

    @Override
    public void onBefore(Request.Builder request) {
    }

    @Override
    public void onProgress(float progress, long total) {
    }

    @Override
    public R onParse(Response response) throws Exception {
        return null;
    }

    @Override
    public void onResponse(R result) {
    }

    @Override
    public void onError(Exception e) {
    }

    @Override
    public void onAfter() {
    }

    /**
     * 分发UI Thread
     *
     * @param run
     */
    protected void dispatch(Runnable run) {
        Dispatcher.get().dispatch(run);
    }

    @Override
    public CallBack<R, ReQuest<R>> set(ReQuest<R> reQuest) {
        this.reQuest = reQuest;
        return this;
    }

    @Override
    public ReQuest<R> get() {
        return reQuest;
    }
}
