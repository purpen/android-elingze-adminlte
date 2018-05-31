package com.thn.erp.net;

import android.os.Message;
import android.text.TextUtils;

import com.thn.erp.Constants;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.LogUtil;
import com.thn.basemodule.tools.SPUtil;

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

    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;

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

    /**
     * 以GET 方式单独请求数据
     *
     * @param requestUrl 请求Url
     * @param callback   callback
     * @return call
     */
    public static Call getRequest(String requestUrl, HttpRequestCallback callback) {
        return getRequest(requestUrl, null, callback);
    }

    public static Call getRequest(String requestUrl, String urlSuffix, HttpRequestCallback callback) {
        HashMap<String, String> defaultParams = ClientParamsAPI.getDefaultParams();
        return getRequest(requestUrl, urlSuffix, defaultParams, callback);
    }

    public static Call getRequest(String requestUrl, String urlSuffix, HashMap params, HttpRequestCallback callback) {
        String requestUrl1 = requestUrl + File.separator + urlSuffix;
        return sendRequest(GET, requestUrl1, params, callback);
    }

    /**
     * 接口请求方法
     *
     * @param type       请求类型
     * @param requestUrl url
     * @param callback   callback
     * @return call
     */
    public static Call sendRequest(String type, String requestUrl, HttpRequestCallback callback) {
        return sendRequest(type, requestUrl, null, ClientParamsAPI.getDefaultParams(), callback);
    }

    public static Call sendRequest(String type, String requestUrl, HashMap params, HttpRequestCallback callback) {
        return sendRequest(type, requestUrl, null, params, callback);
    }

    public static Call sendRequest(String type, String requestUrl, String authorization, HashMap params, HttpRequestCallback callback) {
        if (type == null || TextUtils.isEmpty(requestUrl) || null == params || callback == null)
            throw new IllegalArgumentException("request params can not be null ");
        final String url = requestUrl.contains("http") ? requestUrl : URL.BASE_URL + requestUrl;
        authorization = TextUtils.isEmpty(authorization) ? SPUtil.read(Constants.AUTHORIZATION) : authorization;

        LogUtil.e("----------sendRequest: " + "\n Type:" + type + "\n URL: " + url + "\n Authorization: " + authorization + "\n Params: " + params);

        Request request = getRequest(type, url, authorization, params);
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
                    LogUtil.e("服务器响应失败： URL = " + url + " 状态码 " + response.code());
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
     * 获取 OkHttpRequest
     *
     * @param type          type
     * @param url           url
     * @param authorization authorization
     * @param params        params
     * @return request
     */
    private static Request getRequest(String type, String url, String authorization, HashMap params) {
        switch (type) {
            case POST:
                return new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", authorization)
                        .post(getRequestBody(params))
                        .build();
            case GET:
                return new Request.Builder()
                        .url(getUrl(url, params))
                        .addHeader("Authorization", authorization)
                        .get()
                        .build();
            case PUT:
                return new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", authorization)
                        .put(getRequestBody(params))
                        .build();
            case DELETE:
                return new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", authorization)
                        .delete(getRequestBody(params))
                        .build();
            default:
                return null;
        }
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

    /**
     * 获取RequestBody 参数
     *
     * @param params
     * @return
     */
    private static RequestBody getRequestBody(HashMap params) {
        return RequestBody.create(JSON, JsonUtil.toJson(params));
    }

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
}
