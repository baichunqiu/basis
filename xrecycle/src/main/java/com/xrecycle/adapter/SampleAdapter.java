package com.xrecycle.adapter;

import android.content.Context;

/**
 * 通用适配器
 *
 * @param <T>
 */
public abstract class SampleAdapter<T> extends RecycleAdapter<T> {

    private int layoutId;

    public SampleAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
        this.layoutId = itemLayoutId;
    }

    @Override
    public int getItemLayoutId(T item, int position) {
        return layoutId;
    }

    @Override
    public void convert(RecycleHolder holder, T t, int position, int layoutId) {
        convert(holder, t, position);
    }

    public abstract void convert(RecycleHolder holder, T t, int position);
}
