package com.bcq.net.wrapper;

import com.bcq.net.wrapper.interfaces.IParse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author: BaiCQ
 * @ClassName: DefauParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class BaseParser implements IParse<Wrapper> {
    @Override
    public Wrapper parse(int httpcode, String json) {
        //{"code":1,"time":"2020-12-11 18:02:25","message":"success","data":{"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.}
        Wrapper info = null;
        JsonObject resulObj = (JsonObject) JsonParser.parseString(json);
        if (null != resulObj) {
            info = new Wrapper().setCode(httpcode);
            int code = resulObj.get("code").getAsInt();
            OkUtil.e("DefauParser", "code = " + code);
            info.setCode(code);
            info.setMessage(resulObj.get("message").getAsString());
            info.setBody(resulObj.get("data"));
        }
        return info;
    }

    @Override
    public boolean ok(int code) {
        return code == 200 || code == 1;
    }
}
