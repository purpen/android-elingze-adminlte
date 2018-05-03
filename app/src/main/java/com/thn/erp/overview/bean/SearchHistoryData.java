package com.thn.erp.overview.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilin on 2018/5/3.
 */

public class SearchHistoryData implements Parcelable {

    /**
     * data : {"count":1,"next":null,"prev":null,"search_items":[{"query_word":"电源","search_at":1520149158,"search_times":1,"total_count":0}]}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    public DataBean data;
    public StatusBean status;
    public boolean success;

    public static class DataBean implements Parcelable {
        /**
         * count : 1
         * next : null
         * prev : null
         * search_items : [{"query_word":"电源","search_at":1520149158,"search_times":1,"total_count":0}]
         */

        public int count;
        public List<SearchItemsBean> search_items;

        public static class SearchItemsBean implements Parcelable {
            /**
             * query_word : 电源
             * search_at : 1520149158
             * search_times : 1
             * total_count : 0
             */

            public String query_word;
            public int search_at;
            public int search_times;
            public int total_count;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.query_word);
                dest.writeInt(this.search_at);
                dest.writeInt(this.search_times);
                dest.writeInt(this.total_count);
            }

            public SearchItemsBean() {
            }

            protected SearchItemsBean(Parcel in) {
                this.query_word = in.readString();
                this.search_at = in.readInt();
                this.search_times = in.readInt();
                this.total_count = in.readInt();
            }

            public static final Creator<SearchItemsBean> CREATOR = new Creator<SearchItemsBean>() {
                @Override
                public SearchItemsBean createFromParcel(Parcel source) {
                    return new SearchItemsBean(source);
                }

                @Override
                public SearchItemsBean[] newArray(int size) {
                    return new SearchItemsBean[size];
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
            dest.writeList(this.search_items);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.count = in.readInt();
            this.search_items = new ArrayList<SearchItemsBean>();
            in.readList(this.search_items, SearchItemsBean.class.getClassLoader());
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

    public SearchHistoryData() {
    }

    protected SearchHistoryData(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SearchHistoryData> CREATOR = new Parcelable.Creator<SearchHistoryData>() {
        @Override
        public SearchHistoryData createFromParcel(Parcel source) {
            return new SearchHistoryData(source);
        }

        @Override
        public SearchHistoryData[] newArray(int size) {
            return new SearchHistoryData[size];
        }
    };
}
