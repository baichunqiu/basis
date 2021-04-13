package com.basis.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapter.interfaces.IHolder;
import com.basis.R;
import com.basis.net.Controller;
import com.basis.net.IOperator;
import com.basis.net.LoadTag;
import com.business.ILoadTag;
import com.business.interfaces.IParse;
import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;
import com.kit.UIKit;
import com.oklib.Method;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListFragment
 * @Description: 正常情况下 ND, AD 的类型是一致的
 */
public abstract class ListFragment<ND, AD,VH extends IHolder> extends BaseFragment implements IOperator<ND, AD,VH> {
    private Class<ND> tClass;
    private Controller<ND,AD, VH> controller;
    private View contentView;

    @Override
    public final int setLayoutId() {
        return R.layout.fragment_abs_list;
    }

    protected RecyclerView.LayoutManager onSetLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    public final void init() {
        resetLayoutView();
        tClass = (Class<ND>) ObjUtil.getTType(getClass())[0];
        controller = new Controller(getLayout(), tClass, this){
            @Override
            protected RecyclerView.LayoutManager onSetLayoutManager() {
                return ListFragment.this.onSetLayoutManager();
            }
        };
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
        View nodata = UIKit.inflate(R.layout.no_data);
        extraParent.addView(nodata, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public void getNetData(String tag, String mUrl, Map<String, Object> params, Method method, boolean isRefresh) {
        getNetData(tag, mUrl, params, null, method, isRefresh);
    }

    /**
     * @param tag       进度条显示msg
     * @param isRefresh 是否刷新
     * @param mUrl      mUrl
     * @param params    参数 注意：不包含page
     * @param method    Post/get
     * @param parser    解析器
     */
    public void getNetData(String tag, String mUrl, Map<String, Object> params, IParse parser, Method method, boolean isRefresh) {
        if (null != controller) {
            ILoadTag itag = TextUtils.isEmpty(tag) ? null : new LoadTag(activity, tag);
            controller.request(itag, mUrl, params, parser, method, isRefresh);
        }
    }

    public void refreshData(List<ND> netData, boolean isRefresh) {
        if (null != controller) controller.onRefreshData(netData, isRefresh);
    }

    @Override
    public void onCustomerRequestAgain(boolean refresh) {
        Logger.e(TAG, "onCustomerRequestAgain:refresh = " + refresh);
    }

    @Override
    public List<AD> onTransform(List<ND> netData) {
        return (List<AD>) netData;
    }

    @Override
    public List<AD> onPreSetData(List<AD> netData) {
        return netData;
    }

    @Override
    public void onError(int code, String msg) {
        Logger.e(TAG, "onAfter: [" + code + "] " + msg);
    }

    public abstract View setContentView();

    public abstract void initView(View view);
}
