package com.qunli.demo;

import android.view.View;

import com.basis.base.BaseActivity;
import com.basis.net.LoadTag;
import com.basis.widget.ActionBarWapper;
import com.basis.widget.IBarWrap;
import com.kit.UIKit;
import com.kit.utils.Logger;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        getView(R.id.load).setOnClickListener(this);
        getView(R.id.widgetActivity).setOnClickListener(this);
        getView(R.id.recycle).setOnClickListener(this);
        getView(R.id.listui).setOnClickListener(this);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 600; i++) {
            buffer.append("#123456789");
        }
        Logger.e(buffer.toString());
    }

    @Override
    public void onInitBarWrapper(IBarWrap wrap) {
        wrap.setHide(false)
                .setTitle(R.string.no_data)
                .addOptionMenu("设置")
                .addOptionMenu("关于")
                .addOptionMenu("新闻")
                .addOptionMenu("搜索", R.mipmap.ic_search)
                .addOptionMenu("分享")
                .setOnMenuSelectedListener(new ActionBarWapper.OnMenuSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        Logger.e(TAG, "onItemSelected:" + position);
                    }
                }).inflate();
    }

    //    @Override
//    public ActionBarWapper onSetBarWrapper() {
//        return wapper = new ActionBarWapper(mActivity)
//                .setHide(false)
//                .setTitle(R.string.no_data)
//                .addOptionMenu("设置")
//                .addOptionMenu("关于")
//                .addOptionMenu("新闻")
//                .addOptionMenu(R.mipmap.ic_search)
//                .addOptionMenu("分享")
//                .setOnMenuSelectedListener(new ActionBarWapper.OnMenuSelectedListener() {
//                    @Override
//                    public void onItemSelected(int position) {
//                        Logger.e(TAG,"onItemSelected:"+position);
//                    }
//                }).inflate();
//    }

    private LoadTag loadTag;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.widgetActivity:
                UIKit.startActivity(mActivity, ViewActivity.class);
                break;
            case R.id.load:
                loadTag = new LoadTag(this);
                loadTag.show();
                break;
            case R.id.recycle:
                UIKit.startActivity(mActivity, RecycleActivity.class);
                break;
            case R.id.listui:
                UIKit.startActivity(mActivity, ListActivity.class);
                break;
        }
    }
}
