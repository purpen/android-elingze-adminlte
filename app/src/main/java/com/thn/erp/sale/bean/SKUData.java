package com.thn.erp.sale.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilin on 2018/3/29.
 */

public class SKUData implements Parcelable {


    /**
     * data : [{"cost_price":"24.00","cover":"http://127.0.0.1:5000/_uploads/photos/171228/67a86ca36b30ec7.jpg","id_code":"","mode":"iPhone8 红色","product_name":"iPhoneX 手机壳","rid":"118040911719","s_color":"红色","s_model":"iPhone8","s_weight":"0.00","sale_price":"69.00","stock_count":0}]
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;
    public List<DataBean> data;

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

    public static class DataBean implements Parcelable {
        /**
         * cost_price : 24.00
         * cover : http://127.0.0.1:5000/_uploads/photos/171228/67a86ca36b30ec7.jpg
         * id_code :
         * mode : iPhone8 红色
         * product_name : iPhoneX 手机壳
         * rid : 118040911719
         * s_color : 红色
         * s_model : iPhone8
         * s_weight : 0.00
         * sale_price : 69.00
         * stock_count : 0
         */

        public String cost_price;
        public String cover;
        public String id_code;
        public String mode;
        public String product_name;
        public String rid;
        public String s_color;
        public String s_model;
        public String s_weight;
        public String sale_price;
        public int stock_count;

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cost_price);
            dest.writeString(this.cover);
            dest.writeString(this.id_code);
            dest.writeString(this.mode);
            dest.writeString(this.product_name);
            dest.writeString(this.rid);
            dest.writeString(this.s_color);
            dest.writeString(this.s_model);
            dest.writeString(this.s_weight);
            dest.writeString(this.sale_price);
            dest.writeInt(this.stock_count);
        }

        protected DataBean(Parcel in) {
            this.cost_price = in.readString();
            this.cover = in.readString();
            this.id_code = in.readString();
            this.mode = in.readString();
            this.product_name = in.readString();
            this.rid = in.readString();
            this.s_color = in.readString();
            this.s_model = in.readString();
            this.s_weight = in.readString();
            this.sale_price = in.readString();
            this.stock_count = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeList(this.data);
    }

    public SKUData() {
    }

    protected SKUData(Parcel in) {
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SKUData> CREATOR = new Parcelable.Creator<SKUData>() {
        @Override
        public SKUData createFromParcel(Parcel source) {
            return new SKUData(source);
        }

        @Override
        public SKUData[] newArray(int size) {
            return new SKUData[size];
        }
    };
}
