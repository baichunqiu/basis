package com.basis.net.callback.base;

import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BaseListCallback
 * @Description: 有body网络请求的回调
 */
public class BaseListCallback<R> implements IListCallback<R> {
    private IRefreshView refreshView;
    protected Class<R> rClass;

    public BaseListCallback() {
        this(null);
    }

    public BaseListCallback(IRefreshView baseListView) {
        this.refreshView = baseListView;
        rClass = (Class<R>) ObjUtil.getTType(BaseListCallback.this)[0];
    }

    @Override
    public List<R> onPreprocess(List<R> rawData) {
        return rawData;
    }

    /**
     * @param rs       网络数据
     * @param loadFull 当前页码<分页使用>
     */
    public void onSuccess(List<R> rs, Boolean loadFull) {
        if (null != refreshView) {
            refreshView.setNoMore(loadFull);
        }
    }

    /**
     * @param errMsg 错误信息
     */
    public void onError(int code, String errMsg) {
        Logger.e("BaseListCallback", "onError:["+code+"] " + errMsg);
    }

    @Override
    public void onAfter(int code, String msg) {
        if (null != refreshView) {
            refreshView.refreshComplete();
            refreshView.refreshComplete();
        }
    }

    @Override
    public Class<R> setType() {
        return rClass;
    }
}
