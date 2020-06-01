package com.basis.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basis.adapter.delegate.Delegate;
import com.basis.adapter.delegate.DelegateManager;


/**
 * @author: BaiCQ
 * @createTime: 2017/2/28 10:12
 * @className:  MultiAdapter
 * @Description: 通用适配器:支持多类型viewType 数据和listview的显示要一致，
 *                例如：listview的第一个条目显示搜索栏，需在数据集postion=0添加占位数据
 */
public abstract class MultiAdapter<T> extends BsiAdapter<T> {

    private DelegateManager mDelegateManager;

    public MultiAdapter() {
        mDelegateManager = new DelegateManager();
    }

    protected final void init(){
        initDelegate();
        if (mDelegateManager.getDelegateCount() == 0){
            throw new IllegalArgumentException("No Delegate added for adapter，you may not call the mothed bind in  Delegate ?");
        }
    }

    /**
     * 添加复用view接口
     * @param delegate
     */
    public void addDelegate(Delegate<T> delegate) {
        mDelegateManager.addDelegate(delegate);
    }

    @Override
    public int getViewTypeCount() {
        int count = mDelegateManager.getDelegateCount();
        if (count > 0)return count;
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDelegateManager.getDelegateCount() > 0) {
            return mDelegateManager.getItemViewType(getItem(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T tData = getItem(position);
        Delegate delegate = mDelegateManager.getItemDelegate(tData, position);
        int layoutId = delegate.setItemViewLayoutId();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, convertView, parent);
            onHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //bind 数据
        delegate.convert(viewHolder,tData,position);
        return viewHolder.getConvertView();
    }

    protected void onHolderCreated(ViewHolder holder, View itemView) {}

    protected abstract void initDelegate();
}
