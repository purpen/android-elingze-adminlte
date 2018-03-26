package com.thn.erp.sale.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lilin on 2018/3/26.
 */

public class DefaultAddressData implements Parcelable {

    /**
     * data : {"area":"四环至五环之间","city":"北京市","first_name":"田","full_name":"田帅","is_default":true,"last_name":"帅","mobile":"18610230451","phone":"01084599328","province":"北京","rid":"5758463019","street_address":"751设计广场","street_address_two":null,"town":"朝阳区","zipcode":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * area : 四环至五环之间
         * city : 北京市
         * first_name : 田
         * full_name : 田帅
         * is_default : true
         * last_name : 帅
         * mobile : 18610230451
         * phone : 01084599328
         * province : 北京
         * rid : 5758463019
         * street_address : 751设计广场
         * street_address_two : null
         * town : 朝阳区
         * zipcode : null
         */

        public String area;
        public String city;
        public String first_name;
        public String full_name;
        public boolean is_default;
        public String last_name;
        public String mobile;
        public String phone;
        public String province;
        public String rid;
        public String street_address;
        public String town;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.area);
            dest.writeString(this.city);
            dest.writeString(this.first_name);
            dest.writeString(this.full_name);
            dest.writeByte(this.is_default ? (byte) 1 : (byte) 0);
            dest.writeString(this.last_name);
            dest.writeString(this.mobile);
            dest.writeString(this.phone);
            dest.writeString(this.province);
            dest.writeString(this.rid);
            dest.writeString(this.street_address);
            dest.writeString(this.town);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.area = in.readString();
            this.city = in.readString();
            this.first_name = in.readString();
            this.full_name = in.readString();
            this.is_default = in.readByte() != 0;
            this.last_name = in.readString();
            this.mobile = in.readString();
            this.phone = in.readString();
            this.province = in.readString();
            this.rid = in.readString();
            this.street_address = in.readString();
            this.town = in.readString();
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

    public DefaultAddressData() {
    }

    protected DefaultAddressData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DefaultAddressData> CREATOR = new Parcelable.Creator<DefaultAddressData>() {
        @Override
        public DefaultAddressData createFromParcel(Parcel source) {
            return new DefaultAddressData(source);
        }

        @Override
        public DefaultAddressData[] newArray(int size) {
            return new DefaultAddressData[size];
        }
    };
}
