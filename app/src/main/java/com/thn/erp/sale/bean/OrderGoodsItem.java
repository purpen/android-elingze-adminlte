package com.thn.erp.sale.bean;

/**
 * Created by lilin on 2018/3/27.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 创建订单界面商品列表
 */
public class OrderGoodsItem implements Parcelable {

    /**
     * rid : 117280530556
     * quantity : 1
     * deal_price : 23
     * discount_amount : 0
     * warehouse_id : 0
     */

    public String rid;
    public int quantity;
    public String deal_price;
    public double discount_amount;
    public long warehouse_id;

    public OrderGoodsItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rid);
        dest.writeInt(this.quantity);
        dest.writeString(this.deal_price);
        dest.writeDouble(this.discount_amount);
        dest.writeLong(this.warehouse_id);
    }

    protected OrderGoodsItem(Parcel in) {
        this.rid = in.readString();
        this.quantity = in.readInt();
        this.deal_price = in.readString();
        this.discount_amount = in.readDouble();
        this.warehouse_id = in.readLong();
    }

    public static final Creator<OrderGoodsItem> CREATOR = new Creator<OrderGoodsItem>() {
        @Override
        public OrderGoodsItem createFromParcel(Parcel source) {
            return new OrderGoodsItem(source);
        }

        @Override
        public OrderGoodsItem[] newArray(int size) {
            return new OrderGoodsItem[size];
        }
    };
}
