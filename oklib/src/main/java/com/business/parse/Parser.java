package com.business.parse;

/**
 * @author: BaiCQ
 * @ClassName: Processor
 * @date: 2018/6/27
 * @Description: 数据解析：统一数据格式，将response的数据封装成Wrapper返回
 */

public interface Parser {
    /**
     * 统一解析字段
     *
     * @param httpcode http状态码
     * @param json     body
     * @return
     */
    Wrapper parse(int httpcode, String json) throws Exception;

    /**
     * 状态判断
     *
     * @param code
     * @return
     */
    boolean ok(int code);

    /**
     * 需要header的keys数组
     *
     * @return
     */
    String[] headerKeys();
}