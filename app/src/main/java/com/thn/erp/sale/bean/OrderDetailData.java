package com.thn.erp.sale.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilin on 2018/4/3.
 */

public class OrderDetailData implements Parcelable {

    /**
     * data : {"buyer_address":"751","buyer_city":"朝阳区","buyer_country":"中国","buyer_name":"lee","buyer_phone":"13366469976","buyer_province":"北京","buyer_remark":null,"buyer_tel":null,"buyer_zipcode":"","created_at":1522374735,"discount_amount":0,"express_at":0,"express_name":"","express_no":null,"freight":0,"items":[{"cost_price":"0.10","cover":"https://kg.erp.taihuoniao.com/20180226/FmK-QWL6gXMGKJ1ln0WE_KSxZALc.jpg","deal_price":0,"discount_amount":0,"id_code":"","mode":"蓝色","price":"22.20","product_name":"动感系列移动电源","quantity":1,"rid":"118260884497","s_color":"蓝色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":11}],"outside_target_id":"D18033043061579","pay_amount":0,"received_at":0,"remark":null,"rid":"D18033043061579","status":1,"store_name":"D3IN微商城(小程序)","total_amount":0,"total_quantity":1}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * buyer_address : 751
         * buyer_city : 朝阳区
         * buyer_country : 中国
         * buyer_name : lee
         * buyer_phone : 13366469976
         * buyer_province : 北京
         * buyer_remark : null
         * buyer_tel : null
         * buyer_zipcode :
         * created_at : 1522374735
         * discount_amount : 0.0
         * express_at : 0
         * express_name :
         * express_no : null
         * freight : 0.0
         * items : [{"cost_price":"0.10","cover":"https://kg.erp.taihuoniao.com/20180226/FmK-QWL6gXMGKJ1ln0WE_KSxZALc.jpg","deal_price":0,"discount_amount":0,"id_code":"","mode":"蓝色","price":"22.20","product_name":"动感系列移动电源","quantity":1,"rid":"118260884497","s_color":"蓝色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":11}]
         * outside_target_id : D18033043061579
         * pay_amount : 0.0
         * received_at : 0
         * remark : null
         * rid : D18033043061579
         * status : 1
         * store_name : D3IN微商城(小程序)
         * total_amount : 0.0
         * total_quantity : 1
         */

        public String buyer_address;
        public String buyer_city;
        public String buyer_country;
        public String buyer_name;
        public String buyer_phone;
        public String buyer_province;
        public String buyer_zipcode;
        public int created_at;
        public double discount_amount;
        public int express_at;
        public String express_name;
        public double freight;
        public String outside_target_id;
        public double pay_amount;
        public int received_at;
        public String rid;
        public int status;
        public String store_name;
        public double total_amount;
        public int total_quantity;
        public List<ItemsBean> items;

        public static class ItemsBean implements Parcelable {
            /**
             * cost_price : 0.10
             * cover : https://kg.erp.taihuoniao.com/20180226/FmK-QWL6gXMGKJ1ln0WE_KSxZALc.jpg
             * deal_price : 0.0
             * discount_amount : 0.0
             * id_code :
             * mode : 蓝色
             * price : 22.20
             * product_name : 动感系列移动电源
             * quantity : 1
             * rid : 118260884497
             * s_color : 蓝色
             * s_model :
             * s_weight : 0.00
             * sale_price : 0.00
             * stock_count : 11
             */

            public String cost_price;
            public String cover;
            public double deal_price;
            public double discount_amount;
            public String id_code;
            public String mode;
            public String price;
            public String product_name;
            public int quantity;
            public String rid;
            public String s_color;
            public String s_model;
            public String s_weight;
            public String sale_price;
            public int stock_count;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.cost_price);
                dest.writeString(this.cover);
                dest.writeDouble(this.deal_price);
                dest.writeDouble(this.discount_amount);
                dest.writeString(this.id_code);
                dest.writeString(this.mode);
                dest.writeString(this.price);
                dest.writeString(this.product_name);
                dest.writeInt(this.quantity);
                dest.writeString(this.rid);
                dest.writeString(this.s_color);
                dest.writeString(this.s_model);
                dest.writeString(this.s_weight);
                dest.writeString(this.sale_price);
                dest.writeInt(this.stock_count);
            }

            public ItemsBean() {
            }

            protected ItemsBean(Parcel in) {
                this.cost_price = in.readString();
                this.cover = in.readString();
                this.deal_price = in.readDouble();
                this.discount_amount = in.readDouble();
                this.id_code = in.readString();
                this.mode = in.readString();
                this.price = in.readString();
                this.product_name = in.readString();
                this.quantity = in.readInt();
                this.rid = in.readString();
                this.s_color = in.readString();
                this.s_model = in.readString();
                this.s_weight = in.readString();
                this.sale_price = in.readString();
                this.stock_count = in.readInt();
            }

            public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
                @Override
                public ItemsBean createFromParcel(Parcel source) {
                    return new ItemsBean(source);
                }

                @Override
                public ItemsBean[] newArray(int size) {
                    return new ItemsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.buyer_address);
            dest.writeString(this.buyer_city);
            dest.writeString(this.buyer_country);
            dest.writeString(this.buyer_name);
            dest.writeString(this.buyer_phone);
            dest.writeString(this.buyer_province);
            dest.writeString(this.buyer_zipcode);
            dest.writeInt(this.created_at);
            dest.writeDouble(this.discount_amount);
            dest.writeInt(this.express_at);
            dest.writeString(this.express_name);
            dest.writeDouble(this.freight);
            dest.writeString(this.outside_target_id);
            dest.writeDouble(this.pay_amount);
            dest.writeInt(this.received_at);
            dest.writeString(this.rid);
            dest.writeInt(this.status);
            dest.writeString(this.store_name);
            dest.writeDouble(this.total_amount);
            dest.writeInt(this.total_quantity);
            dest.writeList(this.items);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.buyer_address = in.readString();
            this.buyer_city = in.readString();
            this.buyer_country = in.readString();
            this.buyer_name = in.readString();
            this.buyer_phone = in.readString();
            this.buyer_province = in.readString();
            this.buyer_zipcode = in.readString();
            this.created_at = in.readInt();
            this.discount_amount = in.readDouble();
            this.express_at = in.readInt();
            this.express_name = in.readString();
            this.freight = in.readDouble();
            this.outside_target_id = in.readString();
            this.pay_amount = in.readDouble();
            this.received_at = in.readInt();
            this.rid = in.readString();
            this.status = in.readInt();
            this.store_name = in.readString();
            this.total_amount = in.readDouble();
            this.total_quantity = in.readInt();
            this.items = new ArrayList<ItemsBean>();
            in.readList(this.items, ItemsBean.class.getClassLoader());
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

    public OrderDetailData() {
    }

    protected OrderDetailData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<OrderDetailData> CREATOR = new Creator<OrderDetailData>() {
        @Override
        public OrderDetailData createFromParcel(Parcel source) {
            return new OrderDetailData(source);
        }

        @Override
        public OrderDetailData[] newArray(int size) {
            return new OrderDetailData[size];
        }
    };
}
