package com.kit;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author: BaiCQ
 * @ClassName: KToast
 * @date: 2018/4/4
 * @Description:
 */
public class KToast {
    private final static KToast ktIns = new KToast();
    private TextView showText;
    private View tipsView;
    private Toast toast;
    private final static int distance = ScreenUtil.getScreemWidth() / 5;


    private KToast() {
        tipsView = UIKit.inflate(R.layout.toast_mb);
        showText = tipsView.findViewById(R.id.toast_content);
    }

    private Toast init() {
        Toast toast = new Toast(UIKit.getContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, distance);//底部
        toast.setView(tipsView);
        toast.setDuration(Toast.LENGTH_LONG);
        return toast;
    }

    private KToast makeText(int resId) {
        showText.setText(resId);
        return this;
    }

    private KToast makeText(String msg) {
        showText.setText(msg);
        return this;
    }


    private void show() {
        if (null != toast) {
            toast.cancel();
        }
        toast = init();
        toast.show();
    }

    /**
     * 显示通知
     *
     * @param resouceId 显示的字符串 id
     */
    public static void show(int resouceId) {
        ktIns.makeText(resouceId).show();
    }


    public static void show(String msg) {
        ktIns.makeText(msg).show();
    }

}
