package com.thn.erp.net;

/**
 * Created by taihuoniao on 2016/3/14.
 * 常量
 */
public class DataConstants {
    public static final String TYPE = "type";//推送返回key
    public static final String TARGET_ID = "target_id";//推送返回的key
    public static final String FIU = "user_id";//用户与平台的绑定
    public static final String PUSH_STATUS = "push_status";
    public static final String STATUS = "status";
    //判断从哪个activity跳转到登录界面
    public static final int ElseActivity = 102;//其他不需要刷新界面的activity
    public static final int WellGoodsFragment = 103;//好货
    public static final int IndexFragment = 105;//首页
    public static final int FindFragment = 106;//发现页
    public static final int SearchActivity = 107;//搜索页
    public static final int FindActivity = 108;//发现大图
    public static final int QJDetailActivity = 109;//情景详情
    public static final int BuyGoodDetailsActivity = 110;//可以购买的商品详情
    public static final int ActivityDetail = 111;//活动详情
    public static final int QJCategoryActivity = 112;//情景分类
    public static final int BrandDetailsActivity = 114;//品牌详情
    public static final int ZONE_DETAIL_ACTIVITY = 115;//地盘详情
    public static final String BroadBrandDetails = "com.taihuoniao.fiu.branddetails";
    public static final String BroadQJCategory = "com.taihuoniao.fiu.qjcategory";
    public static final String BroadBuyGoodDetails = "com.taihuoniao.fiu.buygooddetail";
    public static final String BroadQJDetail = "com.taihuoniao.fiu.qjdetail";
    public static final String BroadSearch = "com.taihuoniao.fiu.SEARCH";
    public static final String BroadIndex = "com.taihuoniao.fiu.index";
    public static final String BroadFind = "com.taihuoniao.fiu.find";
    public static final String BroadWellGoods = "com.taihuoniao.fiu.wellgoods";
    //跳转活动详情
    public static final String BroadSceneActivityDetail = "com.taihuoniao.fiu.activitydetail";

//    public static final String ZONE_DETAIL_ACTIVITY_NAME = ZoneDetailActivity.class.getName();

    public static final int NET_FAIL = 404;//网络请求失败
    //EditPictureActivity跳转到AddProductActivity界面的请求码和返回码
    public static final int REQUESTCODE_EDIT_ADDPRODUCT = 99;
    public static final int RESULTCODE_EDIT_ADDPRODUCT = 98;
    //SelectStoreActivity跳转到SearchURLActivity界面，点击的是哪个按钮，进入哪个商城
    public static final int JINGDONG = 97;
    public static final int TAOBAO = 96;
    //添加标签界面所有标签
    public static final int LABEL_LIST = 87;
    //CreateSceneActivity跳转到BDSearchAddressActivity界面的请求码和返回码
    public static final int REQUESTCODE_CREATESCENE_BDSEARCH = 80;
    public static final int RESULTCODE_CREATESCENE_BDSEARCH = 79;
    //场景详情
    public static final int SCENE_DETAILS = 78;
    //评论列表
    public static final int COMMENTS_LIST = 77;
    //点赞，订阅，关注，收藏通用列表
    public static final int COMMON_LIST = 76;
    //情景列表
    public static final int QINGJING_LIST = 75;
    //提交评论
    public static final int SEND_COMMENT = 74;
    //创建情景
    public static final int CREATE_QINGJING = 73;
    //情景详情
    public static final int QINGJING_DETAILS = 72;
    //购物车商品数量
    public static final int CART_NUM = 71;
    //情景订阅
    public static final int SUBS_QINGJING = 68;
    //取消情景订阅
    public static final int CANCEL_SUBS_QINGJING = 67;
    //品牌列表
    public static final int BRAND_LIST = 66;
    //举报
    public static final int REPORT = 61;
    //产品详情
    public static final int GOODS_DETAIL = 60;
    //用户列表
    public static final int USER_LIST = 58;
    //商品分类的子分类列表
    public static final int CATEGORY_LABEL = 57;
    //品牌详情
    public static final int BRAND_DETAIL = 56;
    //用户添加商品
    public static final int ADD_PRODUCT = 55;
    //省市列表
    public static final int PROVINCE_LIST = 54;
    //提交编辑的地址
    public static final int COMMIT_NEW_ADDRESS = 53;
    //删除收货地址
    public static final int DELETE_ADDRESS = 52;
    //添加新地址的resultcode
    public static final int RESULTCODE_ADDNEWADDRESS = 51;
    //编辑地址requestcode
    public static final int REQUESTCODE_EDITADDRESS = 50;
    //选择收货地址resultcode
    public static final int RESULTCODE_ADDRESS = 49;
    //添加新地址的requestcode
    public static final int REQUESTCODE_ADDNEWADDRESS = 48;
    //获取收货地址
    public static final int GET_ADDRESS_LIST = 47;
    //搜索结果
    public static final int SEARCH_LIST = 46;
    //商品详情评论列表
    public static final int GOODS_DETAILS_COMMENTS = 42;
    //购物车数量
    public static final int PARSER_SHOP_CART_NUMBER = 41;
    public static final int PARSER_SHOP_CART = 40;
    public static final int PARSER_SHOP_CART_CALCULATE = 39;
    public static final int REQUESTCODE_ADDRESS = 37;
    public static final int RESULTCODE_TRANSFER_TIME = 36;
    public static final int REQUESTCODE_TRANSFER_TIME = 35;
    public static final int NOW_CONFIRM_ORDER = 34;
    public static final int ADD_TO_CART = 30;
    public static final int CANCEL_LOVE = 29;
    public static final int LOVE = 28;
    public static final int PARSER_ORDER_DETAILS = 27;
    public static final int PARSER_APPLY_REFUND = 26;
    public static final int PARSER_ORDER = 25;
    public static final int PASER_SHOPCART_INVENTORY_ITEM = 23;
    public static final int FIU_USER = 22;
    //删除用户添加的产品
    public static final int DELETE_PRODUCT = 21;
    public static final int RESULTCODE_MAP = 18;
    //创建场景的添加产品页面的搜索广播
    public static final String BroadSearchFragment = "com.taihuoniao.fiu.searchProduct";
    //删除评论
    public static final int DELETE_COMMENT = 17;
    //场景热门标签
    public static final int CJ_HOTLABEL = 15;
    //删除场景
    public static final int DELETE_SCENE = 14;
    //删除情景
    public static final int DELETE_QINGJING = 13;

