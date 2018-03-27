package com.thn.erp.goods.brand;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Stephen on 2018/3/26 16:34
 * Email: 895745843@qq.com
 */

public class GoodsBrandData implements Parcelable {

    /**
     * data : {"brands":[{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"潮流设计","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FuFOec2y1pOxqfEL-mZ9H_wLZUxM.jpeg","name":"WK潮牌","rid":"656942713","sort_order":1,"status":1},{"banner":"https://kg.erp.taihuoniao.com/20180226/FuFwiHE-m2JV92-aiw9uKWMj9-69.jpg","description":"","features":"超性价比配件全套","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FllIDHtSezOCwguijL_Ad51o4jI5.jpg","name":"Remax","rid":"636728051","sort_order":2,"status":1},{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"蓝牙心率的智能耳机","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FqO_tFC8gcdUtOmX_rOIwA3n9WaI.jpg","name":"mops","rid":"602187359","sort_order":3,"status":1},{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"智能拖地机","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FjGjbctl8sQJwA-c0vK1J9Ukcov8.jpg","name":"洒洼地卡","rid":"697243056","sort_order":4,"status":1}],"count":4,"next":null,"prev":null}
     * status : {"code":200,"message":"Ok all right."}
     * success : true
     */

    private DataEntity data;
    private StatusEntity status;
    private boolean success;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataEntity getData() {
        return data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }

    public static class DataEntity implements Parcelable {
        /**
         * brands : [{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"潮流设计","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FuFOec2y1pOxqfEL-mZ9H_wLZUxM.jpeg","name":"WK潮牌","rid":"656942713","sort_order":1,"status":1},{"banner":"https://kg.erp.taihuoniao.com/20180226/FuFwiHE-m2JV92-aiw9uKWMj9-69.jpg","description":"","features":"超性价比配件全套","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FllIDHtSezOCwguijL_Ad51o4jI5.jpg","name":"Remax","rid":"636728051","sort_order":2,"status":1},{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"蓝牙心率的智能耳机","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FqO_tFC8gcdUtOmX_rOIwA3n9WaI.jpg","name":"mops","rid":"602187359","sort_order":3,"status":1},{"banner":"https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png","description":"","features":"智能拖地机","is_recommended":false,"logo":"https://kg.erp.taihuoniao.com/20180226/FjGjbctl8sQJwA-c0vK1J9Ukcov8.jpg","name":"洒洼地卡","rid":"697243056","sort_order":4,"status":1}]
         * count : 4
         * next : null
         * prev : null
         */

        private int count;
        private String next;
        private String prev;
        private List<BrandsEntity> brands;

        public void setCount(int count) {
            this.count = count;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }

        public void setBrands(List<BrandsEntity> brands) {
            this.brands = brands;
        }

        public int getCount() {
            return count;
        }

        public Object getNext() {
            return next;
        }

        public Object getPrev() {
            return prev;
        }

        public List<BrandsEntity> getBrands() {
            return brands;
        }

        public static class BrandsEntity implements Parcelable {
            /**
             * banner : https://kg.erp.taihuoniao.com/static/img/default-logo-540x540.png
             * description :
             * features : 潮流设计
             * is_recommended : false
             * logo : https://kg.erp.taihuoniao.com/20180226/FuFOec2y1pOxqfEL-mZ9H_wLZUxM.jpeg
             * name : WK潮牌
             * rid : 656942713
             * sort_order : 1
             * status : 1
             */

            private String banner;
            private String description;
            private String features;
            private boolean is_recommended;
            private String logo;
            private String name;
            private String rid;
            private int sort_order;
            private int status;

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setFeatures(String features) {
                this.features = features;
            }

            public void setIs_recommended(boolean is_recommended) {
                this.is_recommended = is_recommended;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getBanner() {
                return banner;
            }

            public String getDescription() {
                return description;
            }

            public String getFeatures() {
                return features;
            }

            public boolean getIs_recommended() {
                return is_recommended;
            }

            public String getLogo() {
                return logo;
            }

            public String getName() {
                return name;
            }

            public String getRid() {
                return rid;
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
                dest.writeString(this.banner);
                dest.writeString(this.description);
                dest.writeString(this.features);
                dest.writeByte(this.is_recommended ? (byte) 1 : (byte) 0);
                dest.writeString(this.logo);
                dest.writeString(this.name);
                dest.writeString(this.rid);
                dest.writeInt(this.sort_order);
                dest.writeInt(this.status);
            }

            public BrandsEntity() {
            }

            protected BrandsEntity(Parcel in) {
                this.banner = in.readString();
                this.description = in.readString();
                this.features = in.readString();
                this.is_recommended = in.readByte() != 0;
                this.logo = in.readString();
                this.name = in.readString();
                this.rid = in.readString();
                this.sort_order = in.readInt();
                this.status = in.readInt();
            }

            public static final Creator<BrandsEntity> CREATOR = new Creator<BrandsEntity>() {
                @Override
                public BrandsEntity createFromParcel(Parcel source) {
                    return new BrandsEntity(source);
                }

                @Override
                public BrandsEntity[] newArray(int size) {
                    return new BrandsEntity[size];
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
            dest.writeTypedList(this.brands);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.count = in.readInt();
            this.next = in.readParcelable(Object.class.getClassLoader());
            this.prev = in.readParcelable(Object.class.getClassLoader());
            this.brands = in.createTypedArrayList(BrandsEntity.CREATOR);
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
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
            public StatusEntity createFromParcel(Parcel source) {
                return new StatusEntity(source);
            }

            public StatusEntity[] newArray(int size) {
                return new StatusEntity[size];
            }
        };
    }

    public GoodsBrandData() {
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

    protected GoodsBrandData(Parcel in) {
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
        this.status = in.readParcelable(StatusEntity.class.getClassLoader());
        this.success = in.readByte() != 0;
    }

    public static final Creator<GoodsBrandData> CREATOR = new Creator<GoodsBrandData>() {
        @Override
        public GoodsBrandData createFromParcel(Parcel source) {
            return new GoodsBrandData(source);
        }

        @Override
        public GoodsBrandData[] newArray(int size) {
            return new GoodsBrandData[size];
        }
    };
}
