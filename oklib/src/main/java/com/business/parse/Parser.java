package com.business.parse;

/**
 * 将response的数据封装成DataInfo返回
 */
public interface Parser {
    /**
     * 统一解析字段
     *
     * @param json
     * @return
     */
    Wrapper parse(String json) throws Exception;

    /**
     * 状态判断
     *
     * @param code
     * @return
     */
    boolean ok(int code);

    String[] headerKeys();
}