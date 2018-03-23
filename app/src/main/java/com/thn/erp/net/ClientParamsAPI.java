package com.thn.erp.net;

import com.thn.erp.Constants;
import com.thn.erp.utils.SPUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ClientParamsAPI {
    public static final String app_key = "fi2N0mZVRUjzCkwtKWbM";
    public static final String app_secret = "cc3c36db353b4543aba12db6bbcff6cb53a592b2";

    private static HashMap<String,String> generateCommonParams(){
        HashMap<String, String> params = new HashMap<>();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = generateRandomString();
        params.put("app_key",app_key);
        params.put("timestamp",timeStamp);
        params.put("nonce_str",nonceStr);
        Map treeMap=new TreeMap<String,String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                }
        );
        treeMap.put("app_key",app_key);
        treeMap.put("timestamp",timeStamp);
        treeMap.put("nonce_str",nonceStr);
        params.put("sign",generateSign(treeMap));
        return params;
    }

    /**
     * nonce_str
     * 获得随机字符串
     * @return
     */
    private static String generateRandomString() {
        String nonce_str ="";
        String randomStr="ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        for (int i = 0; i < 16; i++) {
            nonce_str += randomStr.charAt((int) Math.floor(Math.random() * randomStr.length()));
        }
        return nonce_str;
    }

    private static String generateSign(Map map) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = (String)map.get(key);
            sb.append(key+"="+value+"&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.append(app_secret);
        return new String(Hex.encodeHex(DigestUtils.sha1(sb.toString())));
    }

    /**
     * 获取注册参数
     * @param account
     * @param userPsw
     * @param checkCode
     * @return
     */
    public static HashMap<String,String> getRegisterRequestParams(String account, String userPsw, String checkCode) {
//        1059232202@qq.com
        HashMap<String, String> params = generateCommonParams();
        params.put("email",account);
        params.put("username",checkCode);
        params.put("password",userPsw);
//        params.put("code",checkCode);
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
        HashMap<String, String> params = generateCommonParams();
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
        HashMap<String, String> params = generateCommonParams();
        params.put("email", account);
//        params.put("from_to", "2");     //登录渠道
        params.put("password", password);
        return params;
    }

    /**
     * 获取分销客户列表
     * @param page 当前第几页
     * @return
     */
    public static HashMap<String,String> getCustomerList(int page) {
        HashMap<String, String> params = generateCommonParams();
//        params.put("token",SPUtil.read(Constants.TOKEN));
        params.put("page", String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 新增用户
     * @return
     */
    public static HashMap<String,String> addCustomer(int grade_id,String name,String province,String city,String area,String street_address,String mobile,String phone,String email) {
        HashMap<String, String> params = generateCommonParams();
//        params.put("token",SPUtil.read(Constants.TOKEN));
        params.put("grade_id", String.valueOf(grade_id));
        params.put("name",name);
        params.put("province",province);
        params.put("city",city);
        params.put("area",area);
        params.put("street_address",street_address);
        params.put("mobile",mobile);
        params.put("phone",phone);
        params.put("email",email);
        return params;
    }

    /**
     * 获取商品列表或某分类所有商品
     * @param page
     * @return
     */
    public static HashMap<String,String> getGoodsList(String cid,int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("cid", String.valueOf(cid));
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }
}
