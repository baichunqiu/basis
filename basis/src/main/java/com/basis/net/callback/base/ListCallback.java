package com.basis.net.callback.base;

import com.IRefresh;
import com.business.BsiCallback;
import com.kit.utils.Logger;
import com.kit.utils.ObjUtil;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: ListCallback
 * @Description: 有body网络请求的回调
 */
public class ListCallback<R> implements BsiCallback<List<R>, Boolean,R> {
    private IRefresh refreshView;
    protected Class<R> rClass;

    public ListCallback() {
        rClass = (Class<R>) ObjUtil.getTType(ListCallback.this)[0];
    }

    public ListCallback(IRefresh baseListView) {
        rClass = (Class<R>) ObjUtil.getTType(ListCallback.this)[0];
        this.refreshView = baseListView;
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
        Logger.e("ListCallback", "onError:["+code+"] " + errMsg);
    }

    @Override
    public void onAfter(int code, String msg) {
        if (null != refreshView) {
            refreshView.refreshComplete();
            refreshView.loadComplete();
        }
    }

    @Override
    public Class<R> onGetType() {
        return rClass;
    }
}
