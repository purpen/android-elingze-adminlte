package com.stephen.taihuoniaolibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.stephen.taihuoniaolibrary.R;
import com.stephen.taihuoniaolibrary.common.THNApp;


public class THNToastUtil {
    private static int resInfo          = R.drawable.img_thn_default_info;
    private static int resSuccess       = R.drawable.img_thn_default_success;
    private static int resError         = R.drawable.img_thn_default_error;

    private static Toast toast;
    private static ImageView smallImg;
    private static TextView textView;

    private static void initToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View view = View.inflate(context, R.layout.view_thn_default_progress, null);
        toast.setView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivBigLoading);
        imageView.setVisibility(View.GONE);
        smallImg = (ImageView) view.findViewById(R.id.ivSmallLoading);
        smallImg.setVisibility(View.VISIBLE);
        textView = (TextView) view.findViewById(R.id.tvMsg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        textView.setVisibility(View.VISIBLE);
    }

    public static void showSuccess(int resid) {
        showSuccess(THNApp.getContext(), resid, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context, int resid) {
        showSuccess(context, resid, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context, int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resSuccess);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showSuccess(String message) {
        showSuccess(THNApp.getContext(), message, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context, String message) {
        showSuccess(context, message, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context, String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resSuccess);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showError(int resid) {
        showError(THNApp.getContext(), resid, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context, int resid) {
        showError(context, resid, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context, int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resError);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showError(String message) {
        showError(THNApp.getContext(), message, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context, String message) {
        showError(context, message, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context, String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resError);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showInfo(int resid) {
        showInfo(THNApp.getContext(), resid, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context, int resid) {
        showInfo(context, resid, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context, int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resInfo);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showInfo(String message) {
        showInfo(THNApp.getContext(), message, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context, String message) {
        showInfo(context, message, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context, String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resInfo);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }
}
