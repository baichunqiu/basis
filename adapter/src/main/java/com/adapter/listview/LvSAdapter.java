package com.adapter.listview;

import android.content.Context;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:11
 * @className:  Sample Adapter
 * @Description: 一种viewType通用adapter
 */
public abstract class LvSAdapter<T> extends LvAdapter<T> {
    private int layoutId;

    public LvSAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.layoutId = itemLayoutId;
    }

    @Override
    public int getItemLayoutId(T item, int position) {
        return layoutId;
    }

    @Override
    public void convert(LvHolder holder, T t, int position, int layoutId) {
        convert(holder, t, position);
    }

    public abstract void convert(LvHolder holder, T t, int position);

}
