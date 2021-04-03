package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.IRefresh;
import com.adapter.recycle.RcyAdapter;
import com.adapter.recycle.RcyHolder;
import com.adapter.recycle.RcySAdapter;
import com.business.parse.Wrapper;
import com.kit.UIKit;
import com.kit.cache.GsonUtil;
import com.kit.utils.ImageLoader;
import com.oklib.OkApi;
import com.oklib.callback.BaseCallBack;
import com.xrecycle.ProgressStyle;
import com.xrecycle.XRecyclerView;

import java.util.List;

import okhttp3.Response;

public class RecycleActivity extends AppCompatActivity {
    private XRecyclerView recyclerView;
    private RcyAdapter mAdapter;
    private int mCurPage = 1;
    private boolean sample = false;
    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycle);
        sample = getIntent().getBooleanExtra(UIKit.KEY_BASE,false);
        init();
    }

    public void init() {
        recyclerView = findViewById(R.id.rv);
        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.enableRefresh(true);
        recyclerView.enableLoad(true);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.getDefaultFootView().setNoMoreHint("自定义加载完毕提示");
        recyclerView.setLoadListener(new IRefresh.LoadListener() {
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
        if (sample) {
            mAdapter = new RcySAdapter<Meizi>(this, R.layout.item_mz) {

                @Override
                public void convert(RcyHolder holder, Meizi gankMeizi, int position) {
                    ImageView imageView = holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, gankMeizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                }
            };
        }else {
            mAdapter = new RcyAdapter<Meizi>(this, R.layout.item_mz, R.layout.item_info) {

                @Override
                public int getItemLayoutId(Meizi item, int position) {
                    return position % 2 == 0 ? R.layout.item_mz : R.layout.item_info;
                }

                @Override
                public void convert(RcyHolder holder, Meizi gankMeizi, int position, int layoutId) {
                    if (layoutId == R.layout.item_mz) {
                        ImageView imageView = holder.getView(R.id.img_content);
                        ImageLoader.loadUrl(imageView, gankMeizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                    } else {
                        holder.setText(R.id.tv_info, gankMeizi.getDesc());
                    }
                }
            };
        }
        recyclerView.setAdapter(mAdapter);
        fetchGankMZ(true);
    }

    private void fetchGankMZ(final boolean isRefresh) {
        String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/15";
        OkApi.get( url, null, new BaseCallBack<Wrapper>() {
            @Override
            public Wrapper onParse(Response response) throws Exception {
                int httpCode = response.code();
                String body = response.body().string();
                Wrapper wrapper = BcqApplication.getParser().parse(httpCode,body);
                return wrapper;
            }
            @Override
            public void onResponse(Wrapper wrapper) {
                List<Meizi> meizis = GsonUtil.json2List(wrapper.getBody(),Meizi.class);
                int len = null != meizis?0:meizis.size();
                mAdapter.setData(meizis, isRefresh);
            }

            @Override
            public void onAfter() {
                recyclerView.loadComplete();
                recyclerView.refreshComplete();
            }
        });
    }

}
