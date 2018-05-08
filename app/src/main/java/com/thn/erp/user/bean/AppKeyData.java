package com.thn.erp.user.bean;

public class AppKeyData {

    /**
     * data : {"access_token":"7e209cac2ed0fe7329ad02b29fac4eea3aae77fe","app_key":"8rhJWHgT9NsKuOeRip3t"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean {
        /**
         * access_token : 7e209cac2ed0fe7329ad02b29fac4eea3aae77fe
         * app_key : 8rhJWHgT9NsKuOeRip3t
         */

        public String access_token;
        public String app_key;
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
