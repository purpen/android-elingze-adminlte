package com.thn.erp.sale.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilin on 2018/3/23.
 */

public class GoodsData implements Parcelable {

    /**
     * data : {"count":9,"next":null,"prev":null,"products":[{"cover":"https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg","description":"","features":null,"id_code":null,"name":"动感系列移动电源","price":0.2,"rid":"118260240191","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0.15,"sticked":false},{"cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","description":"","features":null,"id_code":null,"name":"传奇系列","price":98,"rid":"118260327535","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":88,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","description":"","features":null,"id_code":null,"name":"Recci充电线","price":0,"rid":"118260368435","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":49,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","description":"","features":null,"id_code":null,"name":"悦音系列","price":0,"rid":"118260197871","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvGzE_qFrmqnbN3vdeZiRNrRGPGs.jpg","description":"","features":null,"id_code":null,"name":"旅行者蓝牙耳机","price":0,"rid":"118260486508","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FuicNrdezKRqy1hPKk1TchVbFs17.jpg","description":"","features":null,"id_code":null,"name":"萌点充电线","price":0,"rid":"118260569086","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":29,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FmcWpM9TRa9QMmsQPinoOGFTXGG0.jpg","description":"","features":null,"id_code":null,"name":"彩虹移动电源","price":0,"rid":"118260400475","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":69,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FrVN2Qna1NBm7yZ5JVLsnuBC0Gw-.jpg","description":"","features":null,"id_code":null,"name":"轻便糖果耳机","price":0,"rid":"118260316495","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvnWyzcptMqCuC9ITH7eDoQxVUAG.jpg","description":"","features":null,"id_code":null,"name":"Recci蓝牙耳机","price":0,"rid":"118260434950","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":null}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 9
         * next : null
         * prev : null
         * products : [{"cover":"https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg","description":"","features":null,"id_code":null,"name":"动感系列移动电源","price":0.2,"rid":"118260240191","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":0.15,"sticked":false},{"cover":"https://kg.erp.taihuoniao.com/20180226/Fnoungkq94mFVvLYGbN2sod6LjIv.jpg","description":"","features":null,"id_code":null,"name":"传奇系列","price":98,"rid":"118260327535","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":88,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/Fj60jL77HmZQ5Y8mQqXhevARGzZu.jpg","description":"","features":null,"id_code":null,"name":"Recci充电线","price":0,"rid":"118260368435","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":49,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvTozbhDCG_VlEcbKcwTZHMGNTbr.jpg","description":"","features":null,"id_code":null,"name":"悦音系列","price":0,"rid":"118260197871","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":true},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvGzE_qFrmqnbN3vdeZiRNrRGPGs.jpg","description":"","features":null,"id_code":null,"name":"旅行者蓝牙耳机","price":0,"rid":"118260486508","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FuicNrdezKRqy1hPKk1TchVbFs17.jpg","description":"","features":null,"id_code":null,"name":"萌点充电线","price":0,"rid":"118260569086","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":29,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FmcWpM9TRa9QMmsQPinoOGFTXGG0.jpg","description":"","features":null,"id_code":null,"name":"彩虹移动电源","price":0,"rid":"118260400475","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":69,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FrVN2Qna1NBm7yZ5JVLsnuBC0Gw-.jpg","description":"","features":null,"id_code":null,"name":"轻便糖果耳机","price":0,"rid":"118260316495","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":89,"sticked":null},{"cover":"https://kg.erp.taihuoniao.com/20180226/FvnWyzcptMqCuC9ITH7eDoQxVUAG.jpg","description":"","features":null,"id_code":null,"name":"Recci蓝牙耳机","price":0,"rid":"118260434950","s_height":0,"s_length":0,"s_weight":0,"s_width":0,"sale_price":149,"sticked":null}]
         */

        public int count;
        public List<ProductsBean> products;

        public static class ProductsBean implements Parcelable {
            /**
             * cover : https://kg.erp.taihuoniao.com/20180226/Fi-R-nar6b1TdBOKZAnhl-FvT_qc.jpg
             * description :
             * features : null
             * id_code : null
             * name : 动感系列移动电源
             * price : 0.2
             * rid : 118260240191
             * s_height : 0.0
             * s_length : 0.0
             * s_weight : 0.0
             * s_width : 0.0
             * sale_price : 0.15
             * sticked : false
             */

            public String cover;
            public String description;
            public String name;
            public double price;
            public String rid;
            public double s_height;
            public double s_length;
            public double s_weight;
            public double s_width;
            public double sale_price;
            public boolean sticked;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.cover);
                dest.writeString(this.description);
                dest.writeString(this.name);
                dest.writeDouble(this.price);
                dest.writeString(this.rid);
                dest.writeDouble(this.s_height);
                dest.writeDouble(this.s_length);
                dest.writeDouble(this.s_weight);
                dest.writeDouble(this.s_width);
                dest.writeDouble(this.sale_price);
                dest.writeByte(this.sticked ? (byte) 1 : (byte) 0);
            }

            public ProductsBean() {
            }

            protected ProductsBean(Parcel in) {
                this.cover = in.readString();
                this.description = in.readString();
                this.name = in.readString();
                this.price = in.readDouble();
                this.rid = in.readString();
                this.s_height = in.readDouble();
                this.s_length = in.readDouble();
                this.s_weight = in.readDouble();
                this.s_width = in.readDouble();
                this.sale_price = in.readDouble();
                this.sticked = in.readByte() != 0;
            }

            public static final Creator<ProductsBean> CREATOR = new Creator<ProductsBean>() {
                @Override
                public ProductsBean createFromParcel(Parcel source) {
                    return new ProductsBean(source);
                }

                @Override
                public ProductsBean[] newArray(int size) {
                    return new ProductsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.count);
            dest.writeList(this.products);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
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

    public GoodsData() {
    }

    protected GoodsData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoodsData> CREATOR = new Creator<GoodsData>() {
        @Override
        public GoodsData createFromParcel(Parcel source) {
            return new GoodsData(source);
        }

        @Override
        public GoodsData[] newArray(int size) {
            return new GoodsData[size];
        }
    };
}
