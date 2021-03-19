package com.business.parse;

import com.kit.utils.Logger;
import com.oklib.core.ReQuest;

/**
 * @Author: BaiCQ
 * @ClassName: BaseProcessor
 * @CreateDate: 2019/3/27 14:08
 * @Description: 数据处理封装
 */
public abstract class BaseProcessor<R, E> implements Processor<R, E> {
    private final static String TAG = "BaseProcessor";
    //最大重复请求次数
    private final static int MAX_REPEAT = 1;
    //重复次数
    private int repeat = 1;
    //缓存上次code
    private int lastCode = 0;
    private String lastUrl = "";

    @Override
    public final void process(int code, ReQuest reQuest) {
        if (code == lastCode && lastUrl.equals(reQuest.url)) {
            //同一次请求同样的错误¬
            repeat++;
        } else {
            lastCode = code;
            lastUrl = reQuest.url;
            repeat = 1;
        }
        if (repeat > MAX_REPEAT) {
            Logger.e(TAG, "The maximum limit of repeat is " + MAX_REPEAT + " . current repeat = " + repeat);
            return;
        }
        Logger.e(TAG, "**************************** start process code error = " + code + " and request ****************************");
        if (processCode(code)) {
            reQuest.request();
        }
        Logger.e(TAG, "**************************** end   process code error = " + code + " and request ****************************");
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

    public abstract R parseResult(Wrapper wrapper);

    public abstract E parseExtra(Wrapper wrapper);
}