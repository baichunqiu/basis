package com.oklib.core;

import android.net.Uri;

import com.kit.cache.GsonUtil;
import com.oklib.body.IBody;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ok core 转换工具类
 */
public class Transform {
    private static FormType formType = FormType.json;
    public final static MediaType json = MediaType.parse("application/json; charset=utf-8");

    public static void setFormType(FormType formType) {
        Transform.formType = formType;
    }

    private static MultipartBody.Builder param2Builder(Map<String, Object> params) {
        if (null != params && !params.isEmpty()) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof IBody) {//IBody 类型
                    IBody body = (IBody) value;
                    builder.addFormDataPart(key, body.name(), body.body());
                } else {
                    builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
            }
            return builder;
        }
        return null;
    }

    private static MultipartBody.Builder param2Builder(String key, Object value) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != value) {
            if (value instanceof IBody) {//IBody 类型
                IBody body = (IBody) value;
                builder.addFormDataPart(key, body.name(), body.body());
            } else {
                builder.addFormDataPart(key, value.toString());
            }
        }
        return builder;
    }

    /**
     * @param params Map参数
     * @return RequestBody
     */
    public static RequestBody param2Body(Map<String, Object> params) {
        if (formType == FormType.json) {
            return RequestBody.Companion.create(GsonUtil.obj2Json(params), json);
        } else {
            return param2Builder(params).build();
        }
    }

    /**
     * @param key   参数key
     * @param value 参数值
     * @return RequestBody
     */
    public static RequestBody param2Body(String key, Object value) {
        return param2Builder(key, value).build();
    }

    /**
     * get请求时：在url上拼接参数
     *
     * @param url    原url
     * @param params 参数map
     * @return 拼接参数后的url
     */
    public static String urlAppendParam(String url, Map<String, Object> params) {
        if (null == url || null == params || params.isEmpty()) return url;
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
        }
        return builder.build().toString();
    }

}
