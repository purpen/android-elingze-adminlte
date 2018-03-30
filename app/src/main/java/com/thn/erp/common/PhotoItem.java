package com.thn.erp.common;

import android.support.annotation.NonNull;

/**
 * Created by taihuoniao on 2016/3/14.
 */
public class PhotoItem implements Comparable<PhotoItem> {
    private String imageUri;
//    private long id;
    private long date;
    private boolean isChecked;

    public PhotoItem() {
    }

    public PhotoItem(long id, String imageUri, long date) {
//        this.id = id;
        this.imageUri = imageUri;
        this.date = date;
    }

//    public long getId() {
//        return id;
//    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull PhotoItem another) {
        return (int) ((another.getDate() - date) / 1000);
    }
}
