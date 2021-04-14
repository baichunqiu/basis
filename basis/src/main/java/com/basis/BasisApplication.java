package com.basis;

import android.app.Application;

import com.bcq.net.wrapper.OkHelper;
import com.kit.utils.Logger;


public class BasisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Logger.setDebug(true);
        OkHelper.setDebug(true);
    }
}
