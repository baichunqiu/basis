package com.kit;

/**
 * 统一返回接口
 *
 * @param <T>
 */
public interface IResultBack<T> {
    void onResult(T t);
}
