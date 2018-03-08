package com.thn.erp.view.svprogress;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.thn.erp.R;


/**
 * Created by taihuoniao on 2016/1/21.
 */
public class WaitingDialog extends Dialog {
    private ImageView ivBigLoading;
    private RotateAnimation mRotateAnimation;
    private Activity activity;
    public WaitingDialog(Activity activity) {
        this(activity, R.style.waiting_dialog);
    }

    private WaitingDialog(Activity activity, int theme) {
        super(activity, R.style.waiting_dialog);
        this.activity=activity;
        this.setContentView(R.layout.waiting_dialog_layout);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
//        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        initViews();
        init();
    }
    private void initViews() {
        ivBigLoading = (ImageView) findViewById(R.id.ivBigLoading);
    }

    private void init() {
        mRotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
    }

    @Override
    public void show() {
        if (activity.isFinishing()) return;
        clearAnimations();
        super.show();
        ivBigLoading.startAnimation(mRotateAnimation);
    }

    @Override
    public void dismiss() {
        if (activity.isFinishing()) return;
        super.dismiss();
        clearAnimations();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }

    private void clearAnimations() {
        ivBigLoading.clearAnimation();
    }

}
