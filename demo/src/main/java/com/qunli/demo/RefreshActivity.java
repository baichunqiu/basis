package com.qunli.demo;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.IRefresh;
import com.adapter.listview.LvAdapter;
import com.adapter.listview.LvSAdapter;
import com.adapter.listview.LvHolder;
import com.business.parse.Wrapper;
import com.kit.UIKit;
import com.kit.cache.GsonUtil;
import com.kit.utils.ImageLoader;
import com.oklib.OkApi;
import com.oklib.callback.BaseCallBack;
import com.listview.RefreshListView;

import java.util.List;

import okhttp3.Response;

public class RefreshActivity extends AppCompatActivity {
    private RefreshListView listView;
    private LvAdapter mAdapter;
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
        listView = findViewById(R.id.rv);
        listView.enableLoad(true);
        listView.enableRefresh(true);
        listView.setLoadListener(new IRefresh.LoadListener() {
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
            mAdapter = new LvSAdapter<Meizi>(this, R.layout.item_mz) {
                @Override
                public void convert(LvHolder holder, Meizi meizi, int position) {
                    ImageView imageView = holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, meizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                }
            };
        }else {
            mAdapter = new LvAdapter<Meizi>(this, R.layout.item_mz, R.layout.item_info) {
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
        listView.setAdapter(mAdapter);
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
                listView.loadComplete();
                listView.refreshComplete();
            }
        });
    }
}
