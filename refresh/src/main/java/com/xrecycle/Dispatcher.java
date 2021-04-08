package com.xrecycle;

import android.os.Handler;
import android.os.Looper;

/**
 * UI Thread 分发器
 */
public class Dispatcher {
    private static final Dispatcher instance = new Dispatcher();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private Dispatcher() {
    }

    public static Dispatcher get() {
        return instance;
    }


    public void post(Runnable runnable) {
        handler.post(runnable);
    }
    public void  postDelayed(Runnable runnable,long delay){
        handler.postDelayed(runnable,delay);
    }
}