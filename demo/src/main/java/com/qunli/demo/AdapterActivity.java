package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.IRefresh;
import com.adapter.RefreshAdapter;
import com.adapter.interfaces.IHolder;
import com.business.Wrapper;
import com.kit.UIKit;
import com.kit.cache.GsonUtil;
import com.kit.utils.ImageLoader;
import com.kit.utils.Logger;
import com.oklib.OCallBack;
import com.oklib.OkApi;
import com.xrecycle.XRecyclerView;

import java.util.List;

import okhttp3.Response;

public class AdapterActivity extends AppCompatActivity {
    private IRefresh iRefresh;
    private RefreshAdapter<Meizi> mAdapter;
    private int mCurPage = 1;
    private boolean listview = false;

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview = getIntent().getBooleanExtra(UIKit.KEY_BASE, false);
        setContentView(listview ? R.layout.activity_refresh : R.layout.activity_xrecycle);
        init();
    }

    public void init() {
        iRefresh = findViewById(R.id.rv);
        iRefresh.enableRefresh(true);
        iRefresh.enableLoad(true);
        if (!listview) {
            XRecyclerView recyclerView = (XRecyclerView) iRefresh;
            final GridLayoutManager layoutmanager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutmanager);
//            recyclerView.setRefreshStyle(Style.BallSpinFadeLoader);
//            recyclerView.setLoadStyle(Style.BallRotate);
            recyclerView
                    .getDefaultRefreshHeaderView()
                    .setRefreshTimeVisible(true);
        }
        iRefresh.setLoadListener(new IRefresh.LoadListener() {
            @Override
            public void onRefresh() {
                mCurPage = 1;
                fetchGankMZ(true);
            }

            @Override
            public void onLoad() {
                mCurPage++;
                fetchGankMZ(false);
            }
        });
        mAdapter = new RefreshAdapter<Meizi>(this, R.layout.item_mz, R.layout.item_info) {
            @Override
            public int getItemLayoutId(Meizi item, int position) {
                return position % 2 == 0 ? R.layout.item_mz : R.layout.item_info;
            }

            @Override
            public void convert(IHolder holder, Meizi meizi, int position, int layoutId) {
                if (layoutId == R.layout.item_mz) {
                    ImageView imageView = (ImageView) holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, meizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                } else {
                    holder.setText(R.id.tv_info, meizi.getDesc());
                }
            }
        };
        mAdapter.setRefreshView(iRefresh);
        fetchGankMZ(true);
    }

    private void fetchGankMZ(final boolean isRefresh) {
        String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/15";
        OkApi.get(url, null, new OCallBack<Wrapper>() {
            @Override
            public Wrapper onParse(Response response) throws Exception {
                int httpCode = response.code();
                String body = response.body().string();
                return BcqApplication.getParser().parse(httpCode, body);
            }

            @Override
            public void onResult(Wrapper wrapper) {
                List<Meizi> meizis = GsonUtil.json2List(wrapper.getBody(), Meizi.class);
                int len = null == meizis ? 0 : meizis.size();
                Logger.e("AdapterActivity", "fetchGankMZ len = " + len);
                mAdapter.setData(meizis, isRefresh);
            }

            @Override
            public void onAfter() {
                iRefresh.loadComplete();
                iRefresh.refreshComplete();
            }
        });
    }
}
