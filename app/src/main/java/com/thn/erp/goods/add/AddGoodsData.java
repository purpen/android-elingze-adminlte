package com.thn.erp.goods.add;

/**
 * Created by Stephen on 2018/3/29 20:14
 * Email: 895745843@qq.com
 */

public class AddGoodsData {

    /**
     * data : {"cover":"http://xxxx/photos/180224/c833237a728a1ed.jpg","description":"新潮产品描述","features":"最具创新力","id_code":null,"name":"新产品05","price":45,"rid":"8478210953","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":43,"sticked":false}
     * status : {"code":201,"message":"All created."}
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
         * cover : http://xxxx/photos/180224/c833237a728a1ed.jpg
         * description : 新潮产品描述
         * features : 最具创新力
         * id_code : null
         * name : 新产品05
         * price : 45
         * rid : 8478210953
         * s_height : 0
         * s_length : 0
         * s_weight : 0
         * s_width : 0
         * sale_price : 43
         * sticked : false
         */

        private String cover;
        private String description;
        private String features;
        private String id_code;
        private String name;
        private String price;
        private String rid;
        private String s_height;
        private String s_length;
        private String s_weight;
        private String s_width;
        private String sale_price;
        private boolean sticked;

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public void setId_code(String id_code) {
            this.id_code = id_code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public void setS_height(String s_height) {
            this.s_height = s_height;
        }

        public void setS_length(String s_length) {
            this.s_length = s_length;
        }

        public void setS_weight(String s_weight) {
            this.s_weight = s_weight;
        }

        public void setS_width(String s_width) {
            this.s_width = s_width;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public void setSticked(boolean sticked) {
            this.sticked = sticked;
        }

        public String getCover() {
            return cover;
        }

        public String getDescription() {
            return description;
        }

        public String getFeatures() {
            return features;
        }

        public Object getId_code() {
            return id_code;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getRid() {
            return rid;
        }

        public String getS_height() {
            return s_height;
        }

        public String getS_length() {
            return s_length;
        }

        public String getS_weight() {
            return s_weight;
        }

        public String getS_width() {
            return s_width;
        }

        public String getSale_price() {
            return sale_price;
        }

        public boolean getSticked() {
            return sticked;
        }
    }

    public static class StatusEntity {
        /**
         * code : 201
         * message : All created.
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
