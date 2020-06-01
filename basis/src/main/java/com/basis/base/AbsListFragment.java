package com.basis.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basis.R;
import com.bcq.net.view.LoadTag;
import com.business.parse.Parser;
import com.kit.Logger;
import com.kit.ObjUtil;
import com.kit.UIKit;
import com.oklib.core.Method;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListFragment
 * @Description:
 */
public abstract class AbsListFragment<T> extends BaseFragment implements UIController.IOperator<T> {
    private Class<T> tClass;
    private UIController<T> mController;
    private View contentView;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_abs_list;
    }

    public final void init() {
        resetLayoutView();
        tClass = (Class<T>) ObjUtil.getTType(getClass())[0];
        mController = new UIController<T>(getLayout(), tClass, this);
        initView(contentView);
    }

    private void resetLayoutView() {
        FrameLayout ll_content = getView(R.id.ll_content);
        contentView = setContentView();
        ll_content.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        //添加no_data到show_data同级
        View show_data = UIKit.getView(contentView, R.id.bsi_v_show_data);
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
     * @param parser     解析器
     * @param mDialogMsg 进度条显示msg
     * @param method     Post/get
     */
    public void getNetData(boolean isRefresh, String mUrl, Map<String, Object> params, Parser parser, String mDialogMsg, Method method) {
        if (null != mController)
            mController.request(isRefresh, mUrl, params, parser, TextUtils.isEmpty(mDialogMsg) ? null : new LoadTag(mActivity, mDialogMsg), method);
    }

    public void refreshData(List<T> netData, boolean isRefresh) {
        if (null != mController) mController.onRefreshData(netData, isRefresh);
    }

    /**
     * 接口解析数据后子线程预处理数据
     *
     * @param netData
     */
    @Override
    public List<T> onPreprocess(List<T> netData) {
        return netData;
    }

    /**
     * 适配器设置数据前 处理数据 有可能类型转换
     *
     * @param netData
     */
    @Override
    public List onPreRefreshData(List<T> netData, boolean isRefresh) {
        return netData;
    }

    @Override
    public List<T> onPreSetData(List<T> netData) {
        return netData;
    }

    @Override
    public void onError(int status, String errMsg) {
        Logger.e(TAG, "errMsg :" + errMsg);
    }


    public abstract View setContentView();

    public abstract void initView(View view);
}
