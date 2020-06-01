package com.bcq.net.callback;

import android.text.TextUtils;

import com.bcq.net.callback.base.IListCallback;
import com.business.DataInfo;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Parser;
import com.kit.GsonUtil;
import com.kit.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: GeneralListCallback
 * @Description: 列表数据回调
 */
public class GeneralListCallback<R> extends GeneralCallBack<List<R>, Boolean> {

    public GeneralListCallback(ILoadTag loadTag, Parser parser, IBusiCallback<List<R>, Boolean> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
    }

    /**
     * code 或其他错误 返回null
     * 请求成功但是没有数据返回空数组
     *
     * @param netInfo
     * @param iCallback
     * @return
     * @throws Exception
     */
    @Override
    protected List<R> parseResult(DataInfo netInfo, IBusiCallback<List<R>, Boolean> iCallback) throws Exception {
        if (!parser.success(netInfo.getCode()) || !(iCallback instanceof IListCallback)) {
            return null;//状态错误
        }
        IListCallback<R> iListCallback = (IListCallback<R>) iCallback;
        if (!TextUtils.isEmpty(netInfo.getBody())) {
            Logger.e(TAG, "body = " + netInfo.getBody());
            List<R> resultData = GsonUtil.json2List(netInfo.getBody(), iListCallback.setType());
            //预处理数据
            resultData = iListCallback.onPreprocess(resultData);
            Logger.e(TAG, "parseResult : " + resultData.size());
            return resultData;
        } else {//请求成功，但没有数据
            Logger.e(TAG, "parseResult : 0");
            return new ArrayList<>();
        }
    }

    @Override
    protected Boolean parseExtra(DataInfo netInfo) {
        return netInfo.getIndex() >= netInfo.getTotal();
    }
}
