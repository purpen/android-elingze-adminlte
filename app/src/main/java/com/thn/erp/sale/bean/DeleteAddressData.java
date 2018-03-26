package com.thn.erp.sale.bean;

/**
 * Created by lilin on 2018/3/26.
 */

public class DeleteAddressData {

    /**
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;

    public static class StatusBean {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;
    }
}
