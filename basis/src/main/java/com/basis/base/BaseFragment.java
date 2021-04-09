package com.basis.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kit.utils.Logger;

/**
 * @author: BaiCQ
 * @ClassName: BaseFragment
 * @date: 2018/8/17
 * @Description: Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements IBasis {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;
    private boolean init = false;//init 和 onRefresh()的执行的先后问题

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

    @Deprecated
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    @Override
    public abstract int setLayoutId();

    @Override
    public abstract void init();

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
    public void onNetChange() {
    }

    public boolean isInit() {
        return init;
    }
}
