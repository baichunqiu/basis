package com.basis.base;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;

import com.bcq.net.enums.NetType;
import com.kit.UIKit;

/**
 * @author: BaiCQ
 * @ClassName: BaseActivity
 * @date: 2018/8/17
 * @Description: 基类，AbsExitActivity的空实现
 */
public abstract class BaseActivity extends FragmentActivity implements IBase {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;

    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected void onDestroy() {
        UIStack.getInstance().remove(mActivity);
        super.onDestroy();
    }

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        if (setFullScreen()) {//全屏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        UIStack.getInstance().add(mActivity);
        layout = UIKit.inflate(setLayoutId());
        setContentView(layout);
        init();
    }

    protected View getLayout() {
        return layout;
    }

    protected <T extends View> T getView(@IdRes int id) {
        return layout.findViewById(id);
    }

    @Override
    public void onRefresh(Object obj) {
    }

    @Override
    public void onNetChange(NetType netType) {
    }

    @Override
    public void onBackPressed() {
        onBackCode();
    }

    /**
     * 统一处理activity 返回事件
     */
    public void onBackCode() {
        finish();
    }
}