package com.basis.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.basis.R;
import com.basis.percent.PercentLinearLayout;
import com.kit.ResUtil;
import com.kit.ScreenUtil;


/**
 * @author: BaiCQ
 * @createTime: 2016/12/22 18:04
 * @className: WXDialog
 * @Description: 最新标准微信风格弹框
 */
public class WXDialog {
    private Activity context;
    private View contentView;
    private Dialog dialog;
    //标题
    private TextView tvTitle;
    //文本内容和添加view的父布局
    private PercentLinearLayout llContent;
    //内容
    private TextView tvMessage;
    //确认按钮
    private TextView confirmBtn, cancelBtn;

    interface CustomBuilder {
        View onBuild();
    }

    /**
     * 构建WXstyle 风格的dialog
     *
     * @param content
     */
    protected void buildByWXStyle(View content) {
        if (null == content) {
            throw new IllegalArgumentException("contentView can not null !");
        }
        contentView = content;
        dialog = new Dialog(context, R.style.CustomProgressDialog);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.addContentView(contentView, params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        dialog.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 设置窗口大小和位置
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.width = (int) (ScreenUtil.getScreemWidth() * 0.74);
        dialog.onWindowAttributesChanged(wl);
    }

    /**
     * 自定义布局构建
     *
     * @param activity
     * @param builder
     */
    public WXDialog(Activity activity, CustomBuilder builder) {
        this.context = activity;
        View content = null == builder ? null : builder.onBuild();
        buildByWXStyle(content);
    }


    /**
     * 默认布局构建
     *
     * @param activity
     */
    public WXDialog(final Activity activity) {
        this.context = activity;
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_wx_dialog, null);
        buildByWXStyle(contentView);
        initView();
    }

    private void initView() {
        if (null == contentView) return;
        confirmBtn = contentView.findViewById(R.id.btn_confirm);
        cancelBtn = contentView.findViewById(R.id.btn_cancel);
        llContent = (PercentLinearLayout) contentView.findViewById(R.id.ll_content);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        // 默认 标题和确认取消按钮只有设置才显示
        tvTitle.setVisibility(View.GONE);
        confirmBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void show() {
        if (null != dialog) {
            dialog.show();
        }
    }

    public WXDialog setOnDismissListener(DialogInterface.OnCancelListener dismissListener) {
        if (null != dialog) {
            dialog.setOnCancelListener(dismissListener);
        }
        return this;
    }

    /**
     * @param outsideCancele 点击外部区域是否显示
     */
    public void show(boolean outsideCancele) {
        if (null != dialog) {
            dialog.setCanceledOnTouchOutside(outsideCancele);
            dialog.show();
        }
    }

    public WXDialog setTitle(@StringRes int titleId) {
        return setTitle(ResUtil.getString(titleId));
    }

    public WXDialog setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        return this;
    }

    public WXDialog setMessage(@StringRes int msgId) {
        return setMessage(ResUtil.getString(msgId));
    }

