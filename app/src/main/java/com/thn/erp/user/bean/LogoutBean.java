package com.thn.erp.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lilin on 2017/7/18.
 */

public class LogoutBean implements Parcelable {

    /**
     * status : {"code":401,"message":"Logout"}
     * success : true
     */

    public StatusBean status;
    public boolean success;

    public static class StatusBean implements Parcelable {
        /**
         * code : 401
         * message : Logout
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
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public LogoutBean() {
    }

    protected LogoutBean(Parcel in) {
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<LogoutBean> CREATOR = new Parcelable.Creator<LogoutBean>() {
        @Override
        public LogoutBean createFromParcel(Parcel source) {
            return new LogoutBean(source);
        }

        @Override
        public LogoutBean[] newArray(int size) {
            return new LogoutBean[size];
        }
    };
}
