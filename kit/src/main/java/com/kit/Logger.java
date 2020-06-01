package com.kit;

import android.util.Log;

public class Logger {

    public static String Tag = "Logger";
    private static boolean debug = true;

    public static void setDebug(boolean debug) {
        Logger.debug = debug;
    }

    public static void v(Object obj) {
        v(Tag, obj);
    }

    public static void v(String tag, Object obj) {
        if (debug) Log.v(tag, obj.toString());
    }

    public static void d(Object obj) {
        d(Tag,obj);
    }

    public static void d(String tag, Object obj) {
        if (debug) Log.d(tag, obj.toString());
    }

    public static void i(Object obj) {
        i(Tag, obj);
    }

    public static void i(String tag, Object obj) {
        if (debug) Log.i(tag, obj.toString());
    }

    public static void w(Object obj) {
        w(Tag, obj);
    }

    public static void w(String tag, Object obj) {
        if (debug) Log.w(tag, obj.toString());
    }

    public static void e(Object obj) {
        e(Tag, obj);
    }

    public static void e(String tag, Object obj) {
        if (debug) Log.e(tag, obj.toString());
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       原log文本
     * @param showCount 规定每段显示的长度（最好不要超过eclipse限制长度）
     */
    public static void showLogInfo(String tag, String log, int showCount) {
        if (debug) {
            if (log.length() > showCount) {
                String show = log.substring(0, showCount);
                Log.i(tag, show + "");
                if ((log.length() - showCount) > showCount) {// 剩下的文本还是大于规定长度
                    String partLog = log.substring(showCount, log.length());
                    showLogInfo(tag, partLog, showCount);
                } else {
                    String surplusLog = log.substring(showCount, log.length());
                    Log.i(tag, surplusLog + "");
                }
            } else {
                Log.i(tag, log + "");
            }
        }

    }

}
