package com.business;

import com.business.parse.Parser;

public class OkHelper {
    private final static OkHelper instance = new OkHelper();
    //通用json解析器
    private Parser defaultParser;
    private String mToken;

    public static OkHelper get() {
        return instance;
    }

    public static void setDebug(boolean debug){
        OkUtil.debug = debug;
    }

    /**
     * 缓存token
     * 供外部设置
     *
     * @param token
     */
    public void setToken(String token) {
        if (null != token && "".equals(token)) {
            mToken = token;
        }
    }

    public String getToken() {
        return mToken;
    }

    public void setParser(Parser parser) {
        defaultParser = parser;
    }

    protected Parser getParser() {
        if (null == defaultParser) {
            defaultParser = new DefauParser();
        }
        return defaultParser;
    }
}
