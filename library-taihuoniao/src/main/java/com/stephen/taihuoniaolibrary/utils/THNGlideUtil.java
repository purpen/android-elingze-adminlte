package com.stephen.taihuoniaolibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.stephen.taihuoniaolibrary.R;
import com.stephen.taihuoniaolibrary.common.THNApp;

/**
 * Created by Stephen on 11/20/2016.
 * Emial: 895745843@qq.com
 */
public class THNGlideUtil {
    private static final int DEFAULT_IMAGEID = R.drawable.default_error_load;
    private static final int ERROR_IMAGEID = R.drawable.default_error_load;
    private static final int FADE_IN_DURATION = 250;

    public static Context getContext() {
        return THNApp.getContext();
    }

    /**
     * 加载网络图片
     * 加载资源图片
     * 加载本地文件图片
     * 从Uri中加载
     *
     * @param T
     * @param imageView
     */
    public static <T> void displayImage(T T, ImageView imageView) {
        displayImage(T, imageView, null);
    }

    public static <T> void displayImage(T T, ImageView imageView, Integer defaultImageId) {
        displayImage(T, imageView, defaultImageId, null);
    }

    public static <T> void displayImage(T T, ImageView imageView, Integer defaultImageId, Integer errorId) {
        Glide.with(getContext()).load(T).asBitmap()
                .placeholder((defaultImageId == null) ? DEFAULT_IMAGEID : defaultImageId)
                .error((errorId == null) ? ERROR_IMAGEID : errorId)
                .into(imageView);
    }

    /**
     * 加载GIF图片
     *
     * @param T
     * @param imageView
     */
    public static  <T> void displayGifImage(T T, ImageView imageView) {
        displayGifImage(T, imageView, null);
    }

    public static <T> void displayGifImage(T T, ImageView imageView, Integer defaultImageId) {
        displayGifImage(T, imageView, defaultImageId, null);
    }

    public static <T> void displayGifImage(T T, ImageView imageView, Integer defaultImageId, Integer errorId) {
        Glide.with(getContext()).load(T).asGif()
                .placeholder((defaultImageId == 0) ? DEFAULT_IMAGEID : defaultImageId)
                .error((errorId == null) ? ERROR_IMAGEID : errorId)
                .into(imageView);
    }

    /**
     * 淡入显示图片
     * @param T
     * @param imageView
     * @param <T>
     */
    public static <T> void displayImageFadein(T T, ImageView imageView) {
        displayImageFadein(T, imageView, 0);
    }

    public static <T> void displayImageFadein(T T, ImageView imageView, int defaultImageId) {
        displayImageFadein(T, imageView, defaultImageId, 0);
    }

    public static <T> void displayImageFadein(T T, ImageView imageView, int defaultImageId, int errorId) {
        displayImageFadein(T, imageView, defaultImageId, errorId, FADE_IN_DURATION);
    }



    public static  <T> void displayImageFadein(T T, ImageView imageView, int defaultImageId, int errorId, int durationMillis) {
        Glide.with(getContext()).load(T).
                placeholder((defaultImageId == 0) ? DEFAULT_IMAGEID : defaultImageId).
                error((errorId == 0) ? ERROR_IMAGEID : errorId).
                crossFade().crossFade(durationMillis).
                into(imageView);
    }

    /**
     * 指定加载尺寸
     * @param T
     * @param imageView
     * @param width
     * @param height
     * @param <T>
     */
    public static  <T> void displayImageFadeinWithDimen(T T, ImageView imageView, int width, int height) {
        Glide.with(getContext()).load(T).
                placeholder(DEFAULT_IMAGEID).
                error(ERROR_IMAGEID).crossFade().crossFade(FADE_IN_DURATION).override(width, height).
                into(imageView);
    }

    public static  <T> void displayImageFadeinCenterCrop(T T, ImageView imageView, int width, int height ) {
        int lWidth;
        int lHeight;
        if (width <= 0 || height <= 0) {
            lWidth = THNScreenUtil.getScreenWidth();
            lHeight = lWidth;
        } else {
            lWidth = width;
            lHeight = height;
        }
        Glide.with(getContext()).
                load(T).
                placeholder(DEFAULT_IMAGEID).
                error(ERROR_IMAGEID).
                centerCrop().
                crossFade(FADE_IN_DURATION).
                override(lWidth,lHeight).
                into(imageView);
    }

    public static void displayImageWithId(int imgId,ImageView imageView){
        Glide.with(getContext()).load(imgId).placeholder(DEFAULT_IMAGEID).error(ERROR_IMAGEID).into(imageView);
    }

    public static <T> void displayImageNoFading(T T,ImageView imageView){
        Glide.with(getContext()).load(T).diskCacheStrategy(DiskCacheStrategy.SOURCE ).crossFade(0).placeholder(DEFAULT_IMAGEID).error(ERROR_IMAGEID).into(imageView);
    }

    public static <T> void loadBitmap(T T, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(getContext()) .load(T).asBitmap() .dontAnimate() .into(simpleTarget);
    }
}
