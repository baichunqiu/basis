package com.qunli.ui;

import com.basis.base.BaseActivity;
import com.basis.widget.SwitchButton;
import com.kit.DateUtil;
import com.kit.DateUtil.DateFt;
import com.kit.DateUtil.TimeFt;
import com.kit.Logger;

import java.util.Date;

public class WidgetActivity extends BaseActivity {
    private String TAG = "WidgetActivity";

    @Override
    public int setLayoutId() {
        return R.layout.activity_widget;
    }

    @Override
    public void init() {
        setTitle("自定义View");
        Date date = new Date();
        String dateStr = DateUtil.date2String(date, DateFt.yHMHdH, TimeFt.HCmCs);
        Logger.e("date = " + dateStr);
        Date d = DateUtil.string2Date(dateStr, DateFt.yHMHdH, TimeFt.HCmCs);
        dateStr = DateUtil.date2String(d, DateFt.yLMLd, TimeFt.HHmHsH);
        Logger.e("date = " + dateStr);

        String dateStr1 = DateUtil.date2String(date, "yyyy-MM-dd HH:mm:ss:SS");
        Logger.e("date1 = " + dateStr1);
        Date d1 = DateUtil.string2Date(dateStr1, "yyyy-MM-dd HH:mm:ss:SS");
        dateStr1 = DateUtil.date2String(d1, "yyyy-MM-dd HH:mm:ss:SS");
        Logger.e("date1 = " + dateStr1);
        SwitchButton switchButton = getView(R.id.switch_button);

        switchButton.setChecked(true);
//        switchButton.isChecked();
//        switchButton.toggle();     //switch state
//        switchButton.toggle(false);//switch without animation
        switchButton.setShadowEffect(true);//disable shadow effect
//        switchButton.setEnabled(false);//disable button
//        switchButton.setEnableEffect(false);//disable the switch animation
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
            }
        });
    }
}
