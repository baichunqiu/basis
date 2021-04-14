package com.bcq.net.net;

import com.bcq.net.wrapper.BsiCallback;
import com.bcq.net.wrapper.interfaces.IResult;

/**
 * @author: BaiCQ
 * @ClassName: StatusCallback
 * @Description: 没有body请求回调
 */
public class StatusCallback implements BsiCallback<IResult.StatusResult,Integer, String,Void> {

    public StatusCallback() {
    }

    /**
     * @param result
     */
    @Override
    public void onResult(IResult.StatusResult result) {

    }

    /**
     * @param message 错误信息
     */
    @Override
    public void onError(int code, String message) {
    }

    @Override
    public void onAfter() {
    }

    @Override
    public Class<Void> onGetType() {
        return null;
    }
}
