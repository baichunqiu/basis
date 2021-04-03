package com.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.adapter.interfaces.DataObserver;
import com.adapter.interfaces.IAdapte;
import com.adapter.interfaces.IHolder;
import com.adapter.listview.LvAdapter;
import com.adapter.listview.LvHolder;
import com.adapter.recycle.RcyAdapter;
import com.adapter.recycle.RcyHolder;

import java.util.List;

public abstract class RefreshAdapter<T> implements IAdapte<T, IHolder> {
    protected final Context context;
    private IAdapte adapter;
    private int[] layoutIds;

    public RefreshAdapter(Context context, int... layoutIds) {
        this.context = context;
        this.layoutIds = layoutIds;
    }

    public void setRefreshView(Object refreshView) {
        if (refreshView instanceof ListView) {
            ((ListView) refreshView).setAdapter(lvAdapter());
        } else if (refreshView instanceof RecyclerView) {
            ((RecyclerView) refreshView).setAdapter(recycleAdapter());
        } else {
            throw new IllegalArgumentException("No Support View Type :" + refreshView.getClass().getSimpleName());
        }
    }

    public BaseAdapter lvAdapter() {
        adapter = new LvAdapter<T>(context, layoutIds) {
            @Override
            public int getItemLayoutId(T item, int position) {
                return RefreshAdapter.this.getItemLayoutId(item, position);
            }

            @Override
            public void convert(LvHolder holder, T item, int position, int layoutId) {
                RefreshAdapter.this.convert(holder, item, position, layoutId);
            }
        };
        return (LvAdapter) adapter;
    }

    public RecyclerView.Adapter recycleAdapter() {
        adapter = new RcyAdapter<T>(context, layoutIds) {
            @Override
            public int getItemLayoutId(T item, int position) {
                return RefreshAdapter.this.getItemLayoutId(item, position);
            }

            @Override
            public void convert(RcyHolder holder, T item, int position, int layoutId) {
                RefreshAdapter.this.convert(holder, item, position, layoutId);
            }
        };
        return (RcyAdapter) adapter;
    }

    @Override
    public void setDataObserver(DataObserver observer) {
        if (null != adapter) adapter.setDataObserver(observer);
    }

    @Override
    public void setData(List<T> list, boolean refresh) {
        if (null != adapter) adapter.setData(list, refresh);
    }

    @Override
    public T getItem(int position) {
        if (null != adapter) adapter.getItem(position);
        return null;
    }

    @Override
    public boolean removeItem(T item) {
        if (null != adapter) return adapter.removeItem(item);
        return false;
    }

    @Override
    public abstract int getItemLayoutId(T item, int position);

    @Override
    public abstract void convert(IHolder iHolder, T t, int position, int layoutId);
}
