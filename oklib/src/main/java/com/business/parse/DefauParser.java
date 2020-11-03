package com.business.parse;

import com.business.DataInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author: BaiCQ
 * @ClassName: DefauParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class DefauParser implements Parser {

    /**
     * @param json
     */
    @Override
    public DataInfo parse(String json) {
        DataInfo info = new DataInfo();
        JsonObject resulObj = (JsonObject) JsonParser.parseString(json);
        JsonElement codeel = resulObj.get("code");
        if (null != codeel) {
            info.setCode(codeel.getAsInt());
        } else {
            JsonElement status = resulObj.get("status");
            if (null != status) {
                info.setCode(status.getAsInt());
            } else {
                info.setCode(-1);
            }
        }
        info.setMessage(resulObj.get("message").getAsString());
        JsonElement jsonElement = resulObj.get("result");
        String body = null;
        if (null != jsonElement) {
            if (jsonElement.isJsonArray()) {//JsonArray 不包含list节点
                body = jsonElement.getAsJsonArray().toString();
            } else if (jsonElement.isJsonObject()) {//obj
                JsonObject dataObj = jsonElement.getAsJsonObject();
                JsonElement listObj = dataObj.get("records");
                if (null != listObj) {//包含分页 页码
                    body = listObj.getAsJsonArray().toString();
                    info.setIndex(dataObj.get("current").getAsInt());
                    info.setTotal(dataObj.get("pages").getAsInt());
                } else {
                    body = dataObj.toString();
                }
            } else {
                body = jsonElement.toString();
            }
        }
        info.setBody(body);
        return info;
    }

    @Override
    public boolean success(int code) {
        return code == CODE_OK || code == 0;
    }

    @Override
    public String[] headers() {
        return new String[]{TOKEN_KEY};
    }
}
