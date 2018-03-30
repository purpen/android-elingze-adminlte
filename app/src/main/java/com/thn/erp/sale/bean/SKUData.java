package com.thn.erp.sale.bean;

import java.util.List;

/**
 * Created by lilin on 2018/3/29.
 */

public class SKUData {

    /**
     * data : [{"cost_price":"24.00","cover":"/static/img/mic_logo180x180.jpg","mode":"iPhoneX 黑色","id_code":"","product_name":"iPhoneX 手机壳","rid":"117280530556","s_color":"黑色","s_model":"iPhoneX","s_weight":"0.00","sale_price":"69.00","stock_count":10},{"cost_price":"24.00","cover":"/static/img/mic_logo180x180.jpg","mode":"iPhoneX 白色","id_code":"","product_name":"iPhoneX 手机壳","rid":"117280969019","s_color":"白色","s_model":"iPhoneX","s_weight":"0.00","sale_price":"69.00","stock_count":10}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<DataBean> data;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }

    public static class DataBean {
        /**
         * cost_price : 24.00
         * cover : /static/img/mic_logo180x180.jpg
         * mode : iPhoneX 黑色
         * id_code :
         * product_name : iPhoneX 手机壳
         * rid : 117280530556
         * s_color : 黑色
         * s_model : iPhoneX
         * s_weight : 0.00
         * sale_price : 69.00
         * stock_count : 10
         */

        public String cost_price;
        public String cover;
        public String mode;
        public String id_code;
        public String product_name;
        public String rid;
        public String s_color;
        public String s_model;
        public String s_weight;
        public String sale_price;
        public int stock_count;
    }
}
