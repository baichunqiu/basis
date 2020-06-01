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

/**
 * @author: BaiCQ
 * @ClassName: BaseFragment
 * @date: 2018/8/17
 * @Description: Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements IBase, AppHelper.OnNetChangeListeren {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        AppHelper.getInstance().addOnNetChangeListeren(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AppHelper.getInstance().removeOnNetChangeListeren(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != layout) {
            ViewGroup vg = (ViewGroup) layout.getParent();
            if (vg != null) vg.removeAllViewsInLayout();
        } else {
            layout = inflater.inflate(setLayoutId(), null);
        }
        return layout;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}
