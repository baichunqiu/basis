package com.net;

import com.business.BsiCallback;
import com.business.interfaces.IResult;

/**
 * @author: BaiCQ
 * @ClassName: StatusCallback
 * @Description: 没有body请求回调
 */
public class StatusCallback implements BsiCallback<Integer, String, Void, IResult.StatusResult> {

    public StatusCallback() {
    }

    /**
     * @param result
     */
    @Override
    public void onSuccess(IResult.StatusResult result) {

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
    public Class<Void> onGetType() {
        return null;
    }
}