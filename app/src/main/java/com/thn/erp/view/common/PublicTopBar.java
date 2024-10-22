package com.thn.erp.view.common;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;

/**
 * Created by Stephen on 2018/3/12 15:35
 * Email: 895745843@qq.com
 */

public class PublicTopBar extends RelativeLayout implements View.OnClickListener {

    private RelativeLayout relativeLayoutTopBarLeft;
    private ImageView imageViewTopBarLeft;
    private RelativeLayout relativeLayoutTopBarCenter;
    private TextView textViewTopBarCenter;
    private RelativeLayout relativeLayoutTopBarRight;
    private TextView textViewTopBarRight;

    private ImpTopbarOnClickListener mImpTopbarOnClickListener;
    private boolean isBackKey;

    public PublicTopBar(Context context) {
        this(context, null);
    }

    public PublicTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_public_topbar, this, true);
        onViewCreated();
    }

    private void onViewCreated() {
        initView();
//        setTopBarOnClickListener();
    }

    private void initView(){
        relativeLayoutTopBarLeft = (RelativeLayout) findViewById(R.id.relativeLayout_topBar_left);
        imageViewTopBarLeft = (ImageView) findViewById(R.id.imageView_topBar_left);
        relativeLayoutTopBarCenter = (RelativeLayout) findViewById(R.id.relativeLayout_topBar_center);
        textViewTopBarCenter = (TextView) findViewById(R.id.textView_topBar_center);
        relativeLayoutTopBarRight = (RelativeLayout) findViewById(R.id.relativeLayout_topBar_right);
        textViewTopBarRight = (TextView) findViewById(R.id.textView_topBar_right);
    }


    public void setTopBarOnClickListener(ImpTopbarOnClickListener impTopbarOnClickListener) {
        mImpTopbarOnClickListener = impTopbarOnClickListener;
        relativeLayoutTopBarLeft.setOnClickListener(this);
        relativeLayoutTopBarCenter.setOnClickListener(this);
        relativeLayoutTopBarRight.setOnClickListener(this);
    }

    // 设置背景颜色
    public void setTopBarBackgroud(int color){
        setBackgroundColor(color);
    }

    // 左
/*    public void setRelativeLayoutTopBarLeft(int resid){
        imageViewTopBarLeft.setImageResource(resid);
    }*/
    public void setTopBarLeftImageView(int resid){
        imageViewTopBarLeft.setImageResource(resid);
        imageViewTopBarLeft.setVisibility(VISIBLE);
    }

    public void setTopBarLeftImageView(boolean imgBack){
        isBackKey = imgBack;
        if (!imgBack) {
            return;
        }
        imageViewTopBarLeft.setImageResource(R.mipmap.back_white);
        imageViewTopBarLeft.setVisibility(VISIBLE);
    }

    public void setRelativeLayoutTopBarLeft(int resid, ImpTopbarOnClickListener impTopbarOnClickListener) {

    }

    // 中
/*    public void setRelativeLayoutTopBarCenter(int resid){
        textViewTopBarCenter.setText(resid);
    }*/
    public void setTopBarCenterTextView(String text, int color){
        textViewTopBarCenter.setText(text);
        textViewTopBarCenter.setTextColor(color);
        textViewTopBarCenter.setVisibility(VISIBLE);
    }

    // 右
/*    public void setRelativeLayoutTopBarRight(int resid){

    }*/

    public void setTopBarRightTextView(String text, int color) {
        textViewTopBarRight.setText(text);
        textViewTopBarRight.setTextColor(color);
        textViewTopBarRight.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout_topBar_left:
                if (isBackKey) {
                    Context context = view.getContext();
                    if (context instanceof Activity) {
                        Activity activity = (Activity) context;
                        activity.finish();
                        break;
                    }
                }
                mImpTopbarOnClickListener.onTopBarClick(view, ImpTopbarOnClickListener.LEFT);
                break;
            case R.id.relativeLayout_topBar_center: mImpTopbarOnClickListener.onTopBarClick(view, ImpTopbarOnClickListener.CENTER);
                break;
            case R.id.relativeLayout_topBar_right: mImpTopbarOnClickListener.onTopBarClick(view, ImpTopbarOnClickListener.RIGHT);
                break;
        }
    }
}
