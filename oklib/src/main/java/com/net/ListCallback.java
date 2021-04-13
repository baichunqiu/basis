package com.net;

import com.business.BsiCallback;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: ListCallback
 * @Description: 有body网络请求的回调
 */
public class ListCallback<R> implements BsiCallback<List<R>, Boolean, R> {
    private Class<R> rClass;

    public ListCallback(Class<R> rClass) {
        this.rClass = rClass;
    }

    public void onSuccess(List<R> rs, Boolean loadFull) {
    }

    public void onError(int code, String errMsg) {
    }

    @Override
    public void onAfter(int code, String msg) {
    }

    @Override
    public Class<R> onGetType() {
        return rClass;
    }
}
