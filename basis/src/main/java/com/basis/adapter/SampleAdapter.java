package com.basis.adapter;

import android.content.Context;

import com.basis.adapter.delegate.BaseDelegate;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:11
 * @className:  SampleAdapter
 * @Description: 一种viewType通用adapter
 */
public abstract class SampleAdapter<T> extends MultiAdapter<T> {

    private int layoutId;

    public SampleAdapter(Context context, int layoutId) {
        this.layoutId = layoutId;
        bindContext(context);
        init();
    }

    @Override
    protected void initDelegate() {
        new BaseDelegate<T>(layoutId) {
            @Override
            public boolean enableDisplay(T item, int position) {
                return true;
            }
            @Override
            public void convert(ViewHolder holder, T t, int position) {
                SampleAdapter.this.convert(holder, t, position);
            }
        }.bind(this);
    }

    /**
     * convertView的复用逻辑
     * @param viewHolder convertView对应的ViewHolder
     * @param item 数据类型的实体
     * @param position adpater中的位置索引
     */
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
