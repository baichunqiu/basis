package com.qunli.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.kit.Logger;
import com.oklib.OkApi;
import com.oklib.callback.StringCallBack;

import java.util.Date;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TestActivity";
    private String MEET_ID = "7554318394910745345";//1-5号
    String url = "http://mindoc.qunlivideo.com/uploads/201906/flutter/attach_15a8ee63790b1e01.png";
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMeetDetails(MEET_ID,"test001");
    }

    private void getMeetDetails(String meetId, String userid) {
        final String uri = "/v1/meetings/" + meetId + "?userid=" + userid + "&instanceid=3";
        String url = Signaturer.HOST + uri;
        final String timestamp = String.valueOf(new Date().getTime() / 1000);
        final String nonce = String.valueOf(count++);
        final String signature = Signaturer.signForGet(nonce, timestamp, uri);
        Log.e(TAG, "timestamp = " + timestamp);
        Log.e(TAG, "uri = " + uri);
        Log.e(TAG, "signature = " + signature);
        OkApi.get(url, null, new StringCallBack() {
            @Override
            public void onBefore(Request.Builder request) {
                request.addHeader("X-TC-Signature", signature);
                request.addHeader("X-TC-Nonce", nonce);
                request.addHeader("AppId", Signaturer.APP_ID);
                request.addHeader("X-TC-Key", Signaturer.SECRET_ID);
                request.addHeader("X-TC-Timestamp", timestamp);
            }

            @Override
            public void onResponse(String result) {
                Logger.e("TestActivity", "result = " + result);
            }

            @Override
            public void onError(Exception e) {
                Logger.e("TestActivity", "error = " + e.getMessage());
            }
        });
    }
}
