package com.thn.erp.album;

import android.os.Parcel;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.thn.basemodule.tools.GlideUtil;
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
        GlideUtil.loadImage(path, imageView);
    }

    @Override
    public void displayCameraItem(ImageView imageView) {
        GlideUtil.loadImage(img_camera, imageView);
    }

    @Override
    public void scrolling(GridView view) {
        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState==SCROLL_STATE_IDLE){
                    GlideUtil.resumeRequests(view.getContext());
                }

                if (scrollState ==SCROLL_STATE_FLING) {
                    GlideUtil.pauseRequests(view.getContext());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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
