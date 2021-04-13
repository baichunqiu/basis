package com.business;

import com.business.interfaces.IResult;

/**
 * 业务接口回调
 *
 * @param <R> result 泛型
 * @param <E> extra信息 泛型
 * @param <T> result中的实体类型
 */
public interface BsiCallback<R, E, T, RE extends IResult<R, E>> {

    /**
     * @param result
     */
    void onSuccess(RE result);

    /**
     * @param code   状态码
     * @param errMsg 错误信息
     */
    void onError(int code, String errMsg);

    /**
     * @param code 状态码
     * @param msg  错误信息
     */
    void onAfter(int code, String msg);

    /**
     * 解析实体的类型
     *
     * @return
     */
    Class<T> onGetType();
}
