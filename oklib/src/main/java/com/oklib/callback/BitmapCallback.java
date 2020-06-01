package com.oklib.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public abstract class BitmapCallback extends BaseCallBack<Bitmap> {
    @Override
    public Bitmap onParse(Response response) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}