package com.thn.erp.net;

import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;

import com.thn.erp.Constants;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.SPUtil;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Stephen on 2017/1/4 16:48
 * Email: 895745843@qq.com
 */

public class HttpRequest {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public final static int CONNECT_TIMEOUT = 30;
    public final static int READ_TIMEOUT = 30;
    public final static int WRITE_TIMEOUT = 30;

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .sslSocketFactory(createSSLSocketFactory())
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            })
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
            .build();

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


    public static Call sendRequest(String type, String requestUrl, HashMap<String, String> params, HttpRequestCallback callback) {
        if (null == params) throw new IllegalArgumentException("argument params can not be null ");
        if (TextUtils.isEmpty(requestUrl))
            throw new IllegalArgumentException("argument requestUrl can not be null");
        if (null == callback)
            throw new IllegalArgumentException("argument callback can not be null");
        final String url;
        if (!requestUrl.contains("http")) {
            url = URL.BASE_URL + requestUrl;
        } else {
            url = requestUrl;
        }

        Request request = getRequest(type, url, params);

        if (null == request) return null;

        Call call = mOkHttpClient.newCall(request);

        callback.onStart();
        final NetWorkHandler handler = new NetWorkHandler(callback);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = NetWorkHandler.CALLBACK_FAILURE;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Message message = Message.obtain();
                    message.what = NetWorkHandler.CALLBACK_SUCCESS;
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                } else {
                    LogUtil.e("服务器响应失败： URL = "+ url + " 状态码 " + response.code());
                    Message message = Message.obtain();
                    message.what = NetWorkHandler.CALLBACK_FAILURE;
                    message.obj = new IOException("INTERNAL SERVER ERROR");
                    handler.sendMessage(message);
                }
            }
        });

        return call;
    }

    /**
     * 包含文件参数的表单上传
     *
     * @param requestUrl
     * @param params
     * @param callback
     * @return
     */
    public static Call sendRequest(String requestUrl, HashMap<String, Object> params, HttpRequestCallback callback) {
        if (null == params) throw new IllegalArgumentException("argument params can not be null ");
        if (TextUtils.isEmpty(requestUrl))
            throw new IllegalArgumentException("argument requestUrl can not be null");
        if (null == callback)
            throw new IllegalArgumentException("argument callback can not be null");
        String url;
        if (!requestUrl.contains("http")) {
            url = URL.BASE_URL + requestUrl;
        } else {
            url = requestUrl;
        }

        Request request = getRequest(url, params);

        if (null == request) return null;

        Call call = mOkHttpClient.newCall(request);

        callback.onStart();
        final NetWorkHandler handler = new NetWorkHandler(callback);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = NetWorkHandler.CALLBACK_FAILURE;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Message message = Message.obtain();
                    message.what = NetWorkHandler.CALLBACK_SUCCESS;
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                } else {
                    LogUtil.e("response.isSuccessful()==false");
                }
            }
        });

        return call;
    }

    /**
     * 登录请求
     *
     * @param callback
     * @return
     */
    public static Call sendRequest(HashMap<String, String> params, HttpRequestCallback callback) {
        if (null == params)
            throw new IllegalArgumentException("argument callback can not be null");

        if (null == callback)
            throw new IllegalArgumentException("argument callback can not be null");

        String str = params.get("email") + ":" + params.get("password");
        str = "Basic  " + Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        Request request = null;
        request = new Request.Builder()
                .url(URL.AUTH_LOGIN)
                .addHeader("Authorization", str.trim())
                .post(getRequestBody(params))
                .build();
        Call call = mOkHttpClient.newCall(request);

        callback.onStart();
        final NetWorkHandler handler = new NetWorkHandler(callback);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = NetWorkHandler.CALLBACK_FAILURE;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Message message = Message.obtain();
                    message.what = NetWorkHandler.CALLBACK_SUCCESS;
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                } else {
                    LogUtil.e("response.isSuccessful()==false");
                }
            }
        });

        return call;
    }


    private static Request getRequest(String url, HashMap<String, Object> params) {
        Request request = new Request.Builder()
                .url(url)
                .post(getMultipartBody(params))
                .build();
        return request;
    }

    private static Request getRequest(String type, String url, HashMap<String, String> params) {
        Request request = null;
        String str=SPUtil.read(Constants.LOGIN_INFO);
        if (!TextUtils.isEmpty(str)){
            str = "Basic  " + Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        }
        switch (type) {
            case POST:
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", str.trim())
                        .post(getRequestBody(params))
                        .build();
                break;
            case GET:
                request = new Request.Builder()
                        .url(getUrl(url, params))
                        .addHeader("Authorization", str.trim())
                        .build();
                break;
            case PUT:
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", str.trim())
                        .put(getRequestBody(params))
                        .build();
                break;
            case DELETE:
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", str.trim())
                        .build();
                break;
            default:
                break;
        }

        return request;
    }


    private static RequestBody getMultipartBody(HashMap<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getValue() instanceof File) {
                builder.addFormDataPart(entry.getKey(), "tmp.jpg", RequestBody.create(MEDIA_TYPE_JPG, (File) entry.getValue()));
            } else if (entry.getValue() instanceof String) {
                builder.addFormDataPart(entry.getKey(), (String) entry.getValue());
            }
        }
        return builder.build();
    }


    /**
     * 拼接get参数
     *
     * @param url
     * @param params
     * @return
     */
    private static String getUrl(String url, HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        if (params.isEmpty()) return builder.toString();
        builder.append("?");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return builder.deleteCharAt(builder.lastIndexOf("&")).toString();
    }


    private static RequestBody getRequestBody(HashMap<String, String> params) {
        RequestBody requestBody = RequestBody.create(JSON, JsonUtil.toJson(params));
        return requestBody;
    }
}
