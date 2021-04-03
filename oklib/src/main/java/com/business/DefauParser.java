package com.business;

import com.business.parse.Parser;
import com.business.parse.Wrapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author: BaiCQ
 * @ClassName: DefauParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class DefauParser implements Parser {
    //    {"code":1,"time":"2020-12-11 18:02:25","message":"success","data":{"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.}
    @Override
    public Wrapper parse(int httpcode,String json) {
        Wrapper info = new Wrapper();
        OkUtil.e("DefauParser", "json = " + json);
        JsonObject resulObj = (JsonObject) JsonParser.parseString(json);
        if (null != resulObj) {
            int code = resulObj.get("code").getAsInt();
            OkUtil.e("DefauParser", "code = " + code);
            info.setCode(code);
            String message = resulObj.get("message").getAsString();
            info.setMessage(message);
            JsonObject data = resulObj.get("data").getAsJsonObject();
            info.setBody(data.toString());
        } else {
            OkUtil.e("DefauParser", "resulObj = null ");
        }
        return info;
    }

    @Override
    public boolean ok(int code) {
        return code == 200 || code == 1;
    }

    @Override
    public String[] headerKeys() {
        return new String[0];
    }
}
