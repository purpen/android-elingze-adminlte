package com.thn.erp.overview.usermanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lilin on 2018/3/23.
 */

public class AddCustomerData implements Parcelable {

    /**
     * data : {"address":"4hao","grade":"一级代理","mobile":"13829839078","name":"销2","rid":"F7813469","status":1}
     * status : {"code":201,"message":"All created."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * address : 4hao
         * grade : 一级代理
         * mobile : 13829839078
         * name : 销2
         * rid : F7813469
         * status : 1
         */

        public String address;
        public String grade;
        public String mobile;
        public String name;
        public String rid;
        public int status;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeString(this.grade);
            dest.writeString(this.mobile);
            dest.writeString(this.name);
            dest.writeString(this.rid);
            dest.writeInt(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.address = in.readString();
            this.grade = in.readString();
            this.mobile = in.readString();
            this.name = in.readString();
            this.rid = in.readString();
            this.status = in.readInt();
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

    public AddCustomerData() {
    }

    protected AddCustomerData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<AddCustomerData> CREATOR = new Creator<AddCustomerData>() {
        @Override
        public AddCustomerData createFromParcel(Parcel source) {
            return new AddCustomerData(source);
        }

        @Override
        public AddCustomerData[] newArray(int size) {
            return new AddCustomerData[size];
        }
    };
}
