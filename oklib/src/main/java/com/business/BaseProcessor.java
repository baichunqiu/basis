package com.business;

import com.business.interfaces.IPage;
import com.business.interfaces.IProcess;
import com.business.interfaces.IResult;
import com.business.interfaces.IWrap;
import com.google.gson.JsonElement;
import com.oklib.ORequest;

import java.util.List;

/**
 * @Author: BaiCQ
 * @ClassName: BaseProcessor
 * @CreateDate: 2019/3/27 14:08
 * @Description: 数据处理封装
 * 注意：仅支持 StatueResult的类型 和 ObjResult<List<T>>的结果
 */
public class BaseProcessor<R, E, T, RE extends IResult<R, E>> implements IProcess<R, E, T, RE> {
    private final static String TAG = "BaseProcessor";
    //最大重复请求次数
    private final static int MAX_REPEAT = 1;
    //重复次数
    private int repeat = 1;
    //缓存上次code
    private int lastCode = 0;
    private String lastUrl = "";

    @Override
    public final void process(int code, ORequest ORequest) {
        if (code == lastCode && lastUrl.equals(ORequest.url)) {
            //同一次请求同样的错误
            repeat++;
        } else {
            lastCode = code;
            lastUrl = ORequest.url;
            repeat = 1;
        }
        if (repeat > MAX_REPEAT) {
            OkUtil.e(TAG, "The maximum limit of repeat is " + MAX_REPEAT + " . current repeat = " + repeat);
            return;
        }
        OkUtil.e(TAG, "**************************** start process code error = " + code + " and request ****************************");
        if (processCode(code)) {
            ORequest.request();
        }
        OkUtil.e(TAG, "**************************** end   process code error = " + code + " and request ****************************");
    }

    /**
     * 处理error code
     *
     * @param code
     * @return true： 需要再次尝试请求 false：不需要再次请求
     */
    protected boolean processCode(int code) {
        return false;
    }

    @Override
    public RE processResult(IWrap wrap, Class<T> clazz) {
        if (null == clazz) {
            return (RE) new IResult.StatusResult(wrap.getCode(), wrap.getMessage());
        } else {
            IPage page = wrap.getPage();
            boolean extra = null == page ? false : (page.getPage() >= page.getTotal());
            R result = null;
            JsonElement element = wrap.getBody();
            if (null != element) {
                result = (R) OkUtil.json2List(wrap.getBody(), clazz);
            }
            return (RE) new IResult.ObjResult(result, extra);
        }
    }
}