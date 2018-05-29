package com.thn.basemodule.tools;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.thn.basemodule.R;

/**
 * Toast工具类
 */
public class ToastUtil {
    private static int resInfo = R.drawable.ic_svstatus_info;
    private static int resSuccess = R.drawable.ic_svstatus_success;
    private static int resError = R.drawable.ic_svstatus_error;

    static class ToastHolder {
        Toast toast;
        ImageView imageView;
        TextView textView;
    }

    private static ToastHolder initToastHolder() {
        ToastHolder toastHolder = new ToastHolder();

        Toast toast = new Toast(BaseModuleContext.getContext());
        View view = View.inflate(BaseModuleContext.getContext(), R.layout.view_svprogress, null);
        toast.setView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivBigLoading);
        imageView.setVisibility(View.GONE);
        imageView = (ImageView) view.findViewById(R.id.ivSmallLoading);
        imageView.setVisibility(View.VISIBLE);

        TextView textView = (TextView) view.findViewById(R.id.tvMsg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        textView.setVisibility(View.VISIBLE);

        toastHolder.toast = toast;
        toastHolder.imageView = imageView;
        toastHolder.textView = textView;

        return toastHolder;
    }

    public static void showSuccess(String message) {
        showSuccess(message, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(int resid) {
        showSuccess(resid, Toast.LENGTH_SHORT);
    }

    public static void showSuccess(String message, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resSuccess);
        toastHolder.textView.setText(message);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }

    public static void showSuccess(int resid, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resSuccess);
        toastHolder.textView.setText(resid);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }

    public static void showError(String message) {
        showError(message, Toast.LENGTH_SHORT);
    }

    public static void showError(int resid) {
        showError(resid, Toast.LENGTH_SHORT);
    }

    public static void showError(String message, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resError);
        toastHolder.textView.setText(message);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }

    public static void showError(int resid, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resError);
        toastHolder.textView.setText(resid);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }

    public static void showInfo(String message) {
        showInfo(message, Toast.LENGTH_SHORT);
    }

    public static void showInfo(int resid) {
        showInfo(resid, Toast.LENGTH_SHORT);
    }

    public static void showInfo(String message, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resInfo);
        toastHolder.textView.setText(message);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }

    public static void showInfo(int resid, int duration) {
        ToastHolder toastHolder = initToastHolder();
        toastHolder.imageView.setImageResource(resInfo);
        toastHolder.textView.setText(resid);
        toastHolder.toast.setDuration(duration);
        toastHolder.toast.show();
    }
}
