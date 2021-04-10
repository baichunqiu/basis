package com.kit.wapper;

public interface IHelper<T extends IHelper> {

    T get();

    void setDebug(boolean debug);

    void logi(String tag,Object obj);

    void loge(String tag,Object obj);

    void obj2Json(Object obj);
}
