package com.thn.erp.goods.category;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 2018/3/26 17:44
 * Email: 895745843@qq.com
 */

public class GoodsCategoryData implements Parcelable {

    /**
     * data : {"categories":[{"description":"","id":1,"name":"家居","sort_order":1,"status":1},{"description":"","id":3,"name":"创意设计","sort_order":3,"status":1},{"description":"","id":4,"name":"杯子","sort_order":2,"status":1},{"description":"","id":5,"name":"智能硬件","sort_order":1,"status":1}],"count":17,"next":"http://127.0.0.1:9000/api/v1.0/categories?page=2","prev":null}
     * status : {"code":200,"message":"Ok all right."}
     */

    private DataEntity data;
    private StatusEntity status;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public static class DataEntity implements Parcelable {
        /**
         * categories : [{"description":"","id":1,"name":"家居","sort_order":1,"status":1},{"description":"","id":3,"name":"创意设计","sort_order":3,"status":1},{"description":"","id":4,"name":"杯子","sort_order":2,"status":1},{"description":"","id":5,"name":"智能硬件","sort_order":1,"status":1}]
         * count : 17
         * next : http://127.0.0.1:9000/api/v1.0/categories?page=2
         * prev : null
         */

        private int count;
        private String next;
        private String prev;
        private List<CategoriesEntity> categories;

        public void setCount(int count) {
            this.count = count;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }

        public void setCategories(List<CategoriesEntity> categories) {
            this.categories = categories;
        }

        public int getCount() {
            return count;
        }

        public String getNext() {
            return next;
        }

        public Object getPrev() {
            return prev;
        }

        public List<CategoriesEntity> getCategories() {
            return categories;
        }

        public static class CategoriesEntity implements Parcelable {
            /**
             * description :
             * id : 1
             * name : 家居
             * sort_order : 1
             * status : 1
             */

            private String description;
            private int id;
            private String name;
            private int sort_order;
            private int status;

            public void setDescription(String description) {
                this.description = description;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDescription() {
                return description;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getSort_order() {
                return sort_order;
            }

            public int getStatus() {
                return status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.description);
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeInt(this.sort_order);
                dest.writeInt(this.status);
            }

            public CategoriesEntity() {
            }

            protected CategoriesEntity(Parcel in) {
                this.description = in.readString();
                this.id = in.readInt();
                this.name = in.readString();
                this.sort_order = in.readInt();
                this.status = in.readInt();
            }

            public static final Creator<CategoriesEntity> CREATOR = new Creator<CategoriesEntity>() {
                @Override
                public CategoriesEntity createFromParcel(Parcel source) {
                    return new CategoriesEntity(source);
                }

                @Override
                public CategoriesEntity[] newArray(int size) {
                    return new CategoriesEntity[size];
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
            dest.writeString(this.next);
            dest.writeString(this.prev);
            dest.writeList(this.categories);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.count = in.readInt();
            this.next = in.readString();
            this.prev = in.readString();
            this.categories = new ArrayList<CategoriesEntity>();
            in.readList(this.categories, CategoriesEntity.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    public static class StatusEntity implements Parcelable {
        /**
         * code : 200
         * message : Ok all right.
         */

        private int code;
        private String message;

        public void setCode(int code) {
            this.code = code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeString(this.message);
        }

        public StatusEntity() {
        }

        protected StatusEntity(Parcel in) {
            this.code = in.readInt();
            this.message = in.readString();
        }

        public static final Creator<StatusEntity> CREATOR = new Creator<StatusEntity>() {
            @Override
            public StatusEntity createFromParcel(Parcel source) {
                return new StatusEntity(source);
            }

            @Override
            public StatusEntity[] newArray(int size) {
                return new StatusEntity[size];
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
    }

    public GoodsCategoryData() {
    }

    protected GoodsCategoryData(Parcel in) {
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.status = in.readParcelable(StatusEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoodsCategoryData> CREATOR = new Parcelable.Creator<GoodsCategoryData>() {
        @Override
        public GoodsCategoryData createFromParcel(Parcel source) {
            return new GoodsCategoryData(source);
        }

        @Override
        public GoodsCategoryData[] newArray(int size) {
            return new GoodsCategoryData[size];
        }
    };
}