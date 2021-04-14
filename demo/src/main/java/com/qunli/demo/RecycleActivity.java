package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcq.refresh.IRefresh;
import com.bcq.adapter.interfaces.IAdapte;
import com.bcq.adapter.recycle.RcyAdapter;
import com.bcq.adapter.recycle.RcyHolder;
import com.bcq.adapter.recycle.RcySAdapter;
import com.bcq.net.wrapper.OkUtil;
import com.bcq.net.wrapper.Wrapper;
import com.kit.UIKit;
import com.kit.utils.ImageLoader;
import com.kit.utils.Logger;
import com.bcq.net.api.OCallBack;
import com.bcq.net.OkApi;

import java.util.List;

import okhttp3.Response;

public class RecycleActivity extends AppCompatActivity {
    private IRefresh refresh;
    private IAdapte mAdapter;
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
        refresh = findViewById(R.id.rv);
        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 2);
        ((RecyclerView) refresh).setLayoutManager(layoutmanager);
        refresh.enableRefresh(true);
        refresh.enableLoad(true);
        refresh.setLoadListener(new IRefresh.LoadListener() {
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
            mAdapter = new RcySAdapter<Meizi,RcyHolder>(this, R.layout.item_mz) {

                @Override
                public void convert(RcyHolder holder, Meizi gankMeizi, int position) {
                    ImageView imageView = holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, gankMeizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                }
            };
        }else {
            mAdapter = new RcyAdapter<Meizi,RcyHolder>(this, R.layout.item_mz, R.layout.item_info) {

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
        mAdapter.setRefreshView(refresh);
        fetchGankMZ(true);
    }

    private void fetchGankMZ(final boolean isRefresh) {
        String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/15";
        OkApi.get( url, null, new OCallBack<Wrapper>() {
            @Override
            public Wrapper onParse(Response response) throws Exception {
                int httpCode = response.code();
                String body = response.body().string();
                return BcqApplication.getParser().parse(httpCode, body);
            }

            @Override
            public void onResult(Wrapper wrapper) {
                List<Meizi> meizis = OkUtil.json2Obj(wrapper.getBody(), Meizi.class);
                int len = null == meizis ? 0 : meizis.size();
                Logger.e("AdapterActivity", "fetchGankMZ len = " + len);
                mAdapter.setData(meizis, isRefresh);
            }

            @Override
            public void onAfter() {
                refresh.loadComplete();
                refresh.refreshComplete();
            }
        });
    }
}