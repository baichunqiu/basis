package com.basis.net.callback.base;

import com.business.IBusiCallback;

/**
 * @author: BaiCQ
 * @ClassName: IBusiCallback
 * @Description: 没有body请求回调
 */
public class BaseCallback implements IBusiCallback<Integer, String> {

    /**
     * @param status
     */
    @Override
    public void onSuccess(Integer status, String sysMsg) {
    }

    /**
     * @param message 错误信息
     */
    @Override
    public void onError(int code, String message) {
    }

    @Override
    public void onAfter(int code, String sysMsg) {
    }
}
