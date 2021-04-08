package com.oklib.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.oklib.OCallBack;

import okhttp3.Response;

public abstract class BitmapCallback extends OCallBack<Bitmap> {
    @Override
    public Bitmap onParse(Response response) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}