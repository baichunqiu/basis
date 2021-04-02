package com.xrecycle;

public interface IXRecycle {

    void refresh();

    void setNoMore(boolean noMore);

    void refreshComplete();

    void loadComplete();

    void setLoadListener(LoadListener listener);

    interface LoadListener {

        void onRefresh();

        void onLoad();
    }
}
