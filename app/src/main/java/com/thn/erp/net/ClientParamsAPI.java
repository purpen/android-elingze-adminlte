package com.thn.erp.net;

import com.thn.erp.Constants;
import com.thn.erp.utils.SPUtil;

import java.util.HashMap;

public class ClientParamsAPI {
    /**
     * 获得top100产品列表
     * @param start_time
     * @param end_time
     * @return
     */
    public static HashMap<String,String> getSalesTop100Params(String start_time, String end_time) {
        HashMap<String, String> params = new HashMap<>();
        params.put("start_time",start_time);
        params.put("end_time", end_time);
        params.put("token", SPUtil.read(Constants.TOKEN));
        return params;
    }
}
