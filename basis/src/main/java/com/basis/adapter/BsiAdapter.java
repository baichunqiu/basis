package com.basis.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BsiAdapter<T> extends BaseAdapter {
    protected Context mContext;
    private List<T> mDatas;

    /**
     * 绑定 context
     * @param context
     */
    public BsiAdapter bindContext(Context context) {
        this.mContext = context;
        return this;
    }

    public List<T> getData() {
        return mDatas;
    }

    /**
     * 设置数据
     * @param datas
     */
    public final void setData(List<T> datas) {
        this.mDatas = handleData(datas);
        notifyDataSetChanged();
    }

    protected List<T> handleData(List<T> orgData){
        return orgData;
    }

    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        int count = getCount();
        if (position < 0 || count == 0 || position >= count)return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void release(){
        if (null != mDatas)mDatas.clear();
    }
}
