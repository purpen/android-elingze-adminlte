package com.thn.erp.goods.add;

/**
 * Created by Stephen on 2018/3/28 18:51
 * Email: 895745843@qq.com
 */

public class QiniuTokenData {

    /**
     * data : {"up_endpoint":"https://up.qbox.me","up_token":"AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:OgsNLotOvWrTLDv7_QOSREvN2Zo=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTIxNzcxNzI1LCJjYWxsYmFja1VybCI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMC9vcGVuL3Fpbml1L25vdGlmeSIsImNhbGxiYWNrQm9keSI6ImZpbGVwYXRoPSQoa2V5KSZmaWxlbmFtZT0kKGZuYW1lKSZmaWxlc2l6ZT0kKGZzaXplKSZtaW1lPSQobWltZVR5cGUpJnVzZXJfaWQ9JCh4OnVzZXJfaWQpJndpZHRoPSQoaW1hZ2VJbmZvLndpZHRoKSZoZWlnaHQ9JChpbWFnZUluZm8uaGVpZ2h0KSZleHQ9JChleHQpJmRpcmVjdG9yeT0kKHg6ZGlyZWN0b3J5KSIsInNhdmVLZXkiOiIkKHllYXIpJChtb24pJChkYXkpLyQoZXRhZykkKGV4dCkiLCJmc2l6ZUxpbWl0IjoyMDk3MTUyMCwicmV0dXJuVXJsIjoiIiwicmV0dXJuQm9keSI6IiJ9"}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    private DataEntity data;
    private StatusEntity status;
    private boolean success;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataEntity getData() {
        return data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }

    public static class DataEntity {
        /**
         * up_endpoint : https://up.qbox.me
         * up_token : AWTEpwVNmNcVjsIL-vS1hOabJ0NgIfNDzvTbDb4i:OgsNLotOvWrTLDv7_QOSREvN2Zo=:eyJzY29wZSI6ImZya2luZyIsImRlYWRsaW5lIjoxNTIxNzcxNzI1LCJjYWxsYmFja1VybCI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMC9vcGVuL3Fpbml1L25vdGlmeSIsImNhbGxiYWNrQm9keSI6ImZpbGVwYXRoPSQoa2V5KSZmaWxlbmFtZT0kKGZuYW1lKSZmaWxlc2l6ZT0kKGZzaXplKSZtaW1lPSQobWltZVR5cGUpJnVzZXJfaWQ9JCh4OnVzZXJfaWQpJndpZHRoPSQoaW1hZ2VJbmZvLndpZHRoKSZoZWlnaHQ9JChpbWFnZUluZm8uaGVpZ2h0KSZleHQ9JChleHQpJmRpcmVjdG9yeT0kKHg6ZGlyZWN0b3J5KSIsInNhdmVLZXkiOiIkKHllYXIpJChtb24pJChkYXkpLyQoZXRhZykkKGV4dCkiLCJmc2l6ZUxpbWl0IjoyMDk3MTUyMCwicmV0dXJuVXJsIjoiIiwicmV0dXJuQm9keSI6IiJ9
         */

        private String up_endpoint;
        private String up_token;
        private String user_id;

        public String getDirectory_id() {
            return directory_id;
        }

        public void setDirectory_id(String directory_id) {
            this.directory_id = directory_id;
        }

        private String directory_id;

        public void setUp_endpoint(String up_endpoint) {
            this.up_endpoint = up_endpoint;
        }

        public void setUp_token(String up_token) {
            this.up_token = up_token;
        }

        public String getUp_endpoint() {
            return up_endpoint;
        }

        public String getUp_token() {
            return up_token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
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
