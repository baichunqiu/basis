package com.bcq.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.bcq.adapter.interfaces.DataObserver;
import com.bcq.adapter.interfaces.IAdapte;
import com.bcq.adapter.interfaces.IHolder;
import com.bcq.adapter.listview.LvAdapter;
import com.bcq.adapter.recycle.RcyAdapter;

import java.util.List;

public abstract class RefreshAdapter<T, VH extends IHolder> implements IAdapte<T, VH> {
    protected final Context context;
    private IAdapte adapter;
    private int[] layoutIds;

    public RefreshAdapter(Context context, int... layoutIds) {
        this.context = context;
        this.layoutIds = layoutIds;
    }

    @Override
    public <V extends View> void setRefreshView(V refreshView) {
        if (refreshView instanceof ListView) {
            ((ListView) refreshView).setAdapter(lvAdapter());
        } else if (refreshView instanceof RecyclerView) {
            ((RecyclerView) refreshView).setAdapter(recycleAdapter());
        } else {
            throw new IllegalArgumentException("No Support View Type :" + refreshView.getClass().getSimpleName());
        }
    }

    public BaseAdapter lvAdapter() {
        adapter = new LvAdapter<T, VH>(context, layoutIds) {
            @Override
            public int getItemLayoutId(T item, int position) {
                return RefreshAdapter.this.getItemLayoutId(item, position);
            }

            @Override
            public void convert(VH holder, T item, int position, int layoutId) {
                RefreshAdapter.this.convert(holder, item, position, layoutId);
            }
        };
        return (LvAdapter) adapter;
    }

    public RecyclerView.Adapter recycleAdapter() {
        adapter = new RcyAdapter<T, VH>(context, layoutIds) {
            @Override
            public int getItemLayoutId(T item, int position) {
                return RefreshAdapter.this.getItemLayoutId(item, position);
            }

            @Override
            public void convert(VH holder, T item, int position, int layoutId) {
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
    public abstract void convert(VH iHolder, T t, int position, int layoutId);
}
