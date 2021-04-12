package com.qunli.demo;

import android.view.View;
import android.widget.ImageView;

import com.adapter.SampleAdapter;
import com.adapter.interfaces.IAdapte;
import com.adapter.listview.LvHolder;
import com.basis.PreviewActivity;
import com.kit.UIKit;
import com.kit.utils.ImageLoader;
import com.oklib.Method;

public class ListActivity extends com.basis.base.ListActivity<Meizi, Meizi, LvHolder> {
    private IAdapte mAdapter;
    private int mCurPage = 1;

    @Override
    public View setContentView() {
        return UIKit.inflate(R.layout.activity_list_demo);
    }

    String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/20";

    public void initView(View view) {
        getWrapBar().setTitle(R.string.str_list_mv).work();
        getNetData(true, url, null, "", Method.get);
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
                            PreviewActivity.preview(mActivity, meizi.getUrl());
                        }
                    });
                }
            };
        }
        return mAdapter;
    }
}