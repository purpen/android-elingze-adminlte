package com.thn.erp.goods.brand;

/**
 * Created by Stephen on 2018/4/8 19:52
 * Email: 895745843@qq.com
 */

public class BrandBean {

    /**
     * data : {"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"潮流设计","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FuFOec2y1pOxqfEL-mZ9H_wLZUxM.jpeg","name":"WK潮牌","rid":"656942713","sort_order":1,"status":1}
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
         * banner : https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png
         * description :
         * features : 潮流设计
         * is_recommended : false
         * logo : https://kg.erp.taihuoniao.com/20180226/FuFOec2y1pOxqfEL-mZ9H_wLZUxM.jpeg
         * name : WK潮牌
         * rid : 656942713
         * sort_order : 1
         * status : 1
         */

        private String banner;
        private String description;
        private String features;
        private boolean is_recommended;
        private String logo;
        private String name;
        private String rid;
        private int sort_order;
        private int status;

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public void setIs_recommended(boolean is_recommended) {
            this.is_recommended = is_recommended;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBanner() {
            return banner;
        }

        public String getDescription() {
            return description;
        }

        public String getFeatures() {
            return features;
        }

        public boolean getIs_recommended() {
            return is_recommended;
        }

        public String getLogo() {
            return logo;
        }

        public String getName() {
            return name;
        }

        public String getRid() {
            return rid;
        }

        public int getSort_order() {
            return sort_order;
        }

        public int getStatus() {
            return status;
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
