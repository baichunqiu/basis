package com.bcq.net.callback;

import com.business.DataInfo;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Parser;

/**
 * @author: BaiCQ
 * @ClassName: GeneralStateCallback
 * @Description: 状态回调
 */
public class GeneralStateCallback extends GeneralCallBack<Integer, String> {

    public GeneralStateCallback(ILoadTag loadTag, Parser parser, IBusiCallback<Integer, String> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
    }

    @Override
    protected Integer parseResult(DataInfo netInfo, IBusiCallback<Integer, String> iCallback) {
        return netInfo.getCode();
    }

    @Override
    protected String parseExtra(DataInfo netInfo) {
        return netInfo.getMessage();
    }
}
