package com.business;

/**
 * 业务接口回调
 *
 * @param <R> result 泛型
 * @param <E> extra信息 泛型
 * @param <T> result中的实体类型
 */
public interface BsiCallback<R, E, T> {

    /**
     * @param result
     * @param extra  额外信息
     */
    void onSuccess(R result, E extra);

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
