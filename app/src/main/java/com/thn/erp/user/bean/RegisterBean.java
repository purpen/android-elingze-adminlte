package com.thn.erp.user.bean;

/**
 * @author lilin
 * created at 2016/8/25 16:13
 */
public class RegisterBean {

    /**
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 201
         * message : All created.
         */

        public int code;
        public String message;
    }
}
