package com.bcq.net.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.business.ILoadTag;
import com.net.R;
import com.spinkit.SpinKitView;


/**
 * @author: BaiCQ
 * @ClassName: LoadTag
 * @Description: 标准加载进度条
 */
public class LoadTag implements ILoadTag {
    private Dialog dialog;
    private String loadMsg;
    private SpinKitView progressBar;
    private int styleIndex = 2;
    private DialogInterface.OnDismissListener dismissListener;

    public LoadTag(Activity activity) {
        this(activity, activity.getString(R.string.net_loading));
    }

    public LoadTag(Activity activity, String dialogMsg) {
        dialog = new Dialog(activity, R.style.CustomProgressDialog);
        View rootView = LayoutInflater.from(activity).inflate(R.layout.net_layout_load_dialog, null);
        progressBar = (SpinKitView) rootView.findViewById(R.id.prgressBar);
        progressBar.setStyleByIndex(styleIndex);
        TextView textView = (TextView) rootView.findViewById(R.id.tv_load_msg);
        if (TextUtils.isEmpty(dialogMsg)) {
            dialogMsg = activity.getString(R.string.net_loading);
        }
        this.loadMsg = dialogMsg;
        textView.setText(loadMsg);
        // 允许点返回键取消
        dialog.setCancelable(true);
        // 触碰其他地方不消失
        dialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(rootView, params);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (null != dismissListener) dismissListener.onDismiss(dialog);
            }
        });
    }

    @Override
    public ILoadTag setOnDismissListener(DialogInterface.OnDismissListener onDismiss) {
        this.dismissListener = onDismiss;
        return this;
    }

    @Override
    public String getTagMsg() {
        return loadMsg;
    }

    @Override
    public void show() {
        if (null != dialog) {
            dialog.show();
        }
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}