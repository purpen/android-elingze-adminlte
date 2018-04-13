package com.thn.erp.goods.brand.entity;

/**
 * Created by Stephen on 2018/4/9 17:26
 * Email: 895745843@qq.com
 */

public class BrandResultBean {

    /**
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    private StatusEntity status;
    private boolean success;

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }

    public static class StatusEntity {
        /**
         * code : 200
         * message : Ok all right.
         */

        private int code;
        private String message;

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
