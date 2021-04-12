package com.basis.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.adapter.interfaces.IHolder;
import com.basis.R;
import com.basis.net.LoadTag;
import com.business.interfaces.IParse;
import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;
import com.kit.UIKit;
import com.oklib.Method;

import java.util.List;
import java.util.Map;

/**
 * @param <ND> 接口数据类型
 * @param <AD> 适配器数据类型 一般情况：和ND类型一致
 * @param <VH> 适配器的holder类型 IRefresh的类型是Listview VH是LvHolder，若是RecylerView VH是RcyHolder
 */
public abstract class ListActivity<ND, AD, VH extends IHolder> extends BaseActivity implements UIController.IOperator<ND, AD, VH> {
    private Class<ND> tClass;
    private UIController<ND, AD, VH> mController;
    private View contentView;

    @Override
    public final int setLayoutId() {
        return R.layout.activity_abs_list;
    }

    @Override
    public final void init() {
        tClass = (Class<ND>) ObjUtil.getTType(getClass())[0];
        resetLayoutView();
        mController = new UIController<>(getLayout(), tClass, this);
        initView(contentView);
    }

    private void resetLayoutView() {
        FrameLayout ll_content = getView(R.id.ll_content);
        contentView = setContentView();
        ll_content.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        //添加no_data到show_data同级
        View show_data = UIKit.getView(contentView, R.id.bsi_show_data);
        //未设置show_data布局 使用lv替代
        if (null == show_data) show_data = UIKit.getView(contentView, R.id.bsi_refresh);
        ViewGroup extraParent = null != show_data ? (ViewGroup) show_data.getParent() : ll_content;
        extraParent.addView(UIKit.inflate(R.layout.no_data), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public void getNetData(boolean isRefresh, String mUrl, Map<String, Object> params, String mDialogMsg, Method method) {
        getNetData(isRefresh, mUrl, params, null, mDialogMsg, method);
    }

    /**
     * @param isRefresh  是否刷新
     * @param mUrl       mUrl
     * @param params     参数 注意：不包含page
     * @param method     Post/get
     * @param parser     解析器
     * @param mDialogMsg 进度条显示msg
     */
    public void getNetData(boolean isRefresh, String mUrl, Map<String, Object> params, IParse parser, String mDialogMsg, Method method) {
        if (null != mController)
            mController.request(isRefresh, mUrl, params, parser, TextUtils.isEmpty(mDialogMsg) ? null : new LoadTag(mActivity, mDialogMsg), method);
    }

    /**
     * 刷新适配器数据
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    public void refreshData(List<ND> netData, boolean isRefresh) {
        if (null != mController) mController.onRefreshData(netData, isRefresh);
    }

    /**
     * @param netData 此次请求的数据
     */
    @Override
    public List<AD> onPreRefreshData(List<ND> netData, boolean isRefresh) {
        return (List<AD>) netData;
    }

    /**
     * @param netData 设置给适配器的数据
     */
    @Override
    public List<AD> onPreSetData(List<AD> netData) {
        return netData;
    }

    @Override
    public void onError(int status, String errMsg) {
        Logger.e(TAG, "errMsg :" + errMsg);
    }

    public abstract View setContentView();

    public abstract void initView(View view);
}