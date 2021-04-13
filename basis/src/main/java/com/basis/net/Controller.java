package com.basis.net;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.IRefresh;
import com.adapter.interfaces.DataObserver;
import com.adapter.interfaces.IAdapte;
import com.adapter.interfaces.IHolder;
import com.basis.R;
import com.basis.net.DefauPage;
import com.basis.net.IOperator;
import com.basis.net.LoadTag;
import com.business.interfaces.IParse;
import com.kit.UIKit;
import com.net.NetRefresher;
import com.net.Page;
import com.oklib.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @param <ND> 适配器数据类型
 * @param <AD> 接口数据类型
 * @param <VH> 接口数据类型
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: UIController
 * @Description: 供列表显示页面使用的控制器
 */
public class Controller<ND, AD, VH extends IHolder> extends NetRefresher<ND> implements DataObserver {
    private final String TAG = "UIController";
    //全局分页信息
    private final static Page page = new DefauPage();
    private IOperator<ND, AD, VH> operator;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List<AD> adapterList = new ArrayList<>();
    private IAdapte<AD, VH> mAdapter;
    private IRefresh refreshView;
    private View showData;
    private View noData;
    private View layout;

    public enum ShowType {
        Data,
        None
    }

    public Controller(View parent, Class<ND> tclazz, IOperator<ND, AD, VH> operator) {
        super(tclazz, page, operator);
        this.layout = parent;
        this.operator = operator;
        initialize();
    }

    private void initialize() {
        showData = UIKit.getView(layout, R.id.bsi_show_data);
        noData = UIKit.getView(layout, R.id.bsi_no_data);
        refreshView = UIKit.getView(layout, R.id.bsi_refresh);
        if (null == showData) showData = (View) refreshView;
        if (refreshView instanceof RecyclerView) {
            final GridLayoutManager layoutmanager = new GridLayoutManager(UIKit.getContext(), 2);
            ((RecyclerView) refreshView).setLayoutManager(layoutmanager);
        }
        refreshView.enableRefresh(true);
        refreshView.enableLoad(true);
        mAdapter = operator.onSetAdapter();
        mAdapter.setDataObserver(this);
        mAdapter.setRefreshView(refreshView);
        refreshView.setLoadListener(new IRefresh.LoadListener() {
            @Override
            public void onRefresh() {
                requestAgain(true, operator);
            }

            @Override
            public void onLoad() {
                requestAgain(false, operator);
            }
        });
        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAgain(true, operator);
            }
        });
    }


    public void request(final boolean isRefresh, String url, Map<String, Object> params, IParse parser, LoadTag loadBar, Method method) {
        super.request(isRefresh, url, params, parser, loadBar, method);
    }

    @Override
    public void onAfter(int code, String msg) {
        super.onAfter(code, msg);
        if (null != refreshView) {
            refreshView.refreshComplete();
            refreshView.loadComplete();
        }
    }

    /**
     * 设置适配器数据回调
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    @Override
    public void onRefreshData(List<ND> netData, boolean isRefresh) {
        /* 当页数据转换处理 */
        List<AD> preData = operator.onTransform(netData);
        if (isRefresh) adapterList.clear();
        if (null != preData) adapterList.addAll(preData);
        /* 设置适配器前 */
        List<AD> temp = operator.onPreSetData(adapterList);
        if (null != temp && !temp.isEmpty()) {
            showViewType(ShowType.Data);
            mAdapter.setData(temp, isRefresh);
        } else {
            showViewType(ShowType.None);
        }
    }

    /**
     * 因适配器 removeItem 导致数据从有到无是回调
     * BsiAdapter.OnNoDataListeren 接口
     */
    @Override
    public void onObserve(int length) {
        if (length == 0) {
            showViewType(ShowType.None);
        }
    }

    public final void showViewType(ShowType showType) {
        if (ShowType.Data == showType) {
            showData.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        } else {
            showData.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }
}