    /**
     * 如果是html 使用：Html.fromHtml(msg)转换
     *
     * @param msg
     */
    public WXDialog setMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            tvMessage.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(msg);
        }
        return this;
    }


    public void setPositiveButton(final View.OnClickListener confirmListener, String text) {
        confirmBtn.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(text)) {
            confirmBtn.setText(text);
        }
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
            }
        });
    }

    public WXDialog setSureButton(@StringRes int sureId, View.OnClickListener sureClick) {
        return setSureButton(ResUtil.getString(sureId), ResUtil.getColor(R.color.white), null, sureClick);
    }

    public WXDialog setSureButton(String sureText, View.OnClickListener sureClick) {
        return setSureButton(sureText, ResUtil.getColor(R.color.white), null, sureClick);
    }

    public WXDialog setSureButton(@StringRes int sureId, @ColorRes int colorId, View.OnClickListener sureClick) {
        return setSureButton(ResUtil.getString(sureId), ResUtil.getColor(colorId), null, sureClick);
    }

    public WXDialog setSureButton(@StringRes int sureId, @ColorRes int colorId, @DrawableRes int sureBgId, View.OnClickListener sureClick) {
        return setSureButton(ResUtil.getString(sureId), ResUtil.getColor(colorId), ResUtil.getDrawable(sureBgId), sureClick);
    }

    /**
     * @param sureText  文本信息
     * @param color     Color.parse("#ffffff") or ResUtil.getColor(colorId)
     * @param sureBg    按钮背景 不设置使用默认
     * @param sureClick
     */
    public WXDialog setSureButton(String sureText, int color, Drawable sureBg, final View.OnClickListener sureClick) {
        confirmBtn.setVisibility(View.VISIBLE);
        confirmBtn.setTextColor(color);
        if (!TextUtils.isEmpty(sureText)) confirmBtn.setText(sureText);
        if (null != sureBg) confirmBtn.setBackground(sureBg);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sureClick != null) sureClick.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public WXDialog setCancelButton(@StringRes int cancelId) {
        return setCancelButton(ResUtil.getString(cancelId));
    }

    public WXDialog setCancelButton(String cancelText) {
        return setCancelButton(cancelText, ResUtil.getColor(R.color.color_font_black2), null);
    }

    public WXDialog setCancelButton(@StringRes int cancelId, @ColorRes int colorId, @DrawableRes int cancelBgId) {
        return setCancelButton(ResUtil.getString(cancelId), ResUtil.getColor(colorId), ResUtil.getDrawable(cancelBgId));
    }

    /**
     * @param cancelText
     * @param color
     * @param cancelBg
     * @return
     */
    public WXDialog setCancelButton(String cancelText, int color, Drawable cancelBg) {
        cancelBtn.setVisibility(View.VISIBLE);
        cancelBtn.setTextColor(color);
        if (!TextUtils.isEmpty(cancelText)) cancelBtn.setText(cancelText);
        if (null != cancelBg) cancelBtn.setBackground(cancelBg);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }


    /**
     * 添加自定义Content视图组件
     *
     * @param customContentView
     */
    public WXDialog addCustomContentView(View customContentView) {
        if (null != llContent && null != customContentView) {
            llContent.removeAllViews();
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            llContent.addView(customContentView, params);
        }
        return this;
    }

    public enum Style {
        sure,
        cacel,
        delete,
        defalut
    }

//    public WXDialog style(Style style, boolean notitle) {
//        if (style == Style.cacel) {
//            cancelStyle()
//        }
//        return this;
//    }

    public WXDialog defalutStyle(boolean title, View.OnClickListener sureClick) {
        return title ? setTitle(R.string.tip)
                .setCancelButton(R.string.cancle)
                .setSureButton(R.string.ok, R.color.white, sureClick)
                : setCancelButton(R.string.cancle)
                .setSureButton(R.string.ok, R.color.white, sureClick);
    }

    public WXDialog cancelStyle(boolean title) {
        return title ? setTitle(R.string.tip)
                .setCancelButton(R.string.cancle) :
                setCancelButton(R.string.cancle);
    }

    public WXDialog sureStyle(boolean title, View.OnClickListener sureClick) {
        return title ? setTitle(R.string.tip)
                .setSureButton(R.string.ok, sureClick) :
                setSureButton(R.string.ok, sureClick);
    }


    public WXDialog deleteStyle(boolean title, View.OnClickListener sureClick) {
        return title ? setTitle(R.string.tip)
                .setSureButton(R.string.delete, R.color.white, R.drawable.selector_red_solid, sureClick)
                : setSureButton(R.string.delete, R.color.white, R.drawable.selector_red_solid, sureClick);
    }

    /**
     * @param activity Activity
     * @param message  提示消息 cancel：知道了
     */
    public static void showCancelDialog(Activity activity, String message) {
        new WXDialog(activity)
                .setMessage(message)
                .cancelStyle(true)
                .show(true);
    }

    /**
     * @param activity Activity
     * @param message  对话框提示的消息 sure：确定
     */
    public static void showSureDialog(Activity activity, String message, View.OnClickListener sureClick) {
        new WXDialog(activity)
                .setMessage(message)
                .sureStyle(true, sureClick)
                .show();
    }

    public static void showDeleteDialog(Activity activity, String message, View.OnClickListener sureClick) {
        new WXDialog(activity)
                .setMessage(message)
                .deleteStyle(false, sureClick)
                .show();
    }

    public static void showDefaultDialog(Activity activity, String message, View.OnClickListener sureClick) {
        new WXDialog(activity)
                .setMessage(message)
                .defalutStyle(true, sureClick)
                .show();
    }
}
