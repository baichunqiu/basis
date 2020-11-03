package com.basis.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BsiAdapter<T> extends BaseAdapter {
    protected Context mContext;
    private List<T> mDatas;

    /**
     * 绑定 context
     *
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
     *
     * @param datas
     */
    public final void setData(List<T> datas) {
        this.mDatas = handleData(datas);
        notifyDataSetChanged();
    }

    protected List<T> handleData(List<T> orgData) {
        return orgData;
    }

    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        int count = getCount();
        if (position < 0 || count == 0 || position >= count) return null;
        return mDatas.get(position);
    }

    public boolean removeItem(T item) {
        if (null == mDatas) return false;
        boolean flag = mDatas.remove(item);
        if (mDatas.isEmpty()) {
            if (null != onNoDataListeren) onNoDataListeren.onNoData();
        }
        return flag;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void release() {
        if (null != mDatas) mDatas.clear();
    }

    public void setOnNoDataListeren(OnNoDataListeren onNoDataListeren) {
        this.onNoDataListeren = onNoDataListeren;
    }

    private OnNoDataListeren onNoDataListeren;

    /**
     * 因适配器 removeItem删除数据时
     * 从有数据变为无数据是 需通知控制器 处理ui跟新
     */
    public interface OnNoDataListeren {
        void onNoData();
    }
}
