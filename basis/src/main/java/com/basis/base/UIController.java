package com.basis.base;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.IRefresh;
import com.adapter.interfaces.DataObserver;
import com.adapter.interfaces.IAdapte;
import com.adapter.interfaces.IHolder;
import com.basis.R;
import com.basis.net.LoadTag;
import com.basis.net.controller.Controller;
import com.business.interfaces.IParse;
import com.kit.UIKit;
import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;
import com.oklib.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: UIController
 * @Description: 供列表显示页面使用的控制器
 * @param <ND> 适配器数据类型
 * @param <AD> 接口数据类型
 * @param <VH> 接口数据类型
 */
public class UIController<ND, AD, VH extends IHolder> extends Controller<ND> implements DataObserver {
    private final String TAG = "UIController";
    private IOperator<ND, AD,VH> operator;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List adapterList = new ArrayList<>();
    private IAdapte<AD, VH> mAdapter;
    private IRefresh refreshView;
    private View showData;
    private View noData;
    private View layout;

    public enum ShowType {
        Data,
        None
    }

    public UIController(View parent, Class<ND> tclazz, IOperator<ND, AD,VH> operator) {
        super(tclazz);
        this.layout = parent;
        this.operator = operator;
        initialize();
        Class[] tClass = ObjUtil.getTType(getClass());
        Logger.e(TAG,"tClass len = "+(null ==tClass?0:tClass.length));
    }

    private void initialize() {
        showData = UIKit.getView(layout, R.id.bsi_show_data);
        noData = UIKit.getView(layout, R.id.bsi_no_data);
        refreshView = UIKit.getView(layout, R.id.bsi_refresh);
        if (null == showData) showData = (View) refreshView;
        if (refreshView instanceof RecyclerView){
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
                requestAgain(true);
            }

            @Override
            public void onLoad() {
                requestAgain(false);
            }
        });
        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAgain(true);
            }
        });
    }


    public void request(final boolean isRefresh, String url, Map<String, Object> params, IParse parser, LoadTag loadBar, Method method) {
        super.request(isRefresh, url, params, parser, loadBar, method, operator, refreshView);
    }

    /**
     * 设置适配器数据回调
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    @Override
    public void onRefreshData(List<ND> netData, boolean isRefresh) {
        //设置适配器前  数据处理
        List<AD> preData = operator.onPreRefreshData(netData, isRefresh);
        if (isRefresh) {
            adapterList.clear();
        }
        if (null != preData) {
            adapterList.addAll(preData);
        }
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

    /**
     * Model 处理抽象接口
     *
     * @param <ND> net data 接口数据类型
     * @param <AD> adapter data 适配器数据类型 一般情况：和ND类型一致
     * @param <VH> 适配器的view holder类型
     */
    public interface IOperator<ND, AD, VH extends IHolder> extends IOperate<ND> {

        /**
         * 刷新数据前回调
         * 此处没使用泛型，特殊情况需要可能修改类型
         *
         * @param netData   当次（当前页）网络数据
         * @param isRefresh 刷新标识
         * @return
         */
        List<AD> onPreRefreshData(List<ND> netData, boolean isRefresh);

        /**
         * 设置adapter数据前回调
         * 一般分页排序功能获取拼接数据时使用
         *
         * @param netData 设置给adapter的所有（包含所有页码）数据
         * @return 返回数据集合直接设置给adapter，会执行showViewType()修改ui
         */
        List<AD> onPreSetData(List<AD> netData);

        IAdapte<AD, VH> onSetAdapter();

        @Override
        void onError(int status, String errMsg);
    }
}