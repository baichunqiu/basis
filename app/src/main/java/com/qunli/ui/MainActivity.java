package com.qunli.ui;

import android.view.View;

import com.basis.base.BaseActivity;
import com.kit.utils.Logger;
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
        getView(R.id.camera).setOnClickListener(this);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 600; i++) {
//            Logger.e("#123456789");
            buffer.append("#123456789");
        }
        Logger.e(buffer.toString());
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
            case R.id.camera:
                UIKit.startActivity(mActivity, CameraActivity.class);
                break;
        }
    }
}
