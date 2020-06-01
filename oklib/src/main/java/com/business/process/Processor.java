package com.business.process;

import com.oklib.core.ReQuest;

/**
 * @author: BaiCQ
 * @ClassName: Processor
 * @date: 2018/6/27
 * @Description: error code processor 错误处理接口
 */
public interface Processor {
    void process(int code, ReQuest request);
}
