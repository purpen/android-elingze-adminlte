package com.thn.basemodule.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.thn.basemodule.R;

/**
 * 图片加载工具类
 */
public class GlideUtil {
    //默认占位图
    public static final int DEFAULT_PLACE_HOLDER = R.drawable.default_load;
    public static final int DEFAULT_ERROR_HOLDER = R.drawable.default_load;
    //The duration of the cross fade animation in milliseconds.
    public static final int FADING_DURING = 250;


    public static <T> void loadImage(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    /**
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageWithFading(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).transition(DrawableTransitionOptions.withCrossFade(FADING_DURING)).apply(requestOptions).into(imageView);
    }



    /**
     * By default you get a Drawable RequestBuilder
     * if you call asBitmap() you will get a Bitmap RequestBuilder
     *
     * @param t
     * @param imageView
     * @param <T>
     */
    public static <T> void loadImageAsBitmap(T t, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).asBitmap().apply(requestOptions).load(t).into(imageView);
    }


    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }


    /**
     * 加载指定宽高图片
     * @param t
     * @param imageView
     * @param width
     * @param height
     * @param <T>
     */
    public static <T> void loadImageWithDimen(T t, ImageView imageView, int width, int height) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(width, height).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }

    /**
     * 加载指定宽高同size图片
     * @param t
     * @param imageView
     * @param size
     * @param <T>
     */
    public static <T> void loadImageWithDimen(T t, ImageView imageView, int size) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL).override(size).error(DEFAULT_ERROR_HOLDER).placeholder(DEFAULT_PLACE_HOLDER);
        Glide.with(imageView.getContext()).load(t).apply(requestOptions).into(imageView);
    }
}
