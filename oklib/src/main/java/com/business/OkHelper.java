package com.business;

import com.business.parse.DefauParser;
import com.business.parse.Parser;
import com.business.process.DefauProcessor;
import com.business.process.Processor;
import com.kit.Logger;

public class OkHelper {
    //通用json解析器
    private static Parser parser;
    //统一错误处理
    private static Processor processor;
    private static boolean debug = true;

    //格式：以'Bearer ' 开始
    // 'Bearer' + ' ' + tokenStr
    private static String TOKEN = null;

    /**
     * 获取token
     * 内部使用
     *
     * @return
     */
    public static String getToken() {
        return TOKEN;
    }

    /**
     * 缓存token
     * 供外部设置
     *
     * @param token
     */
    public static void setToken(String token) {
        if (!token.startsWith("Bearer")) {
            token = "Bearer " + token;
        }
        OkHelper.TOKEN = token;
    }

    /**
     * 设置全局Json解析器
     *
     * @param defaultParser
     */
    public static void setParser(Parser defaultParser) {
        OkHelper.parser = defaultParser;
    }

    public static Parser getParser() {
        if (null == parser) {//使用默认解析器
            parser = new DefauParser();
        }
        return parser;
    }

    /**
     * 设置全局错误处理器
     *
     * @param processor
     */
    public static void setProcessor(Processor processor) {
        OkHelper.processor = processor;
    }

    public static Processor getProcessor() {
        if (null == processor) {
            processor = new DefauProcessor();
        }
        return processor;
    }

    public static void setDebug(boolean debug) {
        OkHelper.debug = debug;
        Logger.setDebug(debug);
    }

    public static boolean debug() {
        return debug;
    }
}
