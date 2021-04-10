package com.basis;

import android.app.Application;

import com.business.OkHelper;
import com.kit.utils.Logger;
import com.kit.UIKit;


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
