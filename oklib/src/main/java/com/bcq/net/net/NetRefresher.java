package com.bcq.net.net;

import com.bcq.net.Request;
import com.bcq.net.wrapper.BsiCallback;
import com.bcq.net.wrapper.ILoadTag;
import com.bcq.net.wrapper.interfaces.IResult;
import com.bcq.net.wrapper.OkUtil;
import com.bcq.net.wrapper.interfaces.IParse;
import com.bcq.net.api.Method;
import com.bcq.net.api.ORequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: NetRefresher
 * @Description: 网络数据处理刷新器
 */
public abstract class NetRefresher<T> implements BsiCallback<IResult.ObjResult<List<T>>, List<T>, Boolean, T> {
    public final static String TAG = "NetRefresher";
    private Class<T> tClass;
    private ORequest<List<T>> ORequest;
    protected int current = 0;//当前页的索引
    //由于使用request.request() 此处在回调callback不能配置死，由控制器动态维护
    //和最后一次请求绑定
    protected boolean refresh = false;//是否刷新标识
    protected Page page;
    protected IOpe operator;

    public NetRefresher(Class<T> clazz, Page page, IOpe operator) {
        this.tClass = clazz;
        this.page = page;
        this.operator = operator;
        current = page.getFirstIndex();
    }

    /**
     * 相同参数再次请求数据  根据refresh 修改 currentPage
     *
     * @param refresh
     */
    protected final void requestAgain(boolean refresh, final IOpe operator) {
        if (null != ORequest) {
            Map<String, Object> params = ORequest.param();
            if (null != params && null != page && params.containsKey(page.getKeyPage())) {
                current = refresh ? page.getFirstIndex() : Integer.valueOf(params.get(page.getKeyPage()).toString()) + 1;
                params.put(page.getKeyPage(), current);
            }
            this.refresh = refresh;
            ORequest = ORequest.request();
        } else {
            if (null != operator) {
                operator.onCustomerRequestAgain(refresh);
            } else {
                OkUtil.e(TAG, "Because Of The Processor is null, The Request To Refresh Data will be Discarded !");
            }
        }
    }

    /**
     * 请求数据
     *
     * @param dialog    ILoadTag load视图
     * @param url       地址
     * @param params    参数
     * @param parser    自定义数据解析器
     * @param method    get/post
     * @param isRefresh 刷新标识
     */
    public final void request(ILoadTag dialog,
                              String url,
                              Map<String, Object> params,
                              IParse parser,
                              Method method,
                              boolean isRefresh) {
        if (null != operator && null != page) {//operator 不为空 需要分页处理
            if (isRefresh) current = page.getFirstIndex();
            if (null == params) params = new HashMap<>(2);
            if (!params.containsKey(page.getKeyPage())) {
                params.put(page.getKeySize(), page.geSize());
                params.put(page.getKeyPage(), current);
            }
        }
        this.refresh = isRefresh;
        ORequest = Request.request(dialog, url, params, parser, method, this);
    }

    /************ BsiCallBack ***********/
    @Override
    public void onResult(IResult.ObjResult<List<T>> result) {
        if (null != operator) {
            onRefreshData(result.getResult(), refresh);//注意 此处不是使用的isRefresh
        }
        if (!result.getExtra()) current++;
    }

    @Override
    public void onError(int code, String errMsg) {
        if (null != operator) {
            onRefreshData(null, refresh);
            operator.onError(code, errMsg);
        }
    }

    @Override
    public void onAfter(int code, String msg) {
        OkUtil.e(TAG, "onAfter: [" + code + "] " + msg);
    }

    @Override
    public Class<T> onGetType() {
        return tClass;
    }

    protected abstract void onRefreshData(List<T> data, boolean refresh);
}