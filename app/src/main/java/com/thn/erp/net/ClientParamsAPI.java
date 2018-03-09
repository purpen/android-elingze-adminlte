package com.thn.erp.net;

import com.thn.erp.Constants;
import com.thn.erp.utils.SPUtil;

import java.util.HashMap;

public class ClientParamsAPI {

    /**
     * 获取注册参数
     * @param account
     * @param userPsw
     * @param checkCode
     * @return
     */
    public static HashMap<String,String> getRegisterRequestParams(String account, String userPsw, String checkCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account",account);
        params.put("password",userPsw);
        params.put("code",checkCode);
        return params;
    }

    /**
     * 获取验证码参数
     * @param account
     * @return
     */
    public static HashMap<String,String> getCheckCodeRequestParams(String account) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account",account);
        return params;
    }

    /**
     * 获取重置密码验证码
     * @param account
     * @return
     */
    public static HashMap<String,String> getResetPasswordCodeRequestParams(String account) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone",account);
        return params;
    }

    /**
     * 重置为新密码
     * @param account
     * @param userPsw
     * @param checkCode
     * @return
     */
    public static HashMap<String,String> getResetPasswordRequestParams(String account, String userPsw, String checkCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone",account);
        params.put("code",checkCode);
        params.put("password",userPsw);
        return params;
    }
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

    public static HashMap<String,String> getSalesTrendsRequestParams(String start_time, String end_time) {
        HashMap<String, String> params = new HashMap<>();
        params.put("start_time",start_time);
        params.put("end_time", end_time);
        params.put("token", SPUtil.read(Constants.TOKEN));
        return params;
    }


    /**
     * 获得登录参数
     * @param account
     * @param password
     * @return
     */
    public static HashMap<String, String> getLoginRequestParams(String account, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
//        params.put("from_to", "2");     //登录渠道
        params.put("password", password);
        return params;
    }
}
