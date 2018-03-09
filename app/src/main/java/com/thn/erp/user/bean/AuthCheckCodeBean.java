package com.thn.erp.user.bean;

/**
 * Created by lilin on 2017/7/17.
 */

public class AuthCheckCodeBean {

    /**
     * meta : {"message":"请求成功！","status_code":200}
     * data : {"code":"727140"}
     */

    public MetaBean meta;
    public DataBean data;

    public static class MetaBean {
        /**
         * message : 请求成功！
         * status_code : 200
         */

        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * code : 727140
         */

        public String code;
    }
}
