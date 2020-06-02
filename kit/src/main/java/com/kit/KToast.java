package com.kit;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author: BaiCQ
 * @ClassName: KToast
 * @date: 2018/4/4
 * @Description: ToastManager的备注 强制使用resouceId 替换message text
 */
public class KToast extends Toast {
    private static KToast instance;
    private TextView showText;

    public static KToast getInstance() {
        if (instance == null) {
            synchronized (KToast.class) {
                if (instance == null) {
                    instance = new KToast();
                }
            }
        }
        return instance;
    }

    private KToast() {
        super(UIKit.getContext());
        init(this);
    }

    private void init(Toast toast) {
        View tipsView = UIKit.inflate(R.layout.toast_mb);
        showText = tipsView.findViewById(R.id.toast_content);
        int distance = ScreenUtil.getScreemWidth() * 25 / 100;
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, distance);//底部
        toast.setView(tipsView);
    }

    private KToast makeText(int msgResouceId) {
        return makeText(ResUtil.getString(msgResouceId));
    }

    private KToast makeText(String msg) {
        if (null != showText) {
            showText.setText(msg);
        }
        return this;
    }

    private KToast setDurations(int duration) {
        this.setDuration(duration);
        return this;
    }

    /**
     * 显示通知
     *
     * @param resouceId 显示的字符串 id
     * @param length    显示时间
     */
    public static void show(int resouceId, int length) {
        getInstance().makeText(resouceId).setDurations(length).show();
    }

    /**
     * 显示通知
     *
     * @param resouceId 显示的字符串
     */
    public static void show(int resouceId) {
        show(resouceId, 300);
    }

    public static void show(String msg) {
        show(msg, 300);
    }

    @Deprecated
    public static void show(String msg, int length) {
        getInstance().makeText(msg).setDurations(length).show();
    }


}
