package com.qunli.demo;

import android.view.View;

import com.basis.base.BaseActivity;
import com.basis.net.LoadTag;
import com.basis.widget.WXDialog;
import com.bcq.net.wrapper.Wrapper;
import com.bcq.net.wrapper.interfaces.IParse;
import com.bcq.net.wrapper.interfaces.IResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kit.cache.GsonUtil;
import com.kit.utils.KToast;
import com.kit.utils.Logger;
import com.bcq.net.net.ListCallback;
import com.bcq.net.Request;
import com.bcq.net.api.Method;
import com.bcq.net.api.OCallBack;
import com.bcq.net.OkApi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class NetTestActvivty extends BaseActivity implements View.OnClickListener {
    @Override
    public int setLayoutId() {
        return R.layout.activity_net_test;
    }

    @Override
    public void init() {
        getView(R.id.okapi).setOnClickListener(this);
        getView(R.id.netapi).setOnClickListener(this);
        initBarWrapper();
    }

    public void initBarWrapper() {
        getWrapBar().setTitle(R.string.str_api)
                .work();
    }

    WXDialog dialog;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.okapi:
                oklogin();
                break;
            case R.id.netapi:
                netlogin();
                break;
        }
    }

    public void oklogin() {
        String url = "https://ravc-user.test.qunlivideo.com/api/v1/login/app";
        Map<String, Object> params = new HashMap<>(2);
        params.put("username", "test001");
        params.put("password", "ql123456");
        OkApi.post(url, params, new OCallBack<IResult.StatusResult>() {
            @Override
            public IResult.StatusResult onParse(Response response) throws Exception {
                int httpCode = response.code();
                String json = response.body().string();
                IParse<Wrapper> parse = new LoginParser();
                Logger.e("oklogin", "httpCode = " + httpCode + " json = " + json);
                new IResult.StatusResult(httpCode,json);
                Wrapper wrapper = parse.parse(httpCode,json);
                return new IResult.StatusResult(wrapper.getCode(),wrapper.getMessage());
            }

            @Override
            public void onResult(IResult.StatusResult result) {
                super.onResult(result);
            }
        });
    }

    public void netlogin() {
        String url = "https://ravc-user.test.qunlivideo.com/api/v1/login/app";
        Map<String, Object> params = new HashMap<>(2);
        params.put("username", "test001");
        params.put("password", "ql123456");
        Request.request(new LoadTag(activity, "登录"), url, params, new LoginParser(), Method.post,
                new ListCallback<TokenBean>(TokenBean.class) {
                    @Override
                    public void onResult(IResult.ObjResult<List<TokenBean>> result) {
                        super.onResult(result);
                    }

                    @Override
                    public void onError(int code, String message) {
                        super.onError(code, message);
                        KToast.show("登录失败 code:[" + code + "] " + message);
                    }
                });
    }

    public static class LoginParser implements IParse<Wrapper> {
        @Override
        public Wrapper parse(int code, String json) {
            Logger.e("json = " + json);
            Wrapper info = new Wrapper();
            info.setCode(code);
            JsonElement result = JsonParser.parseString(json);
            if (result instanceof JsonObject) {
                JsonObject resulObj = (JsonObject) result;
                info.setCode(resulObj.get("code").getAsInt());
                info.setMessage(resulObj.get("message").getAsString());
            }
            Logger.e("LoginParser", "wrapper = " + GsonUtil.obj2Json(info));
            return info;
        }

        @Override
        public boolean ok(int code) {
            return code == 200 || code == 0;
        }
    }

    public class TokenBean implements Serializable {

        /**
         * success : true
         * code : 0
         * message : 登录成功
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDc5MzQyNDcsInVzZXJuYW1lIjoiZGVtbyJ9.XyOAPdqu9-lV91_1yQTAngX1DeblTcCHJ7Gjhy2XrL0
         */

        private boolean success;
        private int code;
        private String message;
        private String token;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


}
