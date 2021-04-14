package com.basis.net;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcq.adapter.interfaces.DataObserver;
import com.bcq.adapter.interfaces.IAdapte;
import com.bcq.adapter.interfaces.IHolder;
import com.bcq.net.net.NetRefresher;
import com.bcq.net.net.Page;
import com.bcq.refresh.IRefresh;
import com.kit.UIKit;
import com.kit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

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
    private final String TAG = "Controller";
    //全局分页信息
    private final static Page page = new DefauPage();
    private IOperator<ND, AD, VH> operator;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List<AD> adapterList = new ArrayList<>();
    private IAdapte<AD, VH> mAdapter;
    private VHolder holder;

    protected RecyclerView.LayoutManager onSetLayoutManager() {
        return new LinearLayoutManager(UIKit.getContext());
    }

    public Controller(VHolder holder, Class<ND> tclazz, IOperator<ND, AD, VH> operator) {
        super(tclazz, page, operator);
        this.holder = holder;
        this.operator = operator;
        initialize();
    }

    private void initialize() {
        holder.refresh.enableRefresh(true);
        holder.refresh.enableLoad(true);
        if (holder.refresh instanceof RecyclerView) {
            ((RecyclerView) holder.refresh).setLayoutManager(onSetLayoutManager());
        }
        mAdapter = operator.onSetAdapter();
        mAdapter.setDataObserver(this);
        mAdapter.setRefreshView((View) holder.refresh);
        holder.refresh.setLoadListener(new IRefresh.LoadListener() {
            @Override
            public void onRefresh() {
                requestAgain(true, operator);
            }

            @Override
            public void onLoad() {
                requestAgain(false, operator);
            }
        });
        holder.none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAgain(true, operator);
            }
        });
    }

    @Override
    public void onAfter() {
        super.onAfter();
        if (null != holder && null != holder.refresh) {
            holder.refresh.refreshComplete();
            holder.refresh.loadComplete();
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
            if (null != holder) holder.showType(VHolder.Type.show);
            mAdapter.setData(temp, isRefresh);
        } else {
            if (null != holder) holder.showType(VHolder.Type.none);
        }
    }

    /**
     * 因适配器 removeItem 导致数据从有到无是回调
     * BsiAdapter.OnNoDataListeren 接口
     */
    @Override
    public void onObserve(int length) {
        Logger.e(TAG, "onObserve: len = " + length);
        if (length == 0) {
            if (null != holder) holder.showType(VHolder.Type.none);
        }
    }
}