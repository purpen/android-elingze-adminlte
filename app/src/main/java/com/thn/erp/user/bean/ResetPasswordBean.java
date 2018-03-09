package com.thn.erp.user.bean;

/**
 * Created by lilin on 2017/7/19.
 */

public class ResetPasswordBean {

    /**
     * meta : {"message":"Success","status_code":200}
     */

    public MetaBean meta;

    public static class MetaBean {
        /**
         * message : Success
         * status_code : 200
         */

        public String message;
        public int status_code;
    }
}
