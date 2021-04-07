package com;

import com.progress.Style;

public interface IRefresh {
    void setRefreshStyle(Style style);

    void setLoadStyle(Style style);

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
