package com.kit;

import android.view.View;
import android.view.ViewGroup;

import com.kit.utils.Logger;

/**
 * 通用工具类
 */
public class CommUtil {
    private static long _lastClickTime = 0;
    private final static long INTERVAL_DEF = 300;

    public static boolean isQuickClick() {
        return isQuickClick(INTERVAL_DEF);
    }

    public synchronized static boolean isQuickClick(long interval) {
        boolean flag = false;
        long now = System.currentTimeMillis();
        if (now - _lastClickTime <= interval) {
            flag = true;
        }
        _lastClickTime = now;
        return flag;
    }

}
