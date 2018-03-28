package com.thn.erp.sale.bean;

/**
 * Created by lilin on 2018/3/27.
 */

public class CommitConsigneeData {
    /**
     * data : {"area":"四环至五环之间","city":"北京市","first_name":"田","full_name":"田帅","is_default":true,"last_name":"帅","mobile":"18610230451","phone":"01084599328","province":"北京","rid":"5748231905","street_address":"751设计广场","street_address_two":null,"town":"朝阳区","zipcode":"100015"}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * area : 四环至五环之间
         * city : 北京市
         * first_name : 田
         * full_name : 田帅
         * is_default : true
         * last_name : 帅
         * mobile : 18610230451
         * phone : 01084599328
         * province : 北京
         * rid : 5748231905
         * street_address : 751设计广场
         * street_address_two : null
         * town : 朝阳区
         * zipcode : 100015
         */

        public String area;
        public String city;
        public String first_name;
        public String full_name;
        public boolean is_default;
        public String last_name;
        public String mobile;
        public String phone;
        public String province;
        public String rid;
        public String street_address;
        public String town;
        public String zipcode;
    }

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
