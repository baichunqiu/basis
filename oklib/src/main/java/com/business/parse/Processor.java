package com.business.parse;

import com.oklib.core.ReQuest;

/**
 * @author: BaiCQ
 * @ClassName: Processor
 * @date: 2018/6/27
 * @Description: 处理器接口 封装有错误处理和数据处理
 */
public interface Processor<R, E> {
    /**
     * 错误处理
     *
     * @param code
     * @param request
     */
    void process(int code, ReQuest request);

    /**
     * 数据转换
     *
     * @param wrapper
     * @return
     */
    R transform(Wrapper wrapper);

    /**
     * 额外数据解析
     *
     * @param wrapper
     * @return
     */
    E parseExtra(Wrapper wrapper);
}
