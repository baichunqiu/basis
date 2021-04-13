package com.business;

import com.business.interfaces.IHeader;
import com.business.interfaces.IParse;
import com.business.interfaces.IProcess;
import com.business.interfaces.IResult;

public class OkHelper {
    private final static OkHelper instance = new OkHelper();
    //通用json解析器
    private IParse defaultParser;
    private IProcess defaultProcessor;
    private IHeader headCacher;

    public static OkHelper get() {
        return instance;
    }

    public static void setDebug(boolean debug) {
        OkUtil.debug = debug;
    }

    public void setHeadCacher(IHeader headCacher) {
        this.headCacher = headCacher;
    }

    public IHeader getHeadCacher() {
        return headCacher;
    }

    public void setDefaultParser(IParse parser) {
        defaultParser = parser;
    }

    public IParse getParser() {
        if (null == defaultParser) {
            defaultParser = new BaseParser();
        }
        return defaultParser;
    }

    public void setDefaultProcessor(IProcess defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

    protected <R,E,T,RE extends IResult<R,E>> IProcess<R,E,T, RE> getProcessor() {
        if (null == defaultProcessor) {
            defaultProcessor = new BaseProcessor();
        }
        return defaultProcessor;
    }
}
