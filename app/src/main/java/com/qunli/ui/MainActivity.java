package com.qunli.ui;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.basis.adapter.BsiAdapter;
import com.basis.adapter.SampleAdapter;
import com.basis.adapter.ViewHolder;
import com.basis.base.AbsListActivity;
import com.basis.widget.WXDialog;
import com.bcq.net.NetApi;
import com.bcq.net.callback.base.BaseListCallback;
import com.bcq.net.view.LoadTag;
import com.kit.DateUtil;
import com.kit.DateUtil.TimeFt;
import com.kit.DateUtil.DateFt;
import com.kit.KToast;
import com.kit.Logger;
import com.oklib.core.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AbsListActivity<WorkDetails> {
    private String TAG = "MainActivity";
    private final static String HOST = "https://api.workvideo.qunlivideo.com";
    private final static String WORKS = HOST + "/api/w/project/list";
    private final static String LOGIN = HOST + "/api/w/app/login";
    private String HIS_WORKS = HOST + "/api/w/task/list";
    private BsiAdapter adapter;

    @Override
    public View setContentView() {
        return LayoutInflater.from(mActivity).inflate(R.layout.activity_main, null);
    }


    @Override
    public void initView(View view) {
        setTitle("测试");
//        login("zhixingyuan", "12345678");
//        WXDialog.showDefaultDialog(mActivity, "什么情况?", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KToast.show("确定");
//            }
//        });
        Date date = new Date();
        String dateStr = DateUtil.date2String(date, DateFt.yHMHdH, TimeFt.HCmCs);
        Logger.e("date = " + dateStr);
        Date d = DateUtil.string2Date(dateStr,DateFt.yHMHdH, TimeFt.HCmCs);
        dateStr = DateUtil.date2String(d, DateFt.yLMLd, TimeFt.HHmHsH);
        Logger.e("date = " + dateStr);

        String dateStr1 = DateUtil.date2String(date,"yyyy-MM-dd HH:mm:ss:SS");
        Logger.e("date1 = " + dateStr1);
        Date d1 = DateUtil.string2Date(dateStr1,"yyyy-MM-dd HH:mm:ss:SS");
        dateStr1 = DateUtil.date2String(d1, "yyyy-MM-dd HH:mm:ss:SS");
        Logger.e("date1 = " + dateStr1);
    }

    private void login(String username, String pswd) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("username", username);
        params.put("password", pswd);
        final LoadTag tag = new LoadTag(mActivity, "登录...");
        NetApi.request(tag, LOGIN, params, null, Method.post, new BaseListCallback<TokenBean>() {

            @Override
            public void onSuccess(List<TokenBean> tokenBeans, Boolean loadFull) {
                super.onSuccess(tokenBeans, loadFull);
                //登录成功 获取信息
                int len = null == tokenBeans ? 0 : tokenBeans.size();
                if (1 == len) {
                    KToast.show("登录成功！");
                    Map<String, Object> params = new HashMap<>(2);
                    params.put("include", "parent_project,video,story");//parent_project作业   story操作记录   video视频   creater创建人 room房间
                    params.put("status", "stop");//搜索字段
                    getNetData(true, HIS_WORKS, params, "正在加载联系人", Method.get);
                }
            }

            @Override
            public void onError(int code, String message) {
                super.onError(code, message);
                KToast.show("登录失败：" + message);
            }
        });
    }

    @Override
    public BsiAdapter<WorkDetails> setAdapter() {
        if (null == adapter) {
            adapter = new SampleAdapter<WorkDetails>(mActivity, R.layout.item_history_task) {
                //            adapter = new SampleAdapter<WorkDetails>(mActivity, R.layout.item_task) {
                @Override
                protected void convert(final ViewHolder viewHolder, final WorkDetails item, final int position) {
//                    viewHolder.setText(R.id.tv_task, item.getParent_project().getName());
                    viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            };
        }
        return adapter;
    }
}
