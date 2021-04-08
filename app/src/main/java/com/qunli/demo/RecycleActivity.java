package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.IRefresh;
import com.adapter.interfaces.IAdapte;
import com.adapter.recycle.RcyHolder;
import com.adapter.recycle.RcySAdapter;
import com.basis.net.LoadTag;
import com.basis.net.Request;
import com.basis.net.callback.ListCallback;
import com.kit.utils.ImageLoader;
import com.kit.utils.Logger;
import com.oklib.Method;

import java.util.List;

public class RecycleActivity extends AppCompatActivity {
    private IRefresh refresh;
    private IAdapte mAdapter;
    private int mCurPage = 1;
    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycle);
        init();
    }

    public void init() {
        refresh = findViewById(R.id.rv);
        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 2);
        ((RecyclerView) refresh).setLayoutManager(layoutmanager);
        refresh.enableRefresh(true);
        refresh.enableLoad(true);
        refresh.setLoadListener(new IRefresh.LoadListener() {
            @Override
            public void onRefresh() {
                mCurPage = 1;
                fetchGankMZ(true,false);
            }

            @Override
            public void onLoad() {
                mCurPage++;
                fetchGankMZ(false,false);
            }
        });
        mAdapter = new RcySAdapter<Meizi>(this, R.layout.item_mz) {

            @Override
            public void convert(RcyHolder holder, Meizi gankMeizi, int position) {
                ImageView imageView = holder.getView(R.id.img_content);
                ImageLoader.loadUrl(imageView, gankMeizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
            }
        };
        mAdapter.setRefreshView(refresh);
        fetchGankMZ(true,true);
    }

    private void fetchGankMZ(final boolean isRefresh,boolean show) {
        String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/20";
        Request.request(show?new LoadTag(this):null, url, null, Method.get, new ListCallback<Meizi>(refresh){
            @Override
            public void onSuccess(List<Meizi> meizis, Boolean loadFull) {
                super.onSuccess(meizis,loadFull);
                int len = null == meizis ? 0 : meizis.size();
                Logger.e("AdapterActivity", "fetchGankMZ len = " + len);
                mAdapter.setData(meizis, isRefresh);
                if (loadFull) {
                    mCurPage--;
                }
            }

            @Override
            public void onError(int code, String errMsg) {
                super.onError(code, errMsg);
            }
        });
    }
}