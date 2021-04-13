package com.net;

import com.business.BsiCallback;
import com.business.interfaces.IResult;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: ListCallback
 * @Description: 有body网络请求的回调
 */
public class ListCallback<R> implements BsiCallback<List<R>, Boolean, R, IResult.ObjResult<List<R>>> {
    private Class<R> rClass;

    public ListCallback(Class<R> rClass) {
        this.rClass = rClass;
    }

    public void onSuccess(IResult.ObjResult<List<R>> result) {
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
