package com.basis.base;

import com.bcq.net.api.Method;
import com.bcq.net.wrapper.interfaces.IParse;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @ClassName: IListRefresh
 * @Description: 主动处理接口
 */
public interface IListRefresh<T> {

    void initView();

    /**
     * 刷新适配器数据
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    void refresh(List<T> netData, boolean isRefresh);

    void request(String tag, String api, Map<String, Object> params, Method method, boolean isRefresh);

    /**
     * @param tag       进度条显示msg
     * @param api       mUrl
     * @param params    参数 注意：不包含page
     * @param method    Post/get
     * @param parser    解析器
     * @param isRefresh 是否刷新
     */
    void request(String tag, String api, Map<String, Object> params, IParse parser, Method method, boolean isRefresh);
}
