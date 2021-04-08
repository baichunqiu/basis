package com.business.parse;

import com.oklib.ORequest;

/**
 * @author: BaiCQ
 * @ClassName: Processor
 * @date: 2018/6/27
 * @Description: 处理器接口 封装有错误处理和数据处理
 */
public interface IProcess<R, E> {
    /**
     * 错误处理
     *
     * @param code
     * @param request
     */
    void process(int code, ORequest request);

    /**
     * 数据转换：
     * 从Wrapper中提取业务数据
     *
     * @param wrap
     * @return
     */
    R parseResult(IWrap wrap);

    /**
     * 数据转换：
     * 从Wrapper中提取额外数据
     * @param wrap
     * @return
     */
    E parseExtra(IWrap wrap);
}
