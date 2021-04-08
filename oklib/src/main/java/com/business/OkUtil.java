package com.business;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: BaiCQ
 * @ClassName: OkUtil
 * @Description: 相关工具类
 */
public class OkUtil {
    protected static boolean debug = true;
    public final static String TAG = "OkUtil";
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    private final static Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

    public static void i(String tag, Object obj){
        if (null == obj || !debug) return;
        Log.i(tag,obj.toString());
    }

    public static void e(String tag, Object obj){
        if (null == obj || !debug) return;
        Log.e(tag,obj.toString());
    }

    public static String obj2Json(Object object) {
        if (null == object) return "";
        return gson.toJson(object);
    }

    /**
     * @param element  待解析的JsonElement
     * @param clazz 字节码文件
     * @param <R> result的类型
     * @param <T> 解析实体的类 result是实体：R和T一样
     *           result是集合：R 是List<T>
     * @throws Exception
     */
    public static <R,T> R json2Obj(JsonElement element, Class<T> clazz){
        if (null == clazz) {
            e(TAG, "the clazz can not null!");
            return null;
        }
        if (element.isJsonArray()) {//list
            List<T> lst = new ArrayList<T>();
            JsonArray array = element.getAsJsonArray();
            for (JsonElement elem : array) {
                lst.add(gson.fromJson(elem, clazz));
            }
            return (R) lst;
        } else if (element.isJsonObject()) {//obj
            return (R)gson.fromJson(element, clazz);
        }
        return null;
    }
}
