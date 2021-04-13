//package com.basis.net.controller;
//
//import com.IRefresh;
//import com.basis.net.LoadTag;
//import com.basis.net.RefreshCallback;
//import com.business.interfaces.IParse;
//import com.kit.utils.Logger;
//import com.net.IOperate;
//import com.net.Page;
//import com.net.Request;
//import com.oklib.Method;
//import com.oklib.ORequest;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author: BaiCQ
// * @createTime: 2017/1/13 11:38
// * @className: Controller
// * @Description: 供没有listview的页面使用的控制器
// */
//public abstract class Controller<T> {
//    public final static String TAG = "Controller";
//    private Class<T> tClass;
//    private ORequest<List<T>> ORequest;
//    protected int currentPage = 0;//当前页的索引
//    //由于使用request.request() 此处在回调callback不能配置死，由控制器动态维护
//    //和最后一次请求绑定
//    protected boolean refresh = false;//是否刷新标识
//    protected Page page;
//
//    public Controller(Page page, Class<T> clazz) {
//        this.tClass = clazz;
//        this.page = page;
//        currentPage = page.getFirstIndex();
//    }
//
//    /**
//     * 相同参数再次请求数据  根据refresh 修改 currentPage
//     *
//     * @param refresh
//     */
//    protected final void requestAgain(boolean refresh, final IOperate operator) {
//        if (null != ORequest) {
//            Map<String, Object> params = ORequest.param();
//            if (null != params && null != page && params.containsKey(page.getKeyPage())) {
//                currentPage = refresh ? page.getFirstIndex() : Integer.valueOf(params.get(page.getKeyPage()).toString()) + 1;
//                params.put(page.getKeyPage(), currentPage);
//            }
//            this.refresh = refresh;
//            ORequest = ORequest.request();
//        } else {
//            if (null != operator) {
//                operator.onCustomerRequestAgain(refresh);
//            } else {
//                Logger.e(TAG, "Because Of The Processor is null, The Request To Refresh Data will be Discarded !");
//            }
//        }
//    }
//
//    public void onRefreshPage(boolean refresh, int current, Map<String, Object> params) {
//
//    }
//
//    /**
//     * 请求数据api
//     *
//     * @param isRefresh   刷新标识
//     * @param url         地址
//     * @param params      参数
//     * @param parser      自定义数据解析器
//     * @param dialog      ILoadTag load视图
//     * @param method      get/post
//     * @param operator    数据处理对象
//     * @param refreshView refreshView
//     */
//    protected final void request(boolean isRefresh, String url, Map<String, Object> params,
//                                 IParse parser,
//                                 LoadTag dialog,
//                                 Method method,
//                                 final IOperate operator,
//                                 IRefresh refreshView) {
//        this.refresh = isRefresh;
//        if (null != operator && null != page) {//operator 不为空 需要分页处理
//            if (isRefresh) currentPage = page.getFirstIndex();
//            if (null == params) params = new HashMap<>(2);
//            if (!params.containsKey(page.getKeyPage())) {
//                params.put(page.getKeySize(), page.geSize());
//                params.put(page.getKeyPage(), currentPage);
//            }
//        }
//        RefreshCallback<T> listCallback = new RefreshCallback<T>(tClass, refreshView) {
//            @Override
//            public void onSuccess(List<T> tList, Boolean loadFull) {
//                super.onSuccess(tList, loadFull);
//                if (null != operator) {
//                    onRefreshData(tList, refresh);//注意 此处不是使用的isRefresh
//                }
//                if (!loadFull) currentPage++;
//            }
//
//            @Override
//            public void onError(int code, String errMsg) {
//                super.onError(code, errMsg);
//                if (null != operator) {
//                    onRefreshData(null, refresh);
//                    operator.onError(code, errMsg);
//                }
//            }
//        };
//        ORequest = Request.request(dialog, url, params, parser, method, listCallback);
//    }
//
//    /**
//     * 刷新数据数据回调
//     *
//     * @param netData
//     * @param isRefresh
//     */
//    public abstract void onRefreshData(List<T> netData, boolean isRefresh);
//
//}