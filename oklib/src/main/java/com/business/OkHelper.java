package com.business;

import com.business.parse.IHeader;
import com.business.parse.IParse;

public class OkHelper {
    private final static OkHelper instance = new OkHelper();
    //通用json解析器
    private IParse defaultParser;
    private String mToken;
    private IHeader cacheHeader;

    public static OkHelper get() {
        return instance;
    }

    public static void setDebug(boolean debug) {
        OkUtil.debug = debug;
    }


    public void setCacheHeader(IHeader cacheHeader) {
        this.cacheHeader = cacheHeader;
    }

    public IHeader getCacheHeader() {
        return cacheHeader;
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

    public void setParser(IParse parser) {
        defaultParser = parser;
    }

    protected IParse getDefaultParser() {
        if (null == defaultParser) {
            defaultParser = new DefaultParser();
        }
        return defaultParser;
    }
}
