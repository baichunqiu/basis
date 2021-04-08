package com.basis.net;

import android.text.TextUtils;

import com.business.GeneralWrapperCallBack;
import com.business.BsiCallback;
import com.business.ILoadTag;
import com.business.interfaces.IParse;
import com.kit.utils.Logger;
import com.kit.utils.NetUtil;
import com.oklib.Method;
import com.oklib.ORequest;

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
                                  BsiCallback<Integer,String,Integer> iCallback) {
        return request(url,
                params,
                method,
                checkOK(url,iCallback),
                new GeneralWrapperCallBack(tag, null, iCallback));
    }

    public static <R> ORequest request(ILoadTag tag,
                                     String url,
                                     Map<String, Object> params,
                                     Method method,
                                     BsiCallback<List<R>, Boolean,R> bsiCallback) {
        return request(url,
                params,
                method,
                checkOK(url,bsiCallback),
                new GeneralWrapperCallBack(tag, null, bsiCallback));
    }

    /**
     * @param tag           load视图
     * @param url           地址
     * @param params        参数
     * @param parser        自定义解析器
     * @param method        Method get/post
     * @param bsiCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ORequest request(ILoadTag tag,
                                     String url,
                                     Map<String, Object> params,
                                     IParse parser,
                                     Method method,
                                     BsiCallback<List<R>, Boolean,R> bsiCallback) {
        return request(url,
                params,
                method,
                checkOK(url,bsiCallback),
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
                                   BsiCallback<Integer,String,Integer> iCallback) {
        return request(url,
                params,
                method,
                checkOK(url,iCallback),
                new GeneralWrapperCallBack(tag, parser, iCallback));
    }

    private static <R, E, T> ORequest request(String url,
                                               Map<String, Object> params,
                                               Method method,
                                               boolean qFlag,
                                               GeneralWrapperCallBack<R, E, T> generalCallBack) {
        ORequest req = ORequest.Builder.method(method)
                .url(url)
                .param(params)
                .callback(generalCallBack)
                .build();
        return qFlag ? req.request(): req;
    }


    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @return
     */
    private static boolean checkOK(String url,BsiCallback bsiCallback) {
        boolean checked = !TextUtils.isEmpty(url) && NetUtil.isNetworkAvailable();
        if (!checked && null != bsiCallback) {
            Logger.e(TAG, "you net request url is null ，please check it ");
            bsiCallback.onError(CODE_CHECK_ERROR,"设备未连接网络");
        }
        return checked;
    }
}
