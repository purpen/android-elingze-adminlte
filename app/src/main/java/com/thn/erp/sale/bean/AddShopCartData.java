package com.thn.erp.sale.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AddShopCartData implements Parcelable {

    /**
     * data : {"cart":{"option":null,"product":{"cost_price":"24.00","cover":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","id_code":"","mode":"棕色","price":"68.00","product_name":"数据大仙","rid":"118020098269","s_color":"棕色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":0},"quantity":2,"rid":"118020098269","user_id":2},"item_count":3}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * cart : {"option":null,"product":{"cost_price":"24.00","cover":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","id_code":"","mode":"棕色","price":"68.00","product_name":"数据大仙","rid":"118020098269","s_color":"棕色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":0},"quantity":2,"rid":"118020098269","user_id":2}
         * item_count : 3
         */

        public CartBean cart;
        public int item_count;

        public static class CartBean implements Parcelable {
            /**
             * option : null
             * product : {"cost_price":"24.00","cover":"http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg","id_code":"","mode":"棕色","price":"68.00","product_name":"数据大仙","rid":"118020098269","s_color":"棕色","s_model":"","s_weight":"0.00","sale_price":"0.00","stock_count":0}
             * quantity : 2
             * rid : 118020098269
             * user_id : 2
             */

            public ProductBean product;
            public int quantity;
            public String rid;
            public int user_id;

            public static class ProductBean implements Parcelable {
                /**
                 * cost_price : 24.00
                 * cover : http://xxx/_uploads/photos/180202/f51932c571ba21f.jpg
                 * id_code :
                 * mode : 棕色
                 * price : 68.00
                 * product_name : 数据大仙
                 * rid : 118020098269
                 * s_color : 棕色
                 * s_model :
                 * s_weight : 0.00
                 * sale_price : 0.00
                 * stock_count : 0
                 */

                public String cost_price;
                public String cover;
                public String id_code;
                public String mode;
                public String price;
                public String product_name;
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
                    dest.writeString(this.id_code);
                    dest.writeString(this.mode);
                    dest.writeString(this.price);
                    dest.writeString(this.product_name);
                    dest.writeString(this.rid);
                    dest.writeString(this.s_color);
                    dest.writeString(this.s_model);
                    dest.writeString(this.s_weight);
                    dest.writeString(this.sale_price);
                    dest.writeInt(this.stock_count);
                }

                public ProductBean() {
                }

                protected ProductBean(Parcel in) {
                    this.cost_price = in.readString();
                    this.cover = in.readString();
                    this.id_code = in.readString();
                    this.mode = in.readString();
                    this.price = in.readString();
                    this.product_name = in.readString();
                    this.rid = in.readString();
                    this.s_color = in.readString();
                    this.s_model = in.readString();
                    this.s_weight = in.readString();
                    this.sale_price = in.readString();
                    this.stock_count = in.readInt();
                }

                public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
                    @Override
                    public ProductBean createFromParcel(Parcel source) {
                        return new ProductBean(source);
                    }

                    @Override
                    public ProductBean[] newArray(int size) {
                        return new ProductBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeParcelable(this.product, flags);
                dest.writeInt(this.quantity);
                dest.writeString(this.rid);
                dest.writeInt(this.user_id);
            }

            public CartBean() {
            }

            protected CartBean(Parcel in) {
                this.product = in.readParcelable(ProductBean.class.getClassLoader());
                this.quantity = in.readInt();
                this.rid = in.readString();
                this.user_id = in.readInt();
            }

            public static final Creator<CartBean> CREATOR = new Creator<CartBean>() {
                @Override
                public CartBean createFromParcel(Parcel source) {
                    return new CartBean(source);
                }

                @Override
                public CartBean[] newArray(int size) {
                    return new CartBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.cart, flags);
            dest.writeInt(this.item_count);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.cart = in.readParcelable(CartBean.class.getClassLoader());
            this.item_count = in.readInt();
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
         * code : 201
         * message : All created.
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

    public AddShopCartData() {
    }

    protected AddShopCartData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<AddShopCartData> CREATOR = new Creator<AddShopCartData>() {
        @Override
        public AddShopCartData createFromParcel(Parcel source) {
            return new AddShopCartData(source);
        }

        @Override
        public AddShopCartData[] newArray(int size) {
            return new AddShopCartData[size];
        }
    };
}
