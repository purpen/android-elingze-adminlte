package com.thn.erp.user.bean;

/**
 * Created by lilin on 2017/7/18.
 */

public class LogoutBean {

    /**
     * meta : {"message":"退出成功","status_code":200}
     */

    public MetaBean meta;

    public static class MetaBean {
        /**
         * message : 退出成功
         * status_code : 200
         */

        public String message;
        public int status_code;
    }
}
