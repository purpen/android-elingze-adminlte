package com.thn.basemodule.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.thn.basemodule.R;


public class ToastUtils {
    private static int resInfo = R.drawable.ic_svstatus_info;
    private static int resSuccess = R.drawable.ic_svstatus_success;
    private static int resError = R.drawable.ic_svstatus_error;

    private static Toast toast;
    private static ImageView smallImg;
    private static TextView textView;

    private static void initToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View view = View.inflate(context, R.layout.view_svprogress, null);
        toast.setView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivBigLoading);
        imageView.setVisibility(View.GONE);
        smallImg = (ImageView) view.findViewById(R.id.ivSmallLoading);
        smallImg.setVisibility(View.VISIBLE);
        textView = (TextView) view.findViewById(R.id.tvMsg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        textView.setVisibility(View.VISIBLE);
    }

    public static void showSuccess(Context context,String message) {
        showSuccess(context,message, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context,int resid) {
        showSuccess(context,resid, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(Context context,String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resSuccess);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showSuccess(Context context,int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resSuccess);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showError(Context context,String message) {
        showError(context,message, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context,int resid) {
        showError(context,resid, Toast.LENGTH_SHORT);
    }

    public static void showError(Context context,String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resError);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showError(Context context,int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resError);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showInfo(Context context,String message) {
        showInfo(context,message, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context,int resid) {
        showInfo(context,resid, Toast.LENGTH_SHORT);
    }

    public static void showInfo(Context context,String message, int duration) {
        initToast(context);
        smallImg.setImageResource(resInfo);
        textView.setText(message);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showInfo(Context context,int resid, int duration) {
        initToast(context);
        smallImg.setImageResource(resInfo);
        textView.setText(resid);
        toast.setDuration(duration);
        toast.show();
    }
}
