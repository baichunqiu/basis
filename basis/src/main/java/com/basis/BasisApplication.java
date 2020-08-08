package com.basis;

import android.app.Application;

import com.basis.base.AppHelper;
import com.basis.widget.TitleBar;
import com.business.OkHelper;
import com.kit.UIKit;


public class BasisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        OkHelper.setDebug(true);
        TitleBar.setDefaultBuild(new TitleBar.DefaultBuild(13f, 0)
                .buildTitleColor(UIKit.getResources().getColor(R.color.white))
                .buildLeftDrawable(UIKit.getResources().getDrawable(R.mipmap.ic_back))
                .buildBackGroundColor(getResources().getColor(R.color.color_blue))
                .buildPressDrawable(R.drawable.selector_main_pressed_bg)
                .buildTitleSize(17));
    }
}
