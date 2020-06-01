package com.basis.adapter;

import android.content.Context;

import com.basis.adapter.delegate.BaseDelegate;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:10
 * @className: ComplexAdapter
 * @Description: 多种类型View的通用adapter
 */
public abstract class ComplexAdapter<T> extends MultiAdapter<T> {
    private int[] layoutIds;

    public ComplexAdapter(Context context) {
        bindContext(context);
        layoutIds = setLayoutIds();
        init();
    }

    @Override
    protected final void initDelegate() {
        int count = layoutIds.length;
        for (int i = 0; i < count; i++) {
            final int layoutId = layoutIds[i];
            new BaseDelegate<T>(layoutId) {
                @Override
                public boolean enableDisplay(T item, int position) {
                    return ComplexAdapter.this.enableDisplay(layoutId, item, position);
                }

                @Override
                public void convert(ViewHolder holder, T t, int position) {
                    ComplexAdapter.this.convert(layoutId, holder, t, position);
                }
            }.bind(this);
        }
    }

    protected abstract int[] setLayoutIds();

    protected abstract boolean enableDisplay(int layoutId, T t, int postion);

    protected abstract void convert(int layoutId, ViewHolder holder, T t, int position);
}
