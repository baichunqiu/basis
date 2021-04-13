package com.qunli.demo;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapter.SampleAdapter;
import com.adapter.interfaces.IAdapte;
import com.adapter.recycle.RcyHolder;
import com.basis.PreviewActivity;
import com.basis.base.ListActivity;
import com.kit.UIKit;
import com.kit.utils.ImageLoader;
import com.oklib.Method;

public class RcyListActivity extends ListActivity<Meizi, Meizi, RcyHolder> {
    private IAdapte<Meizi, RcyHolder> mAdapter;
    private int mCurPage = 1;

    @Override
    public View setContentView() {
        return UIKit.inflate(R.layout.activity_rcyle_demo);
    }

    String url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/" + mCurPage + "/count/20";

    public void initView(View view) {
        getWrapBar().setTitle(R.string.str_list_mv).work();
        getNetData( "",url, null , Method.get,true);
    }

    @Override
    protected RecyclerView.LayoutManager onSetLayoutManager() {
//        return new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        return new GridLayoutManager(activity,3);
    }

    @Override
    public IAdapte<Meizi, RcyHolder> onSetAdapter() {
        if (null == mAdapter) {
            mAdapter = new SampleAdapter<Meizi, RcyHolder>(this, R.layout.item_mz) {
                @Override
                public void convert(RcyHolder holder, Meizi meizi, int position, int layoutId) {
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