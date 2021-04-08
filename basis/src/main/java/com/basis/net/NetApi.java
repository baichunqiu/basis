package com.basis.net;

import android.text.TextUtils;

import com.basis.net.callback.GeneralListCallback;
import com.basis.net.callback.GeneralStateCallback;
import com.basis.net.callback.base.IListCallback;
import com.business.GeneralWrapperCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.IParse;
import com.kit.utils.Logger;
import com.kit.utils.NetUtil;
import com.oklib.Method;
import com.oklib.ORequest;

import java.util.Map;


/**
 * @author: BaiCQ
 * @ClassName: NetApi
 * @Description: 网络请求工具类
 * request:
 * 请求获取数据相关api 返回数据集
 * operate：
 * 提交操作相关api 返回状态
 */
public class NetApi {
    public final static String TAG = "NetApi";
    private final static int CODE_CHECK_ERROR = -1;

    /**
     * @param tag           load视图
     * @param url           地址
     * @param params        参数
     * @param method        Method get/post
     * @param iListCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ORequest request(ILoadTag tag, String url, Map<String, Object> params, Method method, IListCallback<R> iListCallback) {
        return request(url, params, method, new GeneralListCallback(tag, null, iListCallback));
    }

    /**
     * @param tag           load视图
     * @param url           地址
     * @param params        参数
     * @param parser        自定义解析器
     * @param method        Method get/post
     * @param iListCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ORequest request(ILoadTag tag, String url, Map<String, Object> params, IParse parser, Method method, IListCallback<R> iListCallback) {
        return request(url, params, method, new GeneralListCallback(tag, parser, iListCallback));
    }

    /**
     * @param tag       load视图
     * @param url       地址
     * @param params    参数
     * @param method    Method get/post
     * @param iCallback 状态回调
     * @return 请求封装体
     */
    public static ORequest operate(ILoadTag tag, String url, Map<String, Object> params, Method method, IBusiCallback<Integer, String> iCallback) {
        return request(url, params, method, new GeneralStateCallback(tag, null, iCallback));
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
    public static ORequest operate(ILoadTag tag, String url, Map<String, Object> params, IParse parser, Method method, IBusiCallback<Integer, String> iCallback) {
        return request(url, params, method, new GeneralStateCallback(tag, parser, iCallback));
    }

    /**
     * 请求
     *
     * @param url             地址
     * @param params          参数
     * @param generalCallBack 回调
     * @param method          请求方式
     * @param <R>             result类型（主数据）
     * @param <E>             extra类型 （附加数据）
     * @return ReQuest 请求封装体
     */
    public static <R, E> ORequest request(String url, Map<String, Object> params, Method method, GeneralWrapperCallBack<R, E> generalCallBack) {
        ORequest request =  ORequest.Builder.method(method)
                .url(url)
                .param(params)
                .callback(generalCallBack)
                .build();
        if (checkOK(url)) {
            request.request();
        } else {
            if (null != generalCallBack) {
                generalCallBack.callback.onError(CODE_CHECK_ERROR,"设备未连接网络");
            }
        }
        return request;
    }


    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @return
     */
    public static <T> boolean checkOK(String url) {
        if (TextUtils.isEmpty(url)) {
            Logger.e(TAG, "you net request url is null ，please check it ");
            return false;
        }
        return NetUtil.isNetworkAvailable();
    }
}
