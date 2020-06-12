package com.basis.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.bcq.net.NetApi;
import com.bcq.net.enums.NetType;
import com.kit.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BaiCQ
 * @ClassName: AppHelper
 * @CreateDate: 2019/3/29 16:43
 * @Description: AppHelper
 */
public class AppHelper {
    private final static String TAG = "AppHelper";
    private final static AppHelper instance = new AppHelper();
    // 退出应用的广播接受者
    private BroadcastReceiver bdReceiver;
    private List<IBase> iBases = new ArrayList<>(16);

    private AppHelper() {
        registBroadcast(new String[]{ConnectivityManager.CONNECTIVITY_ACTION});
    }

    private void registBroadcast(String[] actions) {
        bdReceiver = new BasisReceiver();
        BroadcastUtil.registerReceiver(bdReceiver, actions);
    }

    public static AppHelper getInstance() {
        return instance;
    }

    public void add(IBase iBase) {
        iBases.add(iBase);
        Logger.e(TAG, "add : size = " + iBases.size());
    }

    public void remove(IBase remove) {
        iBases.remove(remove);
        if (iBases.isEmpty()) {
            //没有activity或者fragment 则注销广播
            if (null != bdReceiver) {
                BroadcastUtil.unregisterReceiver(bdReceiver);
                bdReceiver = null;
            }
        }
        Logger.e(TAG, "remove : size = " + iBases.size());
    }

    public BaseActivity getTopActivity() {
        int len = iBases.size();
        for (int i = len - 1; i >= 0; i--) {
            IBase base = iBases.get(i);
            if (base instanceof Activity) {
                return (BaseActivity) base;
            }
        }
        return null;
    }

    public void exit() {
        if (null != bdReceiver) {
            BroadcastUtil.unregisterReceiver(bdReceiver);
            bdReceiver = null;
        }
        for (IBase base : iBases) {
            if (base instanceof Activity) {
                Activity act = (Activity) base;
                if (!act.isFinishing()) {
                    act.finish();
                }
            }
        }
    }

    public class BasisReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                setResultForTop(NetApi.getNetWorkState());
            }
        }
    }


    private void setResultForAll(NetType netType) {
        for (IBase a : iBases) {
            a.onNetChange(netType);
        }
    }

    private void setResultForTop(NetType netType) {
        //遍历直到顶部的activity
        int len = iBases.size();
        for (int i = len - 1; i >= 0; i--) {
            IBase base = iBases.get(i);
            base.onNetChange(netType);
            if (base instanceof Activity) {
                break;
            }
        }
    }

}
