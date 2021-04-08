package com.basis.net.callback.base;

import com.business.BsiCallback;

/**
 * @author: BaiCQ
 * @ClassName: StatusCallback
 * @Description: 没有body请求回调
 */
public class StatusCallback implements BsiCallback<Integer, String, Integer> {

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

    @Override
    public Class<Integer> onGetType() {
        return null;
    }
}
