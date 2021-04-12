package com.qunli.demo;

import android.net.Uri;
import android.view.View;

import com.basis.PreviewActivity;
import com.basis.base.BaseActivity;
import com.basis.widget.ActionWrapBar;
import com.basis.widget.WXDialog;
import com.kit.UIKit;
import com.kit.utils.KToast;
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
        getView(R.id.wrap_bar).setOnClickListener(this);
        getView(R.id.preview).setOnClickListener(this);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 600; i++) {
            buffer.append("#123456789");
        }
        Logger.e(buffer.toString());
        initBarWrapper();
    }

    public void initBarWrapper() {
        getWrapBar().setTitle(R.string.no_data)
                .setBackHide(true)
                .addOptionMenu("分享")
                .addOptionMenu("搜索", R.mipmap.ic_search)
                .setOnMenuSelectedListener(new ActionWrapBar.OnMenuSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        KToast.show(position == 0 ? "分享" : "搜索");
                    }
                }).work();
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
                        .sureStyle(true, null).show();
                new WXDialog(mActivity)
                        .setMessage("删除风格！")
                        .deleteStyle(true, null).show();
                break;
            case R.id.recycle:
                UIKit.startActivity(mActivity, RecycleActivity.class);
                break;
            case R.id.listui:
                UIKit.startActivity(mActivity, ListActivity.class);
                break;
            case R.id.wrap_bar:
                hide = !hide;
                getWrapBar().setHide(hide).work();
                break;
                case R.id.preview:
//                Intent intent = new Intent(getApplicationInfo().packageName + ".preview");
//                intent.addCategory("android.intent.category.DEFAULT");
//                startActivity(intent);
                    Uri uri = Uri.parse("https://images.unsplash.com/photo-1577643816920-65b43ba99fba?ixlib=rb-1.2.1&auto=format&fit=crop&w=3300&q=80");
//                    PreviewActivity.preview(this, uri);
                    PreviewActivity.preview(this, uri);
                break;
        }
    }
    private boolean hide = false;
}
