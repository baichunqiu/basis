package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.IRefresh;
import com.adapter.interfaces.IAdapte;
import com.adapter.interfaces.IHolder;
import com.adapter.listview.LvAdapter;
import com.adapter.listview.LvHolder;
import com.adapter.listview.LvSAdapter;
import com.business.OkUtil;
import com.business.Wrapper;
import com.kit.UIKit;
import com.kit.cache.GsonUtil;
import com.kit.utils.ImageLoader;
import com.kit.utils.Logger;
import com.oklib.OCallBack;
import com.oklib.OkApi;

import java.util.List;

import okhttp3.Response;

public class RefreshActivity extends AppCompatActivity {
    private IRefresh refresh;
    private IAdapte mAdapter;
    private int mCurPage = 1;
    private boolean sample = false;
    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        sample = getIntent().getBooleanExtra(UIKit.KEY_BASE,false);
        init();
    }

    public void init() {
        refresh = findViewById(R.id.rv);
        refresh.enableLoad(true);
        refresh.enableRefresh(true);
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
            mAdapter = new LvSAdapter<Meizi, LvHolder>(this, R.layout.item_mz) {
                @Override
                public void convert(LvHolder holder, Meizi meizi, int position) {
                    ImageView imageView = holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, meizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                }
            };
        }else {
            mAdapter = new LvAdapter<Meizi,LvHolder>(this, R.layout.item_mz, R.layout.item_info) {
                @Override
                public int getItemLayoutId(Meizi item, int position) {
                    return position % 2 == 0 ? R.layout.item_mz : R.layout.item_info;
                }

                @Override
                public void convert(LvHolder holder, Meizi meizi, int position, int layoutId) {
                    if (layoutId == R.layout.item_mz) {
                        ImageView imageView = holder.getView(R.id.img_content);
                        ImageLoader.loadUrl(imageView, meizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                    } else {
                        holder.setText(R.id.tv_info, meizi.getDesc());
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