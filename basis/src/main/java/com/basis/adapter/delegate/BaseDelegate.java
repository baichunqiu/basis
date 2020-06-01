package com.basis.adapter.delegate;

import com.basis.adapter.MultiAdapter;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:07
 * @className: BaseDelegate
 * @Description: 接口ItemDelegate的抽象实现
 */
public abstract class BaseDelegate<T> implements Delegate<T> {
    private int layoutId;

    public BaseDelegate(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int setItemViewLayoutId() {
        return layoutId;
    }

    @Override
    public void bind(MultiAdapter<T> multiAdapter) {
        multiAdapter.addDelegate(this);
    }
}
