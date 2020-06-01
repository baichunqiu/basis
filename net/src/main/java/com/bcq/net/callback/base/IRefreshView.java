package com.bcq.net.callback.base;


/**
 * @author: BaiCQ
 * @className: BaseListView
 * @Description: 所有刷新listview的基类
 */
public interface IRefreshView {

    /**
     * 刷新完毕，结束刷新动画
     */
    void onRefreshComplete();

    /**
     * 添加刷新监听
     *
     * @param onRefreshListener
     */
    void setOnRefreshListener(OnRefreshListener onRefreshListener);

    /*
     * 加载完毕，结束加载动画
     */
    void onLoadComplete();

    /**
     * 添加load监听
     *
     * @param onRefreshListener
     */
    void setOnLoadListener(OnLoadListener onRefreshListener);

    /**
     * 设置加载完毕 没有更多数据
     */
    void setLoadFull(boolean isLoadFull);

    interface OnLoadListener {
        void onLoad();
    }

    interface OnRefreshListener {
        void onRefresh();
    }
}

