package com.bcq.net;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import com.bcq.net.api.core.Utils;
import com.bcq.net.wrapper.BsiCallback;
import com.bcq.net.wrapper.Error;
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
    public final static String TAG = "Request";

    public static ORequest status(ILoadTag tag,
                                  String url,
                                  Map<String, Object> params,
                                  Method method,
                                  BsiCallback<IResult.StatusResult, Integer, String, Void> iCallback) {
        return request(url,
                params,
                method,
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
                                                                        GeneralWrapperCallBack<IR, R, E, T> generalCallBack) {
        return ORequest.Builder.method(method)
                .url(url)
                .param(params)
                .callback(generalCallBack)
                .build()
                .request();
    }
}
