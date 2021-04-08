package com.oklib.core;

public interface IOCallBack<T, E> extends IOBack<T> {

    void set(E e);

    E get();
}