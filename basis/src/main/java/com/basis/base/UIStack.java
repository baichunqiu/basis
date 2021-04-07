package com.basis.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.kit.utils.Logger;

import java.util.LinkedList;

/**
 * @Author: BaiCQ
 * @ClassName: UI实例栈 包含activity 和 fragment
 * @CreateDate: 2019/3/29 16:43
 * @Description: AppHelper
 */
public class UIStack {
    private final static String TAG = "AppHelper";
    private final static UIStack instance = new UIStack();
    //全局广播
    private BroadcastReceiver bdReceiver;
    private LinkedList<IBase> iBases = new LinkedList<>();

    private String[] actions = new String[]{ConnectivityManager.CONNECTIVITY_ACTION};

    private UIStack() {
    }

    public static UIStack getInstance() {
        return instance;
    }

    public void add(IBase iBase) {
        iBases.add(iBase);
        if (null == bdReceiver) {//说明释放啦
            bdReceiver = new UIReceiver();
            BroadcastUtil.registerReceiver(bdReceiver, actions);
        }
        Logger.e(TAG, "add : size = " + iBases.size() + " ibase:" + iBase.getClass().getSimpleName());
    }

    public void remove(IBase remove) {
        iBases.remove(remove);
        if (iBases.isEmpty()) {
            if (null != bdReceiver) {
                BroadcastUtil.unregisterReceiver(bdReceiver);
                bdReceiver = null;
            }
        }
        Logger.e(TAG, "remove : size = " + iBases.size() + " ibase:" + remove.getClass().getSimpleName());
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

    public boolean isTaskTop(BaseActivity activity) {
        int len = iBases.size();
        BaseActivity baseActivity = null;
        for (int i = len - 1; i >= 0; i--) {
            IBase base = iBases.get(i);
            if (base instanceof Activity) {
                baseActivity = (BaseActivity) base;
                break;
            }
        }
        boolean isTaskTop = null != activity && activity == baseActivity;
        Logger.e(TAG, "isTaskTop:" + isTaskTop);
        return isTaskTop;
    }

    public void exit() {
        if (null != bdReceiver) {
            BroadcastUtil.unregisterReceiver(bdReceiver);
            bdReceiver = null;
        }
        int len = iBases.size();
        for (int i = len - 1; i >= 0; i--) {
            IBase base = iBases.get(i);
            if (base instanceof Activity) {
                Activity act = (Activity) base;
                if (!act.isFinishing()) {
                    act.finish();
                }
            }
        }
    }

    public static class UIReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                instance.setResultForAll();
            }
        }
    }

    private void setResultForAll() {
        for (IBase a : iBases) {
            if (a instanceof Activity) {
                a.onNetChange();
            }
        }
    }

//    private void setResultForTop() {
//        //遍历直到顶部的activity
//        int len = iBases.size();
//        for (int i = len - 1; i >= 0; i--) {
//            IBase base = iBases.get(i);
//            base.onNetChange();
//            if (base instanceof Activity) {
//                break;
//            }
//        }
//    }

}
