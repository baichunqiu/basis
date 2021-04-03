package com.adapter;

import android.content.Context;

import com.adapter.interfaces.IHolder;

public abstract class SampleAdapter<T> extends RefreshAdapter<T> {
    private int layoutId;

    public SampleAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.layoutId = layoutId;

    }

    @Override
    public int getItemLayoutId(T item, int position) {
        return layoutId;
    }

    @Override
    public abstract void convert(IHolder iHolder, T t, int position, int layoutId);
}
