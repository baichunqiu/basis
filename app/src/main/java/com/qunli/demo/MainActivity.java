package com.qunli.demo;

import android.view.View;

import com.basis.base.BaseActivity;
import com.basis.net.LoadTag;
import com.basis.widget.ActionBarWapper;
import com.basis.widget.WXDialog;
import com.basis.widget.interfaces.IBarWrap;
import com.business.ILoadTag;
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
                });
    }

    WXDialog dialog;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.widgetActivity:
                UIKit.startActivity(mActivity, ViewActivity.class);
                break;
            case R.id.load:
//                ILoadTag loadTag = new LoadTag(this);
//                loadTag.show();
//                WXDialog.showDefaultDialog(mActivity, "什么情况？", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
                new WXDialog(mActivity)
                        .setMessage("默认风格！")
                        .defalutStyle(true, null).show();
                new WXDialog(mActivity)
                        .setMessage("取消风格！")
                        .cancelStyle(true).show();
                new WXDialog(mActivity)
                        .setMessage("确定风格！")
                        .sureStyle(true,null).show();
                new WXDialog(mActivity)
                        .setMessage("删除风格！")
                        .deleteStyle(true,null).show();
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
