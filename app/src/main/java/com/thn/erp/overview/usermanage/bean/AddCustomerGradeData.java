package com.thn.erp.overview.usermanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AddCustomerGradeData implements Parcelable {

    /**
     * data : {"count":2,"grades":[{"name":"一级代理","rid":1},{"name":"大客户","rid":2}],"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 2
         * grades : [{"name":"一级代理","rid":1},{"name":"大客户","rid":2}]
         * next : null
         * prev : null
         */

        public int count;
        public List<GradesBean> grades;

        public static class GradesBean implements Parcelable {
            /**
             * name : 一级代理
             * rid : 1
             */

            public String name;
            public int rid;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeInt(this.rid);
            }

            public GradesBean() {
            }

            protected GradesBean(Parcel in) {
                this.name = in.readString();
                this.rid = in.readInt();
            }

            public static final Creator<GradesBean> CREATOR = new Creator<GradesBean>() {
                @Override
                public GradesBean createFromParcel(Parcel source) {
                    return new GradesBean(source);
                }

                @Override
                public GradesBean[] newArray(int size) {
                    return new GradesBean[size];
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
            dest.writeList(this.grades);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.grades = new ArrayList<GradesBean>();
            in.readList(this.grades, GradesBean.class.getClassLoader());
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

    public AddCustomerGradeData() {
    }

    protected AddCustomerGradeData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AddCustomerGradeData> CREATOR = new Parcelable.Creator<AddCustomerGradeData>() {
        @Override
        public AddCustomerGradeData createFromParcel(Parcel source) {
            return new AddCustomerGradeData(source);
        }

        @Override
        public AddCustomerGradeData[] newArray(int size) {
            return new AddCustomerGradeData[size];
        }
    };
}
