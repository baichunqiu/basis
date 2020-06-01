package com.basis.adapter;

import android.content.Context;

import com.basis.adapter.delegate.BaseDelegate;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:09
 * @className: TopAdapter
 * @Description: position = 0 是特殊处理的 通用adapter
 */
public abstract class TopAdapter<T> extends MultiAdapter<T> {
    protected int layoutId;
    protected int TopId;

    private TopAdapter(Context context, int layoutId, int TopId) {
        this.layoutId = layoutId;
        this.TopId = TopId;
        bindContext(context);
        init();
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public T getItem(int position) {
        if (position == 0)return null;
        return super.getItem(position - 1);
    }

    @Override
    protected void initDelegate() {
        new BaseDelegate(TopId){
            @Override
            public boolean enableDisplay(Object item, int position) {
                return 0 == position;
            }
            @Override
            public void convert(ViewHolder holder, Object obj, int position) {
                convertTop(holder,obj,position);
            }
        }.bind(this);

        new BaseDelegate<T>(layoutId) {
            @Override
            public boolean enableDisplay(T item, int position) {
                return position > 0;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                TopAdapter.this.convert(holder, t, position);
            }
        }.bind(this);
    }

    protected abstract int convertTop(ViewHolder viewHolder, Object item, int position);

    protected abstract void convert(ViewHolder viewHolder, T item, int position);
}
