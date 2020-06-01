package com.basis.base;

import com.bcq.net.enums.NetType;

/**
 * @author: BaiCQ
 * @ClassName: IRefresh
 * @Description: 刷新UI的接口
 */
public interface IBase {

    /**
     * 设置布局
     */
    int setLayoutId();

    /**
     * 初始化
     */
    void init();

    /**
     * 刷新UI回调接口
     * @param obj
     */
    void onRefresh(Object obj);

    /**
     * 网络变化回调接口
     * @param netType
     */
    void onNetChange(NetType netType);
}
