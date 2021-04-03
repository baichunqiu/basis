package com.qunli.demo;

import android.app.Application;

import com.business.DefauParser;
import com.business.OkHelper;
import com.business.parse.Parser;
import com.business.parse.Wrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kit.cache.GsonUtil;
import com.kit.utils.Logger;


public class BcqApplication extends Application {
    private  static Parser parser;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Logger.setDebug(true);
        OkHelper.setDebug(true);
        parser = new BqParser();
        OkHelper.get().setParser(parser);
    }

    public static Parser getParser() {
        return parser;
    }

    public static class BqParser extends DefauParser{
        @Override
        public Wrapper parse(int httpcode, String json) {
            Wrapper info = new Wrapper();
            info.setCode(httpcode);
            Logger.e("BqParser", "json = " + json);
            JsonElement result = JsonParser.parseString(json);
            if (result instanceof JsonObject) {
                JsonObject resulObj = (JsonObject) result;
                info.setBody(resulObj.get("data").toString());
                info.setIndex(resulObj.get("page").getAsInt());
                info.setTotal(resulObj.get("page_count").getAsInt());
            }
            Logger.e("BqParser", "wrapper = " + GsonUtil.obj2Json(info));
            return info;
        }
    }
}
