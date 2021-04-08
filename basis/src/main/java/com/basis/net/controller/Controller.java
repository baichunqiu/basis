package com.basis.net.controller;

import com.IRefresh;
import com.basis.net.LoadTag;
import com.basis.net.Request;
import com.basis.net.callback.base.StatusCallback;
import com.basis.net.callback.base.ListCallback;
import com.business.interfaces.IParse;
import com.oklib.Method;
import com.oklib.ORequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: Controller
 * @Description: 供没有listview的页面使用的控制器
 */
public abstract class Controller<T> implements IPage {
    public final static String TAG = "Controller";
    private Class<T> tClass;
    private ORequest<List<T>> ORequest;
    protected int currentPage = PAGE_FIRST;//当前页的索引
    //由于使用request.request() 此处在回调callback不能配置死，由控制器动态维护
    //和最后一次请求绑定
    protected boolean refresh = false;//是否刷新标识

    public Controller(Class<T> clazz) {
        this.tClass = clazz;
    }

    /**
     * 相同参数再次请求数据  根据refresh 修改 currentPage
     *
     * @param refresh
     */
    protected final void requestAgain(boolean refresh) {
        if (null != ORequest) {
            Map<String, Object> params = ORequest.param();
            if (null != params && params.containsKey(KEY_PAGE_INDEX)) {
                currentPage = refresh ? PAGE_FIRST : Integer.valueOf(params.get(KEY_PAGE_INDEX).toString()) + 1;
                params.put(KEY_PAGE_INDEX, currentPage + "");
            }
            this.refresh = refresh;
            ORequest = ORequest.request();
        }
    }

    /**
     * 请求数据api
     *
     * @param isRefresh   刷新标识
     * @param url         地址
     * @param params      参数
     * @param parser      自定义数据解析器
     * @param dialog      ILoadTag load视图
     * @param method      get/post
     * @param operator    数据处理对象
     * @param refreshView refreshView
     */
    protected final void request(final boolean isRefresh, String url, Map<String, Object> params,
                                 IParse parser,
                                 LoadTag dialog,
                                 Method method,
                                 final IOperate operator,
                                 IRefresh refreshView) {
        this.refresh = isRefresh;
        if (null != operator) {//operator 不为空 需要分页处理
            if (isRefresh) currentPage = PAGE_FIRST;
            if (null == params) params = new HashMap<>(2);
            if (!params.containsKey(PAGE_SIZE)) {
                params.put(KEY_PAGE_SIZE, PAGE_SIZE + "");
                params.put(KEY_PAGE_INDEX, currentPage + "");
            }
        }
        ListCallback<T> listCallback = new ListCallback<T>(refreshView) {
            List<T> tempData = null;

            @Override
            public void onSuccess(List<T> tList, Boolean loadFull) {
                super.onSuccess(tList, loadFull);
                tempData = tList;
                if (null != operator && !loadFull) {
                    currentPage++;
                }
            }

            @Override
            public void onError(int code, String errMsg) {
                super.onError(code, errMsg);
                tempData = null;
            }

            @Override
            public void onAfter(int status, String msg) {
                super.onAfter(status, msg);
                //注意此处没有使用isRefresh 而是使用 控制器维护的最后一次状态
                if (null == tempData && null != operator){
                    operator.onError(status, msg);
                }
                onRefreshData(tempData, refresh);
            }

            @Override
            public Class<T> onGetType() {
                return tClass;
            }
        };
        ORequest = Request.request(dialog, url, params, parser, method, listCallback);
    }


    /**
     * 没有结果集，只有状态的post请求
     *
     * @param url
     * @param params
     * @param dialog
     */
    public void operate(final String url, Map<String, Object> params, LoadTag dialog) {
        Request.status(dialog, url, params, Method.post, new StatusCallback() {
            @Override
            public void onAfter(int status, String sysMsg) {
                super.onAfter(status, sysMsg);
                _onResponceCallBack(url, status, sysMsg);
            }
        });
    }

    /**
     * 接口响应回调
     *
     * @param url       接口连接
     * @param stateCode 状态 1：成功 其他：失败
     * @param msg       服务器返回msg
     */
    public void _onResponceCallBack(String url, int stateCode, String msg) {

    }

    /**
     * 刷新数据数据回调
     *
     * @param netData
     * @param isRefresh
     */
    public abstract void onRefreshData(List<T> netData, boolean isRefresh);


    public interface IOperate<T> {

        /**
         * 加载失败回调
         */
        void onError(int status, String errMsg);
    }

}