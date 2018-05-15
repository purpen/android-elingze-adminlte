package com.thn.erp.utils;

import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thn.erp.AppApplication;
import com.thn.erp.GlideApp;
import com.thn.erp.R;

/**
 * 图片加载工具类
 */
public class GlideUtil {
    /**
     * 默认占位图
     */
    public static final int DEFAULT_PLACE_HOLDER = R.mipmap.default_load;
    public static final int DEFAULT_ERROR_HOLDER = R.mipmap.default_load;


    public static <T> void loadImage(T t, ImageView imageView){
        GlideApp.with(AppApplication.getContext()).load(t).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(DEFAULT_PLACE_HOLDER).error(DEFAULT_ERROR_HOLDER).into(imageView);
    }

    /**
     * By default you get a Drawable RequestBuilder
     * if you call asBitmap() you will get a Bitmap RequestBuilder
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageAsBitmap(T t, ImageView imageView){
        GlideApp.with(AppApplication.getContext()).asBitmap().load(t).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(DEFAULT_PLACE_HOLDER).error(DEFAULT_ERROR_HOLDER).into(imageView);
    }


    public static void resumeRequests() {
        GlideApp.with(AppApplication.getContext()).resumeRequests();
    }

    public static void pauseRequests() {
        GlideApp.with(AppApplication.getContext()).pauseRequests();
    }


    public static <T> void loadImageWithDimen(T t, ImageView imageView, int width, int height) {
        GlideApp.with(AppApplication.getContext()).load(t).placeholder(DEFAULT_PLACE_HOLDER).error(DEFAULT_ERROR_HOLDER).override(width,height).into(imageView);
    }
}
