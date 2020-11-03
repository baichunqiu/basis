package com.qunli.ui;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.basis.base.BaseActivity;
import com.basis.widget.SwitchButton;
import com.basis.widget.WXDialog;
import com.basis.widget.indicator.GuidPagerAdapter;
import com.basis.widget.indicator.SpringIndicator;
import com.basis.widget.indicator.TabClickListener;
import com.kit.DateFt;
import com.kit.DateUtil;
import com.kit.KToast;
import com.kit.Logger;
import com.kit.TimeFt;

import java.util.Arrays;
import java.util.Date;

public class WidgetActivity extends BaseActivity {
    private String TAG = "WidgetActivity";

    @Override
    public int setLayoutId() {
        return R.layout.activity_widget;
    }

    @Override
    public void init() {
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

            }
        });

        //SpringIndicator
        ViewPager viewPager = getView(R.id.view_pager);
        SpringIndicator springIndicator = getView(R.id.indicator);
        viewPager.setAdapter(new GuidPagerAdapter(titles, Arrays.asList(R.mipmap.bg1, R.mipmap.bg2, R.mipmap.bg3, R.mipmap.bg4)));
        springIndicator.setViewPager(viewPager);
        springIndicator.setOnTabClickListener(new TabClickListener(){
            @Override
            public boolean onTabClick(int position) {
                if (position == 3)
                WXDialog.showCustomDialog(mActivity, new EditText(mActivity), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KToast.show("ok ");
                    }
                });
                if (position == 0){
                    WXDialog.CustomBuilder builder = new WXDialog.CustomBuilder() {
                        @Override
                        public View onBuild() {
                            TextView textView = new TextView(mActivity);
                            textView.setText("完全自定义风格的弹框");
                            return textView;
                        }
                    };
                    WXDialog.s
//                    WXDialog.showCustomDialog(mActivity, builder, new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            KToast.show("弹框消失！");
//                        }
//                    });
                }
                return true;
            }
        });

    }

    private final static String[] titles = new String[]{"标题1", "标题2", "标题3", "标题4"};
}
