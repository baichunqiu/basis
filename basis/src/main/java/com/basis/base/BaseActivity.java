package com.basis.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.basis.widget.ActionWrapBar;
import com.basis.widget.interfaces.IWrapBar;
import com.kit.UIKit;
import com.kit.utils.Logger;

/**
 * @author: BaiCQ
 * @ClassName: BaseActivity
 * @date: 2018/8/17
 * @Description: 基类，AbsExitActivity的空实现
 */
public abstract class BaseActivity extends AppCompatActivity implements IBasis {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity activity;
    private View layout;
    private IWrapBar wrapBar;

    @Override
    protected void onDestroy() {
        UIStack.getInstance().remove(activity);
        super.onDestroy();
    }

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        UIStack.getInstance().add(activity);
        layout = UIKit.inflate(setLayoutId());
        setContentView(layout);
        //init wapp
        wrapBar = new ActionWrapBar(activity).work();
        init();
    }

    public abstract int setLayoutId();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return wrapBar.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.e(TAG, "title = "+item.getTitle());
        if (android.R.id.home == item.getItemId()) {
            Logger.e(TAG, "back !");
            onBackCode();
            return true;
        } else {
            return wrapBar.onOptionsItemSelected(item);
        }
    }

    protected IWrapBar getWrapBar() {
        return wrapBar;
    }

    @Override
    public abstract void init();

    public View getLayout() {
        return layout;
    }

    protected <T extends View> T getView(@IdRes int id) {
        return layout.findViewById(id);
    }

    @Override
    public void onRefresh(Object obj) {
    }

    @Override
    public void onNetChange() {
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