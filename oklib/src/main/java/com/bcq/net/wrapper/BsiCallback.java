package com.bcq.net.wrapper;

import com.bcq.net.wrapper.interfaces.IResult;

/**
 * 业务接口回调
 *
 * @param <IR> result类型
 * @param <R>  IResult<R, E> 中R类型
 * @param <E>  IResult<R, E> 中E类型
 * @param <T>  Type Class 类型
 */
public interface BsiCallback<IR extends IResult<R, E>, R, E, T> {

    /**
     * @param result
     */
    void onResult(IR result);

    /**
     * @param code   状态码
     * @param errMsg 错误信息
     */
    void onError(int code, String errMsg);

    void onAfter();

    /**
     * 解析实体的类型
     *
     * @return
     */
    Class<T> onGetType();
}
