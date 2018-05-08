package com.thn.erp.user.bean;

/**
 * Created by lilin on 2018/3/12.
 */

public class LoginBean {


    /**
     * data : {"created_at":1523927428,"expiration":2592000,"store_rid":"Q4209136","token":"eyJhbGciOiJIUzI1NiIsIPuVGBJ7bUPTJklWBJZOdZXKzCVCfxQxHlhA"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * created_at : 1523927428
         * expiration : 2592000
         * store_rid : Q4209136
         * token : eyJhbGciOiJIUzI1NiIsIPuVGBJ7bUPTJklWBJZOdZXKzCVCfxQxHlhA
         */

        public int created_at;
        public int expiration;
        public String store_rid;
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
