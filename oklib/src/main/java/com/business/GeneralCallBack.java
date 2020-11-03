package com.business;

import android.text.TextUtils;

import com.business.parse.Parser;
import com.kit.Logger;
import com.oklib.callback.BaseCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @param <R> result:
 *            List 数据集合
 *            int  code
 * @param <E> extra：
 *            boolen loadfull
 *            message info
 * @author: BaiCQ
 * @ClassName: GeneralCallBack
 * @Description: GeneralCallBack
 * 1.响应结果是List
 * 2.响应结果无，只有code和message
 */
public abstract class GeneralCallBack<R, E> extends BaseCallBack<R> {
    protected final String TAG = this.getClass().getSimpleName();
    public final static String _unKnow_error = "未知错误!";
    public final static String _noData_error = "NO DATA";
    public final static Map<String, String> cacheHeaders = new HashMap(2);
    private ILoadTag tTag;
    protected Parser parser;
    private IBusiCallback<R, E> iBusiCallback;

    protected int code;
    private E extra;
    private String message = "";

    public GeneralCallBack(ILoadTag loadTag, Parser parser, IBusiCallback<R, E> iBusiCallback) {
        this.tTag = loadTag;
        this.iBusiCallback = iBusiCallback;
        this.parser = null != parser ? parser : OkHelper.getParser();
    }

    /**
     * 根据header的key缓存
     *
     * @param response
     */
    private final void cacheHeader(Response response) {
        String[] headerKeys = parser.headers();
        for (String key : headerKeys) {
            String value = response.header(key);
            if (!TextUtils.isEmpty(value)) {
                if (key.equalsIgnoreCase(Parser.TOKEN_KEY)) {
                    OkHelper.setToken(value);
                }
                //有值才存储 避免因某次数据识别导致token没有
                cacheHeaders.put(key, value);
                Logger.e(TAG, "cacheHeader  key:" + key + " value:" + value);
            }
        }
    }

    /**
     * 在请求前统一添加公共header
     *
     * @param builder
     */
    private void addHeader(Request.Builder builder) {
        String[] headerKeys = parser.headers();
        for (String key : headerKeys) {
            String value = cacheHeaders.get(key);
            if (TextUtils.isEmpty(value) && key.equalsIgnoreCase(Parser.TOKEN_KEY)) {
                //token 没有cache token 则使用外部设置的
                value = OkHelper.getToken();
            }
            if (!TextUtils.isEmpty(value)) {
                builder.addHeader(key, value);
                Logger.e(TAG, "addHeader  key：" + key + " value:" + value);
            }
        }
    }

    @Override
    public void onBefore(Request.Builder builder) {
        if (null != tTag) tTag.show();
        addHeader(builder);
    }

    @Override
    public R onParse(Response response) throws Exception {
        cacheHeader(response);
        String res = response.body().string();
        DataInfo dataInfo = null;
        Logger.e(TAG, "res = " + res);
        if (!TextUtils.isEmpty(res)) {
            if (null != parser) {
                dataInfo = parser.parse(res);
                if (null != dataInfo) {
                    message = dataInfo.getMessage();
                    code = dataInfo.getCode();
                    extra = parseExtra(dataInfo);
                    //Logger.e(TAG, "code = " + code);
                    if (!parser.success(code)) {//错误
                        if (null != reQuest) OkHelper.getProcessor().process(code, reQuest);
                    }
                }
            }
        }
        return parseResult(dataInfo, iBusiCallback);
    }

    @Override
    public void onResponse(R result) {
        boolean success = false;
        boolean nodata = false;
        if (result instanceof Integer) {
            success = parser.success((Integer) result);
        } else if (result instanceof List) {
            if (null != result) {//不为null：定义为no_data
                if ((!((List) result).isEmpty())) {
                    success = true;
                } else {
                    nodata = true;
                }
            }
        }
        Logger.e(TAG, "success = " + success + "  nodata = " + nodata);
        if (success) {
            iBusiCallback.onSuccess(result, extra);
        } else {
            //error 有两种情况:1.nodata 2.error
            if (nodata) {
                iBusiCallback.onError(code, _noData_error);
            } else {
                iBusiCallback.onError(code, TextUtils.isEmpty(message) ? _unKnow_error : message);
            }
        }
    }

    @Override
    public void onError(Exception e) {
        message = e.getLocalizedMessage();//错误信息级别比接口返回信息稿
        iBusiCallback.onError(code, message);
    }

    @Override
    public void onAfter() {
        if (null != tTag) tTag.dismiss();
        iBusiCallback.onAfter(code, message);
    }

    public IBusiCallback<R, E> getiBusiCallback() {
        return iBusiCallback;
    }

    /**
     * 解析结果集
     *
     * @param dataInfo
     * @param iBusiCallback
     */
    protected abstract R parseResult(DataInfo dataInfo, IBusiCallback<R, E> iBusiCallback) throws Exception;

    /**
     * 解析附加信息
     *
     * @param dataInfo
     */
    protected abstract E parseExtra(DataInfo dataInfo);
}
