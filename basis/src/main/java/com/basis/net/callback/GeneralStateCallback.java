package com.basis.net.callback;

import com.business.GeneralWrapperCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Processor;
import com.business.parse.Parser;
import com.business.parse.Wrapper;
import com.business.parse.BaseProcessor;

/**
 * @author: BaiCQ
 * @ClassName: GeneralStateCallback
 * @Description: 状态回调
 */
public class GeneralStateCallback extends GeneralWrapperCallBack<Integer, String> {

    public GeneralStateCallback(ILoadTag loadTag, Parser parser, IBusiCallback<Integer, String> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
    }

    @Override
    public Processor<Integer, String> onSetProcessor() {
        return new BaseProcessor<Integer, String>() {
            @Override
            public Integer parseResult(Wrapper wrapper) {
                return wrapper.getCode();
            }

            @Override
            public String parseExtra(Wrapper wrapper) {
                return wrapper.getMessage();
            }
        };
    }
}
