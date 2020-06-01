package com.bcq.net.callback.base;

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
     *
     * @param rawData
     * @return
     */
    List<R> onPreprocess(List<R> rawData);

    @Override
    void onSuccess(List<R> rs, Boolean loadFull);

    Class<R> setType();
}
