package com.qunli.demo;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.basis.base.BaseActivity;
import com.basis.net.LoadTag;
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
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 600; i++) {
            buffer.append("#123456789");
        }
        Logger.e(buffer.toString());
    }
    private  LoadTag loadTag;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.widgetActivity:
                UIKit.startActivity(mActivity,ViewActivity.class);
                break;
            case R.id.load:
                loadTag = new LoadTag(mActivity);
                loadTag.show();
              break;
          case R.id.recycle:
            UIKit.startActivity(mActivity, RecycleActivity.class);
          break;
        }
    }
}
