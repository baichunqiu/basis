package com.basis.net.callback.base;

import com.business.IBusiCallback;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: IListCallback
 * @Description: 有body网络请求的回调
 */
public interface IListCallback<R> extends IBusiCallback<List<R>, Boolean> {

    /**
     * 数据预处理
     * @param rawData
     * @return
     */
    List<R> onPreprocess(List<R> rawData);

    @Override
    void onSuccess(List<R> rs, Boolean loadFull);

    @Override
    void onError(int code, String errMsg);

    @Override
    void onAfter(int code, String msg);

    Class<R> setType();
}
