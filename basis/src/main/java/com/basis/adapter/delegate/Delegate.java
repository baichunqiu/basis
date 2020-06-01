package com.basis.adapter.delegate;


import com.basis.adapter.MultiAdapter;
import com.basis.adapter.ViewHolder;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:07
 * @className: Delegate
 * @Description: 每个类型的view都对应一个实例
 */
public interface Delegate<T> {
    /**
     * 填充该itemView的布局文件id
     */
    int setItemViewLayoutId();

    /**
     * 绑定适配器
     *
     * @param multiAdapter
     */
    void bind(MultiAdapter<T> multiAdapter);

    /**
     * 什么条件下显示该itemView 用户通过item 和 position判断
     * @param item
     * @param position
     */
    boolean enableDisplay(T item, int position);

    /**
     * 复用 重新设置itemView的数据
     *
     * @param holder
     * @param t
     * @param position
     */
    void convert(ViewHolder holder, T t, int position);

}
