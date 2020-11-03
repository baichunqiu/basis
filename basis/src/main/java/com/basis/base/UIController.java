package com.basis.base;

import android.view.View;
import android.widget.ListView;

import com.basis.R;
import com.basis.adapter.BsiAdapter;
import com.bcq.net.callback.base.IRefreshView;
import com.bcq.net.controller.Controller;
import com.bcq.net.view.LoadTag;
import com.business.parse.Parser;
import com.kit.Logger;
import com.kit.UIKit;
import com.oklib.core.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: UIController
 * @Description: 供列表显示页面使用的控制器
 */
public class UIController<T> extends Controller<T> implements BsiAdapter.OnNoDataListeren {
    private final String TAG = "UIController";
    private IOperator operator;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List adapterList = new ArrayList<>();
    private BsiAdapter mAdapter;
    private IRefreshView refreshView;
    private View showData;
    private View noData;
    private View layout;

    public enum ShowType {
        Data,
        None
    }

    public UIController(View parent, Class<T> tclazz, IOperator<T> operator) {
        super(tclazz);
        this.layout = parent;
        this.operator = operator;
        initialize();
    }

    private void initialize() {
        showData = UIKit.getView(layout, R.id.bsi_v_show_data);
        noData = UIKit.getView(layout, R.id.bsi_v_no_data);
        refreshView = UIKit.getView(layout, R.id.bsi_lv_base);
        if (null == showData) showData = (View) refreshView;
        mAdapter = operator.setAdapter();
        mAdapter.setOnNoDataListeren(this);
        ((ListView) refreshView).setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new IRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestAgain(true);
            }
        });
        refreshView.setOnLoadListener(new IRefreshView.OnLoadListener() {
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


    public void request(final boolean isRefresh, String url, Map<String, Object> params, Parser parser, LoadTag loadBar, Method method) {
        super.request(isRefresh, url, params, parser, loadBar, method, operator, refreshView);
    }

    /**
     * 设置适配器数据回调
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    @Override
    public void onRefreshData(List<T> netData, boolean isRefresh) {
        //设置适配器前  数据处理
        List preData = operator.onPreRefreshData(netData, isRefresh);
        if (isRefresh) {
            adapterList.clear();
        }
        if (null != preData) {
            adapterList.addAll(preData);
        }
        List<T> temp = operator.onPreSetData(adapterList);
        if (null != temp && !temp.isEmpty()) {
            showViewType(ShowType.Data);
            mAdapter.setData(temp);
        } else {
            showViewType(ShowType.None);
        }
    }

    /**
     * 因适配器 removeItem 导致数据从有到无是回调
     * BsiAdapter.OnNoDataListeren 接口
     */
    @Override
    public void onNoData() {
        showViewType(ShowType.None);
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
     */
    public interface IOperator<T> extends IOperate<T> {
        /**
         * adapter设置数据前,数据处理。
         * 此处没使用泛型，特殊情况需要可能修改类型
         */
        List onPreRefreshData(List<T> netData, boolean isRefresh);

        /**
         * 设置Data to Adapter前 一般分页排序功能
         */
        List<T> onPreSetData(List<T> netData);

        BsiAdapter<T> setAdapter();

        @Override
        List<T> onPreprocess(List<T> rawData);

        @Override
        void onError(int status, String errMsg);
    }
}