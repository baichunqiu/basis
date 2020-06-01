package com.business;

/**
 * 业务封装接口
 *
 * @param <R> result 泛型
 * @param <E> extra信息 泛型
 */
public interface IBusiCallback<R, E> {

    /**
     * @param result
     * @param extra  额外信息
     */
    void onSuccess(R result, E extra);

    /**
     * @param code   状态
     * @param errMsg 错误信息
     */
    void onError(int code, String errMsg);

    /**
     * @param code 状态
     * @param msg  错误信息
     */
    void onAfter(int code, String msg);
}
