package com.basis.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bcq.net.enums.NetType;
import com.kit.Logger;

/**
 * @author: BaiCQ
 * @ClassName: BaseFragment
 * @date: 2018/8/17
 * @Description: Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements IBase {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;
    private boolean init = false;

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        UIStack.getInstance().add(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        UIStack.getInstance().remove(this);
        init = false;
        Logger.e(TAG, "onDetach");
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(setLayoutId(), null);
        Logger.e(TAG, "onCreateView");
        return layout;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.e(TAG, "onViewCreated");
        init();
        init = true;
    }

    protected View getLayout() {
        return layout;
    }

    protected <T extends View> T getView(@IdRes int id) {
        return layout.findViewById(id);
    }

    /**
     * 首次刷新尽量先于init执行
     *
     * @param obj
     */
    @Override
    public void onRefresh(Object obj) {
        Logger.e(TAG, "onRefresh");
    }

    @Override
    public void onGlass(boolean connected) {
        Logger.e(TAG, "connected = " + connected);
    }

    @Override
    public void onCmdMsg(String cmd, String msgJson) {
//        Logger.e(TAG, "cmd = " + cmd + " msgJson = "+msgJson);
    }

    @Override
    public void onTrtcOff() {

    }

    @Override
    public void onNetChange(NetType netType) {
    }

    public boolean isInit() {
        return init;
    }
}
