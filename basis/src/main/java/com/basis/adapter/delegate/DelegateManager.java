package com.basis.adapter.delegate;

import com.basis.adapter.ViewHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:10
 * @className: DelegateManager
 * @Description: 复用控件 ItemDelegate的管理类
 */
public class DelegateManager<T> {
    /**
     * 类似但性能优于HashMap<Intager,Object>
     *     此处 key：viewType
     *     Value：convertView （Delegate）
     */
    HashMap<Integer,Delegate<T>> delegates = new HashMap();

    public int getDelegateCount() {
        return delegates.size();
    }

    /**
     * 添加
     * @param delegate
     * @return
     */
    public DelegateManager<T> addDelegate(Delegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    /**
     * 添加
     * @param viewType view类型
     * @param delegate
     * @return
     */
    public DelegateManager<T> addDelegate(int viewType, Delegate<T> delegate) {
        if (delegates.get(viewType) != null) {//该类型已存在
            throw new IllegalArgumentException(
                    "An Delegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered Delegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除
     * @param delegate
     * @return
     */
    public DelegateManager<T> removeDelegate(Delegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        Collection<Delegate<T>>  collection = delegates.values();
        collection.remove(delegate);
        return this;
    }

    /**
     * 移除 按指定类型
     * @param itemType
     * @return
     */
    public DelegateManager<T> removeDelegate(int itemType) {
        delegates.remove(itemType);
        return this;
    }

    /**
     * 获取view类型
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item, int position) {
        for (Map.Entry<Integer, Delegate<T>>  entry: delegates.entrySet()){
            Delegate<T> delegate = entry.getValue();
            if (delegate.enableDisplay(item, position)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException(
                "No Delegate added that matches position=" + position + " in data source");
    }

    /**
     * 复用
     * @param holder ViewHolder
     * @param item 数据
     * @param position 位置
     */
    public void convert(ViewHolder holder, T item, int position) {
        for (Map.Entry<Integer, Delegate<T>>  entry: delegates.entrySet()){
            Delegate<T> delegate = entry.getValue();
            if (delegate.enableDisplay(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No DelegateManager added that matches position=" + position + " in data source");
    }


    /**
     * 获取指定类型的id
     * @param viewType
     * @return
     */
    public int getItemLayoutId(int viewType) {
        return delegates.get(viewType).setItemViewLayoutId();
    }

    public int getItemViewType(Delegate itemDelegate) {
        for (Map.Entry<Integer, Delegate<T>>  entry: delegates.entrySet()){
            Delegate<T> delegate = entry.getValue();
            if (delegate == itemDelegate) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("No Delegate added");
    }

    public Delegate getItemDelegate(T item, int position) {
        for (Delegate<T> delegate: delegates.values()){
            if (delegate.enableDisplay(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No Delegate added that matches position=" + position + " in data source");
    }

    public int getItemLayoutId(T item, int position) {
        return getItemDelegate(item, position).setItemViewLayoutId();
    }
}
