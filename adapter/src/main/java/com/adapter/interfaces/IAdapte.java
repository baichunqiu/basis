package com.adapter.interfaces;

import java.util.List;

/**
 * 通用适配器接口
 */
public interface IAdapte<T,VH extends IHolder> {

    void setDataObserver(DataObserver observer);
    /**
     * 设置数据
     *
     * @param list
     * @param refresh
     */
    void setData(List<T> list, boolean refresh);


    /**
     * 获取指定索引的数据对象
     *
     * @param position
     * @return
     */
    T getItem(int position);

    /**
     * 移除
     * @param item
     * @return
     */
    boolean removeItem(T item);
    /**
     * 根据position 和 数据 获取itemView的布局id
     *
     * @param item
     * @param position
     * @return
     */
    int getItemLayoutId(T item, int position);

    /**
     * 绑定数据
     *
     * @param vh
     * @param t
     * @param position 索引
     * @param layoutId 布局id 多种布局时返回
     */
    void convert(VH vh, T t, int position, int layoutId);
}