    public static final String LOGIN_INFO = "LOGIN_INFO";
    public static final String SHAREDPREFRENCES_FIRST_IN = "fiu_first_in";//第一次进入app
    public static final String FIRST_IN_QING = "FirstInQing";//判断是不是第一次进入情界面
    public static final String FIRST_IN_JING = "FirstInJing";//判断是不是第一次进入景界面
    public static final String FIRST_IN_PIN = "FirstInPin";//第一次进入品
    //网络数据请求失败
    public static final int NETWORK_FAILURE = 999;


    //第三方登录绑定手机号
    public static final int PARSER_THIRD_LOGIN_BIND_PHONE = 37;
    //第三方登录回调时error
    public static final int PARSER_THIRD_LOGIN_ERROR = 38;
    //第三方登录回调时cancel
    public static final int PARSER_THIRD_LOGIN_CANCEL = 39;
    //第三方登录回调时complete
    public static final int PARSER_THIRD_LOGIN = 35;
    //第三方登录不绑定手机号
    public static final int PARSER_THIRD_LOGIN_SKIP_PHONE = 36;
    //找回密码
    public static final int PARSER_FIND_PASSWORD = 10;

    //首页　自定义下拉刷新中mHandler.sendEmptyMessageDelayed(__,__)的第一个参数
    public static final int CUSTOM_PULLTOREFRESH_HOME = 7;
    //验证红包是否可用
    public static final int PARSER_CHECK_REDBAG_USABLE = 42;

    //商品详情界面到可用红包界面的requestcode和resultcode
    public static final int REQUESTCODE_REDBAG = 57;
    public static final int RESULTCODE_REDBAG = 56;
    public static final String GUIDE_TAG = "guide";
    public static final String INVITE_CODE_TAG = "INVITE_CODE_TAG";
    public static final int GUIDE_INTERVAL = 1000;
    public static final String APP_ID = "";
    //帐户处我的红包未过期
    public static final int PARSER_MY_REDBAG_UNTIMEOUT = 19;
    //帐户处我的红包已过期
    public static final int PARSER_MY_REDBAG_TIMEOUT = 20;
    public static final int DIALOG_DELAY = 400;
}
