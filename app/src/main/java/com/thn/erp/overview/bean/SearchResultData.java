package com.thn.erp.overview.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SearchResultData implements Parcelable {


    /**
     * data : {"count":12,"next":true,"prev":false,"products":[{"cost_price":"0.00","cover":"https://kg.erp.taihuoniao.com/20180330/FiXQzu6AsjGeNusi4LtFKHsIfNL9.png","description":"","features":"","id_code":null,"name":"啤酒","price":0.79,"rid":"8280165479","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":17},{"cost_price":"49.00","cover":"https://kg.erp.taihuoniao.com/20180226/FvGzE_qFrmqnbN3vdeZiRNrRGPGs.jpg","description":"","features":"","id_code":null,"name":"旅行者蓝牙耳机","price":200,"rid":"118260486508","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":false,"stock_count":200},{"cost_price":"0.10","cover":"https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg","description":"","features":"","id_code":null,"name":"动感系列移动电源","price":0.2,"rid":"118260240191","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":4},{"cost_price":"0.00","cover":"https://kg.erp.taihuoniao.com/20180330/Fl5VS0QpdyllwhEnLcxih_1u1V3y.png","description":"","features":"","id_code":null,"name":"巧克力","price":25.89,"rid":"8261478539","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":54},{"cost_price":"20.00","cover":"https://kg.erp.taihuoniao.com/20180402/FrdCY42Eq-cYROi29D1CkhRNZKfh.png","description":"","features":"","id_code":null,"name":"万能椅","price":999.99,"rid":"8845617302","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":true,"stock_count":90},{"cost_price":"45.00","cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","description":"","features":null,"id_code":null,"name":"传奇系列","price":98,"rid":"118260327535","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":88,"sticked":true,"stock_count":301},{"cost_price":"28.00","cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","description":"","features":null,"id_code":null,"name":"Recci充电线","price":0,"rid":"118260368435","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":49,"sticked":true,"stock_count":null},{"cost_price":"46.00","cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","description":"","features":null,"id_code":null,"name":"悦音系列","price":0,"rid":"118260197871","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":true,"stock_count":null},{"cost_price":"18.00","cover":"https://kg.erp.taihuoniao.com/20180226/FuicNrdezKRqy1hPKk1TchVbFs17.jpg","description":"","features":null,"id_code":null,"name":"萌点充电线","price":0,"rid":"118260569086","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":29,"sticked":null,"stock_count":null},{"cost_price":"28.00","cover":"https://kg.erp.taihuoniao.com/20180226/FmcWpM9TRa9QMmsQPinoOGFTXGG0.jpg","description":"","features":null,"id_code":null,"name":"彩虹移动电源","price":0,"rid":"118260400475","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":69,"sticked":null,"stock_count":null}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 12
         * next : true
         * prev : false
         * products : [{"cost_price":"0.00","cover":"https://kg.erp.taihuoniao.com/20180330/FiXQzu6AsjGeNusi4LtFKHsIfNL9.png","description":"","features":"","id_code":null,"name":"啤酒","price":0.79,"rid":"8280165479","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":17},{"cost_price":"49.00","cover":"https://kg.erp.taihuoniao.com/20180226/FvGzE_qFrmqnbN3vdeZiRNrRGPGs.jpg","description":"","features":"","id_code":null,"name":"旅行者蓝牙耳机","price":200,"rid":"118260486508","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":false,"stock_count":200},{"cost_price":"0.10","cover":"https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg","description":"","features":"","id_code":null,"name":"动感系列移动电源","price":0.2,"rid":"118260240191","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":4},{"cost_price":"0.00","cover":"https://kg.erp.taihuoniao.com/20180330/Fl5VS0QpdyllwhEnLcxih_1u1V3y.png","description":"","features":"","id_code":null,"name":"巧克力","price":25.89,"rid":"8261478539","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":false,"stock_count":54},{"cost_price":"20.00","cover":"https://kg.erp.taihuoniao.com/20180402/FrdCY42Eq-cYROi29D1CkhRNZKfh.png","description":"","features":"","id_code":null,"name":"万能椅","price":999.99,"rid":"8845617302","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0,"sticked":true,"stock_count":90},{"cost_price":"45.00","cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","description":"","features":null,"id_code":null,"name":"传奇系列","price":98,"rid":"118260327535","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":88,"sticked":true,"stock_count":301},{"cost_price":"28.00","cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","description":"","features":null,"id_code":null,"name":"Recci充电线","price":0,"rid":"118260368435","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":49,"sticked":true,"stock_count":null},{"cost_price":"46.00","cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","description":"","features":null,"id_code":null,"name":"悦音系列","price":0,"rid":"118260197871","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":true,"stock_count":null},{"cost_price":"18.00","cover":"https://kg.erp.taihuoniao.com/20180226/FuicNrdezKRqy1hPKk1TchVbFs17.jpg","description":"","features":null,"id_code":null,"name":"萌点充电线","price":0,"rid":"118260569086","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":29,"sticked":null,"stock_count":null},{"cost_price":"28.00","cover":"https://kg.erp.taihuoniao.com/20180226/FmcWpM9TRa9QMmsQPinoOGFTXGG0.jpg","description":"","features":null,"id_code":null,"name":"彩虹移动电源","price":0,"rid":"118260400475","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":69,"sticked":null,"stock_count":null}]
         */

        public int count;
        public boolean next;
        public boolean prev;
        public List<ProductsBean> products;

        public static class ProductsBean {
            /**
             * cost_price : 0.00
             * cover : https://kg.erp.taihuoniao.com/20180330/FiXQzu6AsjGeNusi4LtFKHsIfNL9.png
             * description :
             * features :
             * id_code : null
             * name : 啤酒
             * price : 0.79
             * rid : 8280165479
             * s_height : 0.0
             * s_length : 0.0
             * s_weight : 0.0
             * s_width : 0.0
             * sale_price : 0.0
             * sticked : false
             * stock_count : 17
             */

            public String cost_price;
            public String cover;
            public String description;
            public String features;
            public Object id_code;
            public String name;
            public double price;
            public String rid;
            public double s_height;
            public double s_length;
            public double s_weight;
            public double s_width;
            public double sale_price;
            public boolean sticked;
            public int stock_count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeByte(this.next ? (byte) 1 : (byte) 0);
            dest.writeByte(this.prev ? (byte) 1 : (byte) 0);
            dest.writeList(this.products);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.next = in.readByte() != 0;
            this.prev = in.readByte() != 0;
            this.products = new ArrayList<ProductsBean>();
            in.readList(this.products, ProductsBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public static class StatusBean implements Parcelable {
        /**
         * code : 200
         * message : Ok all right.
         */

        public int code;
        public String message;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.message);
        }

        public StatusBean() {
        }

        protected StatusBean(Parcel in) {
            this.code = in.readInt();
            this.message = in.readString();
        }

        public static final Creator<StatusBean> CREATOR = new Creator<StatusBean>() {
            @Override
            public StatusBean createFromParcel(Parcel source) {
                return new StatusBean(source);
            }

            @Override
            public StatusBean[] newArray(int size) {
                return new StatusBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public SearchResultData() {
    }

    protected SearchResultData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<SearchResultData> CREATOR = new Creator<SearchResultData>() {
        @Override
        public SearchResultData createFromParcel(Parcel source) {
            return new SearchResultData(source);
        }

        @Override
        public SearchResultData[] newArray(int size) {
            return new SearchResultData[size];
        }
    };
}
