package com.oklib.callback;

import okhttp3.Response;

public abstract class StringCallBack extends BaseCallBack<String> {
    @Override
    public String onParse(Response response) throws Exception {
        return response.body().string();
    }
}
