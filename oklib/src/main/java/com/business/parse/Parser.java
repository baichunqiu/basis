package com.business.parse;

import com.business.DataInfo;

public interface Parser {
    String TOKEN_KEY = "authorization";
    /**
     * 统一解析字段
     *
     * @param json
     * @return
     */
    DataInfo parse(String json) throws Exception;

    /**
     * 状态判断
     *
     * @param code
     * @return
     */
    boolean success(int code);

    /**
     * 从响应结果中保存的header，并添加到下次请求header的keys
     *
     * @return
     */
    String[] headers();
}