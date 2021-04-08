package com.basis.net.callback;

import com.business.GeneralWrapperCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.BaseProcessor;
import com.business.parse.IParse;
import com.business.parse.IProcess;
import com.business.parse.IWrap;

/**
 * @author: BaiCQ
 * @ClassName: GeneralStateCallback
 * @Description: 状态回调
 */
public class GeneralStateCallback extends GeneralWrapperCallBack<Integer, String> {

    public GeneralStateCallback(ILoadTag loadTag, IParse parser, IBusiCallback<Integer, String> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
    }

    @Override
    public IProcess<Integer, String> onSetProcessor() {
        return new BaseProcessor<Integer, String>() {
            @Override
            public Integer parseResult(IWrap wrapper) {
                return wrapper.getCode();
            }

            @Override
            public String parseExtra(IWrap wrapper) {
                return wrapper.getMessage();
            }
        };
    }
}
