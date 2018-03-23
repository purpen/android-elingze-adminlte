package com.thn.erp.overview.usermanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilin on 2018/3/23.
 */

public class CustomerData implements Parcelable {

    /**
     * data : {"count":1,"customers":[{"address":"设计","grade":"一级代理","mobile":"18723435435","name":"宁波大中店","rid":"F2538047","status":1}],"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 1
         * customers : [{"address":"设计","grade":"一级代理","mobile":"18723435435","name":"宁波大中店","rid":"F2538047","status":1}]
         * next : null
         * prev : null
         */

        public int count;
        public List<CustomersBean> customers;

        public static class CustomersBean implements Parcelable {
            /**
             * address : 设计
             * grade : 一级代理
             * mobile : 18723435435
             * name : 宁波大中店
             * rid : F2538047
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

            public CustomersBean() {
            }

            protected CustomersBean(Parcel in) {
                this.address = in.readString();
                this.grade = in.readString();
                this.mobile = in.readString();
                this.name = in.readString();
                this.rid = in.readString();
                this.status = in.readInt();
            }

            public static final Creator<CustomersBean> CREATOR = new Creator<CustomersBean>() {
                @Override
                public CustomersBean createFromParcel(Parcel source) {
                    return new CustomersBean(source);
                }

                @Override
                public CustomersBean[] newArray(int size) {
                    return new CustomersBean[size];
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
            dest.writeList(this.customers);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.customers = new ArrayList<CustomersBean>();
            in.readList(this.customers, CustomersBean.class.getClassLoader());
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

    public CustomerData() {
    }

    protected CustomerData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<CustomerData> CREATOR = new Creator<CustomerData>() {
        @Override
        public CustomerData createFromParcel(Parcel source) {
            return new CustomerData(source);
        }

        @Override
        public CustomerData[] newArray(int size) {
            return new CustomerData[size];
        }
    };
}
