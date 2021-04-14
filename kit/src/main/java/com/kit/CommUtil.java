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

    /**
     * 递归查找指定类型的视图
     *
     * @param layout   视图树的根节点
     * @param level    起始层级
     * @param maxLevel 最大层级 小于0 则不现在层级
     */
    public static <T extends View> T findChildByTypeCalss(ViewGroup layout, Class clazz, int level, int maxLevel) {
        if (level < 1) {
            level = 1;
        }
        if (maxLevel > 0 && level > maxLevel) {
            return null;
        }
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {//遍历第一层视图树
            View ch1 = layout.getChildAt(i);
            if (clazz.isInstance(ch1)) {
                Logger.e(clazz.getSimpleName() + " 在" + level + "级视图查到");
                return (T) ch1;
            } else if (ch1 instanceof ViewGroup) {
                ViewGroup chg = (ViewGroup) ch1;
                T t = findChildByTypeCalss(chg, clazz, level + 1, maxLevel);
                if (null != t) {
                    return t;
                }
            }
        }
        return null;
    }
}
