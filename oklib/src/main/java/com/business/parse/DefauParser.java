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
    public final static int CODE_OK = 1;

    /**
     * @param json
     */
    @Override
    public DataInfo parse(String json) {
        // TODO: 2018/8/17 根据自己的json格式 修改
        DataInfo info = new DataInfo();
        JsonObject resulObj = (JsonObject) JsonParser.parseString(json);
        info.setCode(resulObj.get("code").getAsInt());
        info.setMessage(resulObj.get("message").getAsString());
        JsonElement jsonElement = resulObj.get("data");
        String body = null;
        if (jsonElement.isJsonArray()) {//JsonArray 不包含list节点
            body = jsonElement.getAsJsonArray().toString();
        } else if (jsonElement.isJsonObject()) {//obj
            JsonObject dataObj = jsonElement.getAsJsonObject();
            JsonElement listObj = dataObj.get("list");
            if (null != listObj) {//包含分页 页码
                body = listObj.getAsJsonArray().toString();
                JsonElement metaEle = dataObj.get("meta");
                JsonObject metaObj = null;
                if (null != metaEle) {
                    metaObj = metaEle.getAsJsonObject();
                }
                if (null != metaObj) {
                    JsonObject paginationObj = metaObj.get("pagination").getAsJsonObject();
                    if (null != paginationObj) {
                        info.setIndex(paginationObj.get("current_page").getAsInt());
                        info.setTotal(paginationObj.get("total_pages").getAsInt());
                    }
                }
            } else {
                body = dataObj.toString();
            }
        }else {
            body = jsonElement.toString();
        }
        info.setBody(body);
        return info;
    }

    @Override
    public boolean success(int code) {
        return code == CODE_OK;
    }

    @Override
    public String[] headers() {
        return new String[]{TOKEN_KEY};
    }
}
