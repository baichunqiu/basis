package com;

public interface IRefresh {
    void enableRefresh(boolean enable);

    void enableLoad(boolean enable);

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
