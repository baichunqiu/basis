package com.qunli.demo;

import com.basis.BasisApplication;
import com.bcq.net.wrapper.OkHelper;
import com.bcq.net.wrapper.Wrapper;
import com.bcq.net.wrapper.interfaces.IParse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kit.cache.GsonUtil;
import com.kit.utils.Logger;


public class BApplication extends BasisApplication {
    private static IParse parser;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Logger.setDebug(true);
        OkHelper.setDebug(true);
        parser = new BqIParse();
        OkHelper.get().setDefaultParser(parser);
    }

    public static IParse<Wrapper> getParser() {
        return parser;
    }

    public static class BqIParse implements IParse<Wrapper> {

        @Override
        public Wrapper parse(int httpcode, String json) {
            Wrapper info = new Wrapper();
            info.setCode(httpcode);
            Logger.e("BqParser", "json = " + json);
            JsonElement result = JsonParser.parseString(json);
            if (result instanceof JsonObject) {
                JsonObject resulObj = (JsonObject) result;
                info.setBody(resulObj.get("data"));
                info.setPage(resulObj.get("page").getAsInt(), resulObj.get("page_count").getAsInt());
            }
            Logger.e("BqParser", "wrapper = " + GsonUtil.obj2Json(info));
            return info;
        }

        @Override
        public boolean ok(int code) {
            return code == 200 || code == 1;
        }
    }
}
