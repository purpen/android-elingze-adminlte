package com.thn.erp.album;

import android.os.Parcel;
import android.widget.GridView;
import android.widget.ImageView;

import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;


public class ImageLoaderEngine implements LoadEngine {
    private int img_loading;
    private int img_camera;
    public ImageLoaderEngine() {
        this(0, 0);
    }

    public ImageLoaderEngine(int img_loading) {
        this(img_loading,0);
    }

    public ImageLoaderEngine(int img_loading, int img_camera) {
        if (img_loading == 0)
            this.img_loading = 0;
        else
            this.img_loading = img_loading;
        if (img_camera == 0)
            this.img_camera = R.mipmap.ic_camera;
        else
            this.img_camera = img_camera;
    }

    @Override
    public void displayImage(String path, ImageView imageView) {
        THNGlideUtil.displayImageFadein(path, imageView);
    }

    @Override
    public void displayCameraItem(ImageView imageView) {
        THNGlideUtil.displayImageFadein(img_camera, imageView);
    }

    @Override
    public void scrolling(GridView view) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.img_loading);
        dest.writeInt(this.img_camera);
    }

    protected ImageLoaderEngine(Parcel in) {
        this.img_loading = in.readInt();
        this.img_camera = in.readInt();
    }

    public static final Creator<ImageLoaderEngine> CREATOR = new Creator<ImageLoaderEngine>() {
        public ImageLoaderEngine createFromParcel(Parcel source) {
            return new ImageLoaderEngine(source);
        }

        public ImageLoaderEngine[] newArray(int size) {
            return new ImageLoaderEngine[size];
        }
    };
}
