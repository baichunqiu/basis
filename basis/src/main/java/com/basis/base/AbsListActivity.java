package com.basis.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basis.R;
import com.basis.net.LoadTag;
import com.basis.widget.TitleBar;
import com.business.parse.IParse;
import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;
import com.kit.UIKit;
import com.oklib.Method;

import java.util.List;
import java.util.Map;


/**
 * @param <V> 适配器数据类型
 * @param <T> 接口数据类型
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListActivity
 * @Description: 正常情况下 V T 的类型是一致的
 */
public abstract class AbsListActivity<V, T> extends BaseActivity implements UIController.IOperator<V, T> {
    private Class<T> tClass;
    private UIController<V, T> mController;
    private View contentView;
    private TitleBar titleBar;

    @Override
    public int setLayoutId() {
        return R.layout.activity_abs_list;
    }

    @Override
    public final void init() {
        tClass = (Class<T>) ObjUtil.getTType(getClass())[1];
        resetLayoutView();
        mController = new UIController(getLayout(), tClass, this);
        initView(contentView);
    }

    private void resetLayoutView() {
        initTitleBar();
        FrameLayout ll_content = getView(R.id.ll_content);
        contentView = setContentView();
        ll_content.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        //添加no_data到show_data同级
        View show_data = UIKit.getView(contentView, R.id.bsi_v_show_data);
        //未设置show_data布局 使用lv替代
        if (null == show_data) show_data = UIKit.getView(contentView, R.id.bsi_lv_base);
        ViewGroup extraParent = null != show_data ? (ViewGroup) show_data.getParent() : ll_content;
        extraParent.addView(UIKit.inflate(R.layout.no_data), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    private void initTitleBar() {
        titleBar = getView(R.id.bsi_v_titleBar);
        if (titleBar == null) {
            Logger.e(TAG, "init title_bar error for titleBar is null, " +
                    "are you set id of the view is 'bsi_v_show_data' !");
            return;
        }
        titleBar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackCode();
            }
        });
    }

    public void setQlTitle(String title) {
        if (null != titleBar) titleBar.setTitle(title, R.color.white);
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
     * @param netData 此次请求的数据
     */
    @Override
    public List<V> onPreRefreshData(List<T> netData, boolean isRefresh) {
        return (List<V>) netData;
    }

    /**
     * @param netData 设置给适配器的数据
     */
    @Override
    public List<V> onPreSetData(List<V> netData) {
        return netData;
    }

    @Override
    public void onError(int status, String errMsg) {
        Logger.e(TAG, "errMsg :" + errMsg);
    }

    public abstract View setContentView();

    public abstract void initView(View view);
}