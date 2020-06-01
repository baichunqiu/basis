package com.oklib.core;

import android.os.Handler;
import android.os.Looper;

/**
 * UI Thread 分发器
 */
public class Dispatcher {
    private static final Dispatcher Instance = new Dispatcher();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private Dispatcher() {
    }

    public static Dispatcher get() {
        return Instance;
    }


    public void dispatch(Runnable runnable) {
        handler.post(runnable);
    }
}