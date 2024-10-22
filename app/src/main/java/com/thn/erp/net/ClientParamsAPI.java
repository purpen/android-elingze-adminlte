package com.thn.erp.net;

import com.thn.erp.Constants;
import com.thn.erp.goods.category.UpdateCetegoryBean;
import com.thn.erp.net.paramsBean.UpdateBrandBean;
import com.thn.erp.utils.BeanHelper;
import com.thn.basemodule.tools.SPUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ClientParamsAPI {
//    public static final String app_key = "fi2N0mZVRUjzCkwtKWbM";
//    public static final String app_secret = "cc3c36db353b4543aba12db6bbcff6cb53a592b2";

    private static HashMap<String, String> generateCommonParams() {
        HashMap<String, String> params = new HashMap<>();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = generateRandomString();
        String app_key=SPUtil.read(Constants.APP_KEY);
        params.put("app_key", app_key);
        params.put("timestamp", timeStamp);
        params.put("nonce_str", nonceStr);
        Map treeMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                }
        );
        treeMap.put("app_key", app_key);
        treeMap.put("timestamp", timeStamp);
        treeMap.put("nonce_str", nonceStr);
        params.put("sign", generateSign(treeMap));
        return params;
    }

    /**
     * nonce_str
     * 获得随机字符串
     *
     * @return
     */
    private static String generateRandomString() {
        String nonce_str = "";
        String randomStr = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        for (int i = 0; i < 16; i++) {
            nonce_str += randomStr.charAt((int) Math.floor(Math.random() * randomStr.length()));
        }
        return nonce_str;
    }

    private static String generateSign(Map map) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (String) map.get(key);
            sb.append(key + "=" + value + "&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        sb.append(SPUtil.read(Constants.APP_SECRET));
        return new String(Hex.encodeHex(DigestUtils.sha1(sb.toString())));
    }

    /**
     * 获取注册参数
     *
     * @param account
     * @param userPsw
     * @param checkCode
     * @return
     */
    public static HashMap<String, String> getRegisterRequestParams(String account, String userPsw, String checkCode) {
//        1059232202@qq.com
        HashMap<String, String> params = generateCommonParams();
        params.put("email", account);
        params.put("username", checkCode);
        params.put("password", userPsw);
//        params.put("code",checkCode);
        return params;
    }

    /**
     * 获取验证码参数
     *
     * @param account
     * @return
     */
    public static HashMap<String, String> getCheckCodeRequestParams(String account) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
        return params;
    }

    /**
     * 获取重置密码验证码
     *
     * @param account
     * @return
     */
    public static HashMap<String, String> getResetPasswordCodeRequestParams(String account) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", account);
        return params;
    }

    /**
     * 重置为新密码
     *
     * @param account
     * @param userPsw
     * @param checkCode
     * @return
     */
    public static HashMap<String, String> getResetPasswordRequestParams(String account, String userPsw, String checkCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", account);
        params.put("code", checkCode);
        params.put("password", userPsw);
        return params;
    }

    /**
     * 获得top100产品列表
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static HashMap<String, String> getSalesTop100Params(String start_time, String end_time) {
        HashMap<String, String> params = generateCommonParams();
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        return params;
    }

    public static HashMap<String, String> getSalesTrendsRequestParams(String start_time, String end_time) {
        HashMap<String, String> params = generateCommonParams();
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        return params;
    }


    /**
     * 获得登录参数
     *
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
     * 获取新增商品参数
     *
     * @param name
     * @param cover_id
     * @param price
     * @return
     */
    public static HashMap<String, String> getProductSAddParams(String name, String cover_id, String price) {
        HashMap<String, String> params = generateCommonParams();
        params.put("name", name);
        params.put("cover_id", cover_id);
        params.put("price", price);
        return params;
    }

    /**
     * 获取分销客户列表
     *
     * @param page page 当前第几页
     * @return
     */
    public static HashMap<String, String> getCustomerList(int page) {
        HashMap<String, String> params = generateCommonParams();
//        params.put("token",SPUtil.read(Constants.TOKEN));
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 新增用户
     *
     * @return
     */
    public static HashMap<String, String> addCustomer(int grade_id, String name, String province, String city, String area, String street_address, String mobile, String phone, String email) {
        HashMap<String, String> params = generateCommonParams();
//        params.put("token",SPUtil.read(Constants.TOKEN));
        params.put("grade_id", String.valueOf(grade_id));
        params.put("name", name);
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("street_address", street_address);
        params.put("mobile", mobile);
        params.put("phone", phone);
        params.put("email", email);
        return params;
    }

    /**
     * 获取商品列表或某分类所有商品
     *
     * @param page
     * @return
     */
    public static HashMap<String, String> getGoodsList(String cid, int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("cid", String.valueOf(cid));
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }


    /**
     * 创建订单
     *
     * @return
     */
    public static HashMap createOrderParams(long store_id, String address_rid, double freight, ArrayList items) {
        HashMap<String, String> params = generateCommonParams();
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            hashMap.put(entry.getKey(), entry.getValue());
        }
        hashMap.put("store_id", store_id);
        hashMap.put("address_rid", address_rid);
        hashMap.put("freight", freight);
        hashMap.put("items", items);
        HashMap hashMap1 = new HashMap();
        return hashMap;
    }

    /**
     * 获得运费
     *
     * @return
     */
    public static HashMap<String, String> getFreightParams() {
        return generateCommonParams();
    }

    /**
     * 获取地址列表
     *
     * @return
     */
    public static HashMap<String, String> getAddressListParams() {
        return generateCommonParams();
    }

    /**
     * 获取默认地址
     *
     * @return
     */
    public static HashMap<String, String> getDefaultAddressParams() {
        return generateCommonParams();
    }

    /**
     * 删除收货地址
     *
     * @return
     */
    public static HashMap<String, String> deleteAddressParams() {
        return generateCommonParams();
    }

    /**
     * 获取省
     */
    public static HashMap<String, String> getProvinceParams() {
        return generateCommonParams();
    }

    /**
     * 接口仅需默认参数
     *
     * @return
     */
    public static HashMap<String, String> getDefaultParams() {
        return generateCommonParams();
    }

    /**
     * @param consigneeName
     * @param phone
     * @param provinceId
     * @param cityId
     * @param countyId
     * @param townId
     * @param addressDetail
     * @param zipCode
     * @param checked
     * @return
     */
    public static HashMap<String, String> getCommitAddressParams(String consigneeName, String phone, String provinceId, String cityId, String countyId, String townId, String addressDetail, String zipCode, boolean checked) {
        HashMap<String, String> params = generateCommonParams();
        params.put("first_name", consigneeName);
        params.put("mobile", phone);
        params.put("province_id", provinceId);
        params.put("city_id", cityId);
        params.put("town_id", countyId);
        params.put("area_id", townId);
        params.put("street_address", addressDetail);
        params.put("zipcode", zipCode);
        params.put("is_default", String.valueOf(checked));
        return params;
    }

    /**
     * 获取订单列表
     *
     * @param status 订单状态
     * @param page
     * @return
     */
    public static HashMap<String, String> getOrderList(String status, int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("status", status);
        params.put("page", String.valueOf(page));
        params.put("per_page", Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 获取订单详情
     */
    public static HashMap<String, String> getOrderDetailParams() {
        return generateCommonParams();
    }

    public static HashMap<String, Object> getBrandUpdateParams(UpdateBrandBean brandBean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, String> hashMap1 = getDefaultParams();
        HashMap<String, Object> hashMap2 = BeanHelper.objToHash(brandBean);
        hashMap.putAll(hashMap1);
        hashMap.putAll(hashMap2);

//        hashMap.put("name", brandBean.getName());
//        hashMap.put("features", brandBean );
//        hashMap.put("description", brandBean );
//        hashMap.put("supplier_id", brandBean.getSupplier_id());
//        hashMap.put("is_recommended", brandBean.getIs_recommended());
//        hashMap.put("sort_order",brandBean.getSort_order() );
        return hashMap;
    }

    public static HashMap<String, Object> getCategoryUpdateParams(UpdateCetegoryBean brandBean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, String> hashMap1 = getDefaultParams();
        HashMap<String, Object> hashMap2 = BeanHelper.objToHash(brandBean);
        hashMap.putAll(hashMap1);
        hashMap.putAll(hashMap2);
        return hashMap;
    }

    /**
     * 获取SKU列表
     * @return
     * @param rid
     */
    public static HashMap<String,String> getSKUListParams(String rid) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid",rid);
        return params;
    }

    /**
     * 添加购物车
     * @return
     */
    public static HashMap<String,String> getShopCartParams(String rid,int quantity) {
        HashMap<String, String> params = generateCommonParams();
        params.put("rid",rid);
        params.put("quantity",String.valueOf(quantity));
        return params;
    }

    /**
     * 获取客户等级列表
     * @param page
     * @return
     */
    public static HashMap<String,String> getCustomerGradeParams(int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 新增客户分类
     * @param text
     * @return
     */
    public static HashMap<String,String> getAddGradeParams(String text) {
        HashMap<String, String> params = generateCommonParams();
        params.put("name",text);
        return params;
    }

    /**
     * 轮播图
     * @param spot
     * @param page
     * @return
     */
    public static HashMap<String,String> slideParam(String spot,int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("spot",spot);
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 搜索历史
     * @param page
     * @return
     */
    public static HashMap<String,String> searchHistoryParams(int page) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        return params;
    }

    /**
     * 搜索结果页
     * @return
     */
    public static HashMap<String,String> searchResultParam(int page,String qk) {
        HashMap<String, String> params = generateCommonParams();
        params.put("page",String.valueOf(page));
        params.put("per_page",Constants.PAGE_SIZE);
        params.put("qk",qk);
        return params;
    }

    /**
     * 商户appkey和appSecret
     * @param storeId
     * @return
     */
    public static HashMap<String,String> appKeyAndSecretParams(String storeId) {
        HashMap<String, String> params = generateCommonParams();
        params.put("store_rid",storeId);
        return  params;
    }


    /**
     * 获得订单量趋势
     * @param start_time
     * @param end_time
     * @return
     */
    public static HashMap<String,String> getOrderTrendsRequestParams(String start_time, String end_time) {
        HashMap<String, String> params = generateCommonParams();
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        return params;
    }
}
