package com.xrecycle.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器
 *
 * @param <T>
 */
public abstract class RecycleAdapter<T> extends RecyclerView.Adapter<RecycleHolder> implements IRecycle<T> {
    protected Context context;
    private LayoutInflater inflater;
    // 布局id和itemType的映射关系
    private SparseArray<Integer> itemTypes;
    private List<T> data;

    public RecycleAdapter(Context context, int... itemLayoutId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        itemTypes = new SparseArray<>();
        data = new ArrayList<>();
        int[] ids = itemLayoutId;
        int len = ids == null ? 0 : ids.length;
        for (int i = 0; i < len; i++) {
            itemTypes.put(ids[i], i);
        }
    }

    @Override
    public void setData(List<T> list, boolean refresh) {
        int len = null == list ? 0 : list.size();
        if (len == 0) return;
        if (refresh) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        } else {
            data.addAll(list);
        }
    }

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutIdByViewType(viewType);
        if (layoutId < 0){
            throw new IllegalArgumentException("No ViewHolder Setted for ViewType =" + viewType);
        }
        return newCustomerHolder(parent,layoutId);
    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        int layoutId = getItemLayoutId(getItem(position), position);
        Integer type = itemTypes.get(layoutId);
        if (null == type){
            throw new IllegalArgumentException("No ViewType Setted for position =" + position);
        }
        return type;
    }
    
    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        T dt = getItem(position);
        int layout = getItemLayoutId(dt, position);
        convert(holder, dt, position, layout);
    }

    /************************以上RecyclerView.Adapter的方法*********************/
    @Override
    public T getItem(int position) {
        int count = getItemCount();
        if (position < 0 || count == 0 || position >= count) return null;
        return data.get(position);
    }

    /** 根据itemType 获取布局id*/
    private int getLayoutIdByViewType(int itemType) {
        int size = itemTypes.size();
        for (int i = 0; i < size; i++) {
            int layoutId = itemTypes.keyAt(i);
            Integer type = itemTypes.get(layoutId);
            if (type == itemType) {
                return layoutId;
            }
        }
        return -1;
    }

    /**
     * 若自定义ViewHolder可以继承复写该方法
     * @param parent
     * @param layoutId
     * @return
     */
    public RecycleHolder newCustomerHolder(ViewGroup parent, int layoutId){
        View itemView = inflater.inflate(layoutId,parent,false);
        return new RecycleHolder(itemView);
    }

    /**
     * 根据position 和 数据 获取itemView的布局id
     *
     * @param item
     * @param position
     * @return
     */
    @Override
    public abstract int getItemLayoutId(T item, int position);

    /**
     * 绑定数据
     *
     * @param holder
     * @param t
     * @param position 索引
     * @param layoutId 布局id 多种布局时返回
     */
    @Override
    public abstract void convert(RecycleHolder holder, T t, int position, int layoutId);
}
