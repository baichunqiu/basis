package com.qunli.demo;

import android.view.View;
import android.widget.ImageView;

import com.bcq.adapter.SampleAdapter;
import com.bcq.adapter.interfaces.IAdapte;
import com.bcq.adapter.listview.LvHolder;
import com.basis.PreviewActivity;
import com.basis.base.ListActivity;
import com.kit.UIKit;
import com.kit.utils.ImageLoader;
import com.bcq.net.api.Method;

public class LvListActivity extends ListActivity<Meizi, Meizi, LvHolder> {
    private IAdapte mAdapter;
    private final int mCurPage = 1;

    @Override
    public int setLayoutId() {
        return R.layout.activity_list_demo;
    }

    String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/20";

    @Override
    public void initView() {
        getWrapBar().setTitle(R.string.str_list_mv).work();
        request("", url, null, Method.get, true);
    }

    @Override
    public IAdapte<Meizi, LvHolder> onSetAdapter() {
        if (null == mAdapter) {
            mAdapter = new SampleAdapter<Meizi, LvHolder>(this, R.layout.item_mz) {
                @Override
                public void convert(LvHolder holder, Meizi meizi, int position, int layoutId) {
                    ImageView imageView = holder.getView(R.id.img_content);
                    ImageLoader.loadUrl(imageView, meizi.getUrl(), R.mipmap.ic_launcher, ImageLoader.Size.SZ_250);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PreviewActivity.preview(activity, meizi.getUrl());
                        }
                    });
                }
            };
        }
        return mAdapter;
    }
}