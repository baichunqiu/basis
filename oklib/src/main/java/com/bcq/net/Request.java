package com.bcq.net;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import com.bcq.net.wrapper.BsiCallback;
import com.bcq.net.wrapper.GeneralWrapperCallBack;
import com.bcq.net.wrapper.ILoadTag;
import com.bcq.net.wrapper.OkUtil;
import com.bcq.net.wrapper.interfaces.IParse;
import com.bcq.net.wrapper.interfaces.IResult;
import com.bcq.net.api.Method;
import com.bcq.net.api.ORequest;

import java.util.List;
import java.util.Map;


/**
 * @author: BaiCQ
 * @ClassName: NetApi
 * @Description: 网络请求工具类
 * request:
 * 请求获取数据相关api 返回数据集
 * status：
 * 提交操作相关api 返回状态
 */
public class Request {
    public final static String TAG = "NetApi";
    private final static int CODE_CHECK_ERROR = -1;


    public static ORequest status(ILoadTag tag,
                                  String url,
                                  Map<String, Object> params,
                                  Method method,
                                  BsiCallback<IResult.StatusResult, Integer, String, Void> iCallback) {
        return request(url,
                params,
                method,
                checkOK(url, iCallback),
                new GeneralWrapperCallBack(tag, null, iCallback));
    }

    public static <R> ORequest request(ILoadTag tag,
                                       String url,
                                       Map<String, Object> params,
                                       Method method,
                                       BsiCallback<IResult.ObjResult<List<R>>, List<R>, Boolean, R> bsiCallback) {
        return request(url,
                params,
                method,
                checkOK(url, bsiCallback),
                new GeneralWrapperCallBack(tag, null, bsiCallback));
    }

    /**
     * @param tag         load视图
     * @param url         地址
     * @param params      参数
     * @param parser      自定义解析器
     * @param method      Method get/post
     * @param bsiCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ORequest request(ILoadTag tag,
                                       String url,
                                       Map<String, Object> params,
                                       IParse parser,
                                       Method method,
                                       BsiCallback<IResult.ObjResult<List<R>>, List<R>, Boolean, R> bsiCallback) {
        return request(url,
                params,
                method,
                checkOK(url, bsiCallback),
                new GeneralWrapperCallBack(tag, parser, bsiCallback));
    }

    /**
     * @param tag       load视图
     * @param url       地址
     * @param params    参数
     * @param parser    自定义解析器
     * @param method    Method get/post
     * @param iCallback 状态回调
     * @return 请求封装体
     */
    public static ORequest status(ILoadTag tag,
                                  String url,
                                  Map<String, Object> params,
                                  IParse parser,
                                  Method method,
                                  BsiCallback<IResult.StatusResult, Integer, String, Void> iCallback) {
        return request(url,
                params,
                method,
                checkOK(url, iCallback),
                new GeneralWrapperCallBack(tag, parser, iCallback));
    }

    public static <R> ORequest requestAgain(ORequest<R> request,
                                            BsiCallback<IResult.ObjResult<List<R>>, List<R>, Boolean, R> bsiCallback) {
        if (request.callBack instanceof GeneralWrapperCallBack) {
            ((GeneralWrapperCallBack) request.callBack).setBsiCallback(bsiCallback);
        }
        return request.request();
    }

    private static <IR extends IResult<R, E>, R, E, T> ORequest request(String url,
                                                                        Map<String, Object> params,
                                                                        Method method,
                                                                        boolean qFlag,
                                                                        GeneralWrapperCallBack<IR, R, E, T> generalCallBack) {
        ORequest req = ORequest.Builder.method(method)
                .url(url)
                .param(params)
                .callback(generalCallBack)
                .build();
        return qFlag ? req.request() : req;
    }

    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @return
     */
    private static boolean checkOK(String url, BsiCallback bsiCallback) {
        boolean checked = !TextUtils.isEmpty(url) && isNetworkAvailable();
        if (!checked && null != bsiCallback) {
            OkUtil.e(TAG, "check error you net request url is null or network unavailable ，please check it ");
            bsiCallback.onError(CODE_CHECK_ERROR, "设备未连接网络");
        }
        return checked;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network[] networks = manager.getAllNetworks();
            for (Network network : networks) {
                NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
                if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true;
                }
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private static Application mBaseContext;


    private static Application getBaseContext() {
        if (null != mBaseContext) {
            return mBaseContext;
        }
        try {
            mBaseContext = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (null == mBaseContext) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (final Exception e) {
            try {
                mBaseContext = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                e.printStackTrace();
            }
        }
        return mBaseContext;
    }
}
