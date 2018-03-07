package com.thn.erp.net;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * Created by Stephen on 2017/1/4 16:23
 * Email: 895745843@qq.com
 */

public abstract class HttpRequestCallback {

    public void onStart(){}

    public void onSuccess(String json){};

    public void onSuccess(Bitmap json){};

    public abstract void onFailure(IOException e);
}
