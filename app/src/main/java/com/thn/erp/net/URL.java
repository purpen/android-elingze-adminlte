package com.thn.erp.net;

/**
 * Created by android on 2015/12/27
 * 网络请求url封装
 */
public class URL {
    public static final String BASE_URL = "https://fx.taihuoniao.com/api/v1.0/"; //线上
//    public static final String BASE_URL = "http://t.taihuoniao.com/app/api";  // 测试环境


    // 公共接口
    public static final String H5_URL = "https://m.taihuoniao.com";

    public static final String AUTH_REGISTER = BASE_URL + "auth/register";
    //商家登录
    public static final String AUTH_LOGIN = BASE_URL + "auth/business_login";

    /**
     * 销售额URL
     */
    public static final String SALES_TRENDS = BASE_URL + "stats/sale_amount_trend";

    /**
     * 产品销售量排行榜
     */
    public static final String SALES_TOP100 = BASE_URL + "stats/sale_leader_board";

    public static final String SALES_PRICE_PER_USER = BASE_URL + "survey/customerPriceDistribution";
    public static final String REBUY_RATE = BASE_URL + "survey/repeatPurchase";
    public static final String AREA_DISTRIBUTION = BASE_URL + "survey/orderDistribution";
    public static final String TOP20_TAGS = BASE_URL + "survey/topFlag";
    public static final String ORDERS_IN_24HOURS = BASE_URL + "survey/hourOrder";
    public static final String SALE_CHANNEL = BASE_URL + "survey/sourceSales";

    // 获取七牛Token
    public static final String FILE_TOKEN = BASE_URL + "assets/up_token";

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
    public static final String FORGET_PASSWORD_CHECK_CODE = BASE_URL + "auth/getRetrieveCode";
    /**
     * 重置密码
     */
    public static final String RESET_PASSWORD = BASE_URL + "auth/retrievePassword";

    /**
     * 获取上传头像的token
     */
    public static final String GET_AVATAR_TOKEN = BASE_URL + "tools/getToken";

    /**
     * 七牛上传头像URL
     */
    public static final String QN_UPLOAD_AVATAR = "http://up.qiniu.com";

    /**
     * 添加头像到服务器
     */
    public static final String ADD_USER_IMAGE = BASE_URL + "auth/addUserImage";

    /**
     * 用户反馈
     */
    public static final String USER_FEEDBACK = BASE_URL + "feedback/store";

    /**
     *
     */
    public static final String PRODUCTS_ARTICLE_LIST = BASE_URL + "product/articleLists";


    // 所有商品列表
//    public static final String PRODUCTS_RECOMMEND       = "products";
    public static final String QINIU_TOKEN = "assets/up_token";
    public static final String PRODUCT_ADD = "products";

    // 品牌
    public static final String PRODUCT_BRAND = "brands";       //品牌列表
    public static final String PRODUCT_CATEGORY = "categories";   //分类
    public static final String PRODUCT_BRAND_DETIALS = "brands/<rid>"; //品牌详情
    public static final String PRODUCT_BRAND_ADD = "brands";       //新增品牌
    public static final String PRODUCT_BRAND_UPDATE = "brands/<rid>"; //更新品牌
    public static final String PRODUCT_BRAND_DELETE = "brands/<rid>"; //删除品牌

    /**
     * 分销客户列表
     */
    public static final String CUSTOMER_LIST = BASE_URL + "customers";

    /**
     * 客户等级列表
     */
    public static final String CUSTOMER_GRADE_LIST = BASE_URL + "customer_grades";

    /**
     * 所有商品列表
     */
    public static final String GOODS_LIST = BASE_URL + "products";
    /**
     * 新增订单
     */
    public static final String ADD_ORDER = BASE_URL + "orders/create";
    /**
     * 获得运费
     */
    public static final String GET_FREIGHT = BASE_URL + "orders/freight";

    /**
     * 地址列表
     */
    public static final String ADDRESS_LIST = BASE_URL + "address";

    /**
     * 删收货地址
     */
    public static final String DELETE_ADDRESS = BASE_URL + "address/";

    /**
     * 获取省列表
     */
    public static final String PROVINCE_LIST = BASE_URL + "places/provinces";

    public static final String CITIES_LIST = BASE_URL + "places/";

    /**
     * 提交收货地址
     */
    public static final String COMMIT_ADDRESS = BASE_URL + "address";

    /**
     * 订单列表
     */
    public static final String ORDER_LIST = BASE_URL + "orders";

    /**
     * 商户appkey和appsecret
     */
    public static final String APPKEY_APPSECRET = BASE_URL + "auth/exchange_token";


    /**
     * 获取今天，过去7天，过去30天销售数据
     */
    public static final String SALES_DAYS_TRENDS = BASE_URL + "stats/stat_collect";

    /**
     * 获取订单数量
     */
    public static final String STATISTIC_ORDER_TRENDS = BASE_URL + "stats/order_quantity_trend";

    /**
     * 获取默认地址
     */
    public static String GET_DEFAULT_ADDRESS = BASE_URL + "address/is_default";

    /**
     * 获取SKU列表
     */
    public static String SKU_LIST = BASE_URL + "products/skus";

    /**
     * 获取sku信息
     */
    public static String SKU_INFO = BASE_URL + "products/by_sku";

    /**
     * 添加购物车
     */
    public static String ADD_SHOPCART = BASE_URL + "cart";

    /**
     * 轮播图
     */
    public static String SLIDES = BASE_URL + "common/slides";

    /**
     * 搜索历史
     */
    public static String SEARCH_HISTORY = BASE_URL + "search/history";
}
