package com.thn.erp.net;
import java.io.IOException;
import java.util.HashMap;

public final class RequestManager {
    public interface RequestCallBack{
        void onStart();
        void onSuccess(String json);
        void onFailure(IOException e);
    }
    private int page;
    public static final int PAGE_SIZE = 10;
    public RequestManager(int requestType,String url,int page, final RequestCallBack requestCallBack){
        HashMap<String, String> params = ClientParamsAPI.getGoodsList("", page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (requestCallBack!=null) requestCallBack.onStart();
            }

            @Override
            public void onSuccess(String json) {
                if (requestCallBack!=null) requestCallBack.onSuccess(json);
            }

            @Override
            public void onFailure(IOException e) {
                onFailure(e);
            }
        });
    }

    public void request(){

    }
}
