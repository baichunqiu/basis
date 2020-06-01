package com.business.process;

import android.util.Log;

import com.oklib.core.ReQuest;

/**
 * @Author: BaiCQ
 * @ClassName: DefauProcessor
 * @CreateDate: 2019/3/27 14:08
 * @Description:
 */
public class DefauProcessor extends BaseProcessor {

    protected boolean processCode(int code) {
        Log.e("processCode", "code = " + code);
        return false;
    }
}