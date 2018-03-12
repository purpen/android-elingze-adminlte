package com.thn.erp.user.bean;

/**
 * Created by lilin on 2018/3/12.
 */

public class LoginBean {

    /**
     * data : {"expiration":7200,"token":"eyJhbGciOiJIUzI1NJpZtZS5HkISmEqq113c7nyp_hdIo-mU"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * expiration : 7200
         * token : eyJhbGciOiJIUzI1NJpZtZS5HkISmEqq113c7nyp_hdIo-mU
         */

        public int expiration;
        public String token;
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
