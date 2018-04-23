package com.thn.erp.more.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stephen on 2018/4/17 10:29
 * Email: 895745843@qq.com
 */

public class PrepareExportStockBean implements Parcelable {

    private String orderId;
    private String customerName;
    private String orderAmount;
    private String orderProduct;
    private String orderingTime;

    public PrepareExportStockBean( String customerName, String orderAmount, String orderProduct) {
        this.customerName = customerName;
        this.orderAmount = orderAmount;
        this.orderProduct = orderProduct;
    }

    public PrepareExportStockBean(String orderId, String customerName, String orderAmount, String orderProduct, String orderingTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderAmount = orderAmount;
        this.orderProduct = orderProduct;
        this.orderingTime = orderingTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        this.orderProduct = orderProduct;
    }

    public String getOrderingTime() {
        return orderingTime;
    }

    public void setOrderingTime(String orderingTime) {
        this.orderingTime = orderingTime;
    }

    @Override
    public String toString() {
        return "PrepareExportStockBean{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", orderProduct='" + orderProduct + '\'' +
                ", orderingTime='" + orderingTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.customerName);
        dest.writeString(this.orderAmount);
        dest.writeString(this.orderProduct);
        dest.writeString(this.orderingTime);
    }

    protected PrepareExportStockBean(Parcel in) {
        this.orderId = in.readString();
        this.customerName = in.readString();
        this.orderAmount = in.readString();
        this.orderProduct = in.readString();
        this.orderingTime = in.readString();
    }

    public static final Parcelable.Creator<PrepareExportStockBean> CREATOR = new Parcelable.Creator<PrepareExportStockBean>() {
        @Override
        public PrepareExportStockBean createFromParcel(Parcel source) {
            return new PrepareExportStockBean(source);
        }

        @Override
        public PrepareExportStockBean[] newArray(int size) {
            return new PrepareExportStockBean[size];
        }
    };
}

