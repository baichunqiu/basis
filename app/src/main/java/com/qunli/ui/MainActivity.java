package com.qunli.ui;

import android.view.View;

import com.basis.base.BaseActivity;
import com.kit.UIKit;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        getView(R.id.listActivity).setOnClickListener(this);
        getView(R.id.widgetActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.listActivity:
                UIKit.startActivity(mActivity, ListActivity.class);
                break;
            case R.id.widgetActivity:
                UIKit.startActivity(mActivity, WidgetActivity.class);
                break;
        }
    }
}
