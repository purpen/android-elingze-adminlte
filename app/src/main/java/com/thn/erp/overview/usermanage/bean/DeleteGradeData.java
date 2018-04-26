package com.thn.erp.overview.usermanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DeleteGradeData implements Parcelable {

    /**
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public StatusBean status;
    public boolean success;

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
        dest.writeParcelable(this.status, flags);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
    }

    public DeleteGradeData() {
    }

    protected DeleteGradeData(Parcel in) {
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DeleteGradeData> CREATOR = new Parcelable.Creator<DeleteGradeData>() {
        @Override
        public DeleteGradeData createFromParcel(Parcel source) {
            return new DeleteGradeData(source);
        }

        @Override
        public DeleteGradeData[] newArray(int size) {
            return new DeleteGradeData[size];
        }
    };
}
