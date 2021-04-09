package com.business.interfaces;

import com.oklib.ORequest;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: Processor
 * @date: 2018/6/27
 * @Description: 处理器接口 封装有错误处理和数据处理
 * @param <R> result的类型
 * @param <E> extra的类型
 * @param <T> 解析实体的类 result是实体：R和T一样
 *           result是集合：R 是List<T>
 */
public interface IProcess<R, E, T> {
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
    R parseResult(IWrap wrap, Class<T> clazz);

    /**
     * 数据转换：
     * 从Wrapper中提取额外数据
     * @param wrap
     * @return
     */
    E parseExtra(IWrap wrap);
}