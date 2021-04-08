package com.basis.net.callback;

import com.basis.net.callback.base.IListCallback;
import com.business.GeneralWrapperCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.IPage;
import com.business.parse.IParse;
import com.business.parse.IProcess;
import com.business.parse.BaseProcessor;
import com.business.parse.IWrap;
import com.kit.cache.GsonUtil;
import com.kit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: GeneralListCallback
 * @Description: 列表数据回调
 */
public class GeneralListCallback<R> extends GeneralWrapperCallBack<List<R>, Boolean> {

    public GeneralListCallback(ILoadTag loadTag, IParse parser, IBusiCallback<List<R>, Boolean> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
    }

    @Override
    public IProcess<List<R>, Boolean> onSetProcessor() {
        return new BaseProcessor<List<R>, Boolean>() {
            @Override
            public List<R> parseResult(IWrap wrapper) {
                if (null != wrapper.getBody() && !"".equals(wrapper.getBody()) && callback instanceof IListCallback) {
                    IListCallback<R> iListCallback = (IListCallback<R>) callback;
                    List<R> resultData = GsonUtil.json2List(wrapper.getBody(), iListCallback.setType());
                    resultData = iListCallback.onPreprocess(resultData);
                    Logger.e(TAG, "parseResult len = " + resultData.size());
                    return resultData;
                } else {//请求成功，但没有数据
                    Logger.e(TAG, "parseResult len = 0");
                    return new ArrayList<>();
                }
            }

            @Override
            public Boolean parseExtra(IWrap wrapper) {
                IPage page = wrapper.getPage();
                return null == page ? false : page.getPage() >= page.getTotal();
            }
        };
    }
}
