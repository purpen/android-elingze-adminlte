package com.thn.erp.net;

/**
 * Created by android on 2015/12/27
 * 网络请求url封装
 */
public class URL {
    public static final String BASE_URL = "http://k.taihuoniao.com/saasApi/"; //线上
//    public static final String BASE_URL = "http://t.taihuoniao.com/app/api";  // 测试环境


    // 公共接口
    public static final String H5_URL = "https://m.taihuoniao.com";

    public static final String AUTH_REGISTER = BASE_URL + "auth/register";
    public static final String AUTH_LOGIN = BASE_URL + "auth/login";
    public static final String SALES_TRENDS = BASE_URL + "survey/salesTrends";
    public static final String SALES_TOP100 = BASE_URL + "survey/salesRanking";
    public static final String SALES_PRICE_PER_USER = BASE_URL + "survey/customerPriceDistribution";
    public static final String REBUY_RATE = BASE_URL + "survey/repeatPurchase";
    public static final String AREA_DISTRIBUTION = BASE_URL + "survey/orderDistribution";
    public static final String TOP20_TAGS = BASE_URL + "survey/topFlag";
    public static final String ORDERS_IN_24HOURS = BASE_URL + "survey/hourOrder";
    public static final String SALE_CHANNEL = BASE_URL + "survey/sourceSales";

    /**
     * 获取注册验证码
     */
    public static final String AUTH_CHECK_CODE = BASE_URL + "auth/getRegisterCode";


    /**
     * 已合作产品列表
     */
    public static final String COOPERATE_PRODUCTS_LIST = BASE_URL + "product/cooperateProductLists";

    /**
     * (产品库)产品库列表
     */
    public static final String PRODUCTS_LIB_LIST = BASE_URL + "product/lists";

    /**
     * 产品详情
     */
    public static final String PRODUCTS_INFO = BASE_URL + "product/info";
    public static final String ADD_PRODUCTS_COOPERATED = BASE_URL + "product/trueCooperate";
    public static final String PRODUCTS_TEXT_LIST = BASE_URL + "product/describeLists";
    public static final String PRODUCTS_PICTURE_LIST = BASE_URL + "product/imageLists";
    public static final String PRODUCTS_VIDEO_LIST = BASE_URL + "product/videoLists";

    /**
     * 判断手机是否已注册
     */
    public static final String AUTH_PHONE_REGISTER = BASE_URL + "auth/phone";

    /**
     * 获取用户账户信息
     */
    public static final String USER_ACCOUNT_INFO = BASE_URL + "survey/index";
    /**
     * 获取用户信息
     */
    public static final String USER_INFO = BASE_URL + "auth/user";
    /**
     * 退出用户
     */
    public static final String LOGOUT_USER = BASE_URL + "auth/logout";

    /**
     * 换密码
     */
    public static final String UPDATE_PASSWORD = BASE_URL + "auth/changePassword";

    /**
     * 忘记密码验证码
     */
    public static final String FORGET_PASSWORD_CHECK_CODE =BASE_URL+"auth/getRetrieveCode" ;
    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = BASE_URL+"auth/retrievePassword";

    /**
     * 获取上传头像的token
     */
    public static final String GET_AVATAR_TOKEN = BASE_URL+"tools/getToken";

    /**
     * 七牛上传头像URL
     */
    public static final String QN_UPLOAD_AVATAR ="http://up.qiniu.com";

    /**
     * 添加头像到服务器
     */
    public static final String ADD_USER_IMAGE = BASE_URL + "auth/addUserImage";

    /**
     * 用户反馈
     */
    public static final String USER_FEEDBACK = BASE_URL+ "feedback/store";

    /**
     *
     */
    public static final String PRODUCTS_ARTICLE_LIST = BASE_URL+"product/articleLists";

    // 海报
    public static final String POSTER_POSTER_LIST       = "http://k.taihuoniao.com/saasApi/posters";
    public static final String POSTER_POSTER_DETAILS    = "http://k.taihuoniao.com/saasApi/poster";
}