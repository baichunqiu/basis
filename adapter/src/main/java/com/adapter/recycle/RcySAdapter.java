package com.adapter.recycle;

import android.content.Context;

/**
 * 通用适配器
 *
 * @param <T>
 */
public abstract class RcySAdapter<T> extends RcyAdapter<T> {

    private int layoutId;

    public RcySAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.layoutId = itemLayoutId;
    }

    @Override
    public int getItemLayoutId(T item, int position) {
        return layoutId;
    }

    @Override
    public void convert(RcyHolder holder, T t, int position, int layoutId) {
        convert(holder, t, position);
    }

    public abstract void convert(RcyHolder holder, T t, int position);
}
