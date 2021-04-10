package com.kit.wapper;

import android.view.View;

import com.kit.utils.Logger;

/**
 * 处理 View 重复点击的代理
 */
public class KClickProxy implements View.OnClickListener {
    private static final String TAG = "KitClickProxy";
    private int interval = 300;//milliseconds
    private long mLastClickTime;
    //被代理对象
    private View.OnClickListener mproxyer;

    public KClickProxy(View.OnClickListener mproxyer) {
        this.mproxyer = mproxyer;
    }

    /**
     * @param mproxyer 点击事件
     * @param interval 多次点击间隔
     */
    public KClickProxy(View.OnClickListener mproxyer, int interval) {
        this.mproxyer = mproxyer;
        this.interval = interval;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - mLastClickTime >= interval) {
            mproxyer.onClick(v);
            mLastClickTime = System.currentTimeMillis();
        } else {
            Logger.i(TAG, "onClick: 重复点击");
        }
    }
}