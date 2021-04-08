package com.business;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    /**
     * 功能需要将说有json or JsonArr 解析成List<T>
     * 但是由于
     * gson.fromJson(json, new TypeToken<List<T>>() {}.getType()); 此处使用泛型 导致解析获取T类型失败。
     * 故有次手动解析实现 效率比 json2Obj(String json, TypeToken typeToken) 要低
     *
     * @param json  待解析的json串
     * @param clazz 字节码文件
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (null == clazz) {
            e(TAG, "the clazz can not null!");
            return null;
        }
        List<T> lst = new ArrayList<T>();
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement instanceof JsonArray) {//兼融list
                JsonArray array = jsonElement.getAsJsonArray();
                for (JsonElement elem : array) {
                    lst.add(gson.fromJson(elem, clazz));
                }
            } else if (jsonElement instanceof JsonObject) {//obj
                lst.add(gson.fromJson(json, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static <T> T json2Obj(String json, Class<T> clazz) {
        if (null == clazz) {
           e(TAG, "the clazz can not null!");
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    public static String obj2Json(Object object) {
        if (null == object) return "";
        return gson.toJson(object);
    }

    public static void i(String tag, Object obj){
        if (null == obj || !debug) return;
        Log.i(tag,obj.toString());
    }

    public static void e(String tag, Object obj){
        if (null == obj || !debug) return;
        Log.e(tag,obj.toString());
    }
}
