package com.thn.erp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thn.erp.R;


public class CustomHeadView extends RelativeLayout {
    private Context context;
    private ImageButton head_goback;
    private ImageButton iv_left;
    private TextView head_center_tv;
    private ImageView iv_center_logo;
    private ImageButton iv_head_search;
    private ImageButton head_view_shop;
    private RelativeLayout rl_head_shop;
    private TextView tv_head_right;
    private TextView tv_tip_num;
    private ImageButton ib_right;
    private RelativeLayout rootView;
    public void setGoBackListenr(IgobackLister igobackLister){
        mIgobackLister = igobackLister;
    }
    private IgobackLister mIgobackLister;

    public interface IgobackLister {
        void goback();
    }
    public CustomHeadView(Context context) {
        this(context, null);
    }

    public CustomHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflatelayout(context);
    }

    private void inflatelayout(Context context) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_headview_layout, this, true);
        rootView = (RelativeLayout) view.findViewById(R.id.relativeLayout_titleBar);
        initViews(this.rootView);
        initViews(view);
    }

    private void initViews(View view) {
        head_goback = (ImageButton) view.findViewById(R.id.head_goback);
        iv_left = (ImageButton) view.findViewById(R.id.iv_left);
        head_center_tv = (TextView) view.findViewById(R.id.head_center_tv);
        iv_center_logo = (ImageView) view.findViewById(R.id.iv_center_logo);
//        iv_head_search = (ImageButton) view_link_help.findViewById(R.id.iv_head_search);
        rl_head_shop = (RelativeLayout) view.findViewById(R.id.rl_head_shop);
        head_view_shop = (ImageButton) view.findViewById(R.id.head_view_shop);
        tv_tip_num = (TextView) view.findViewById(R.id.tv_tip_num);
        tv_head_right = (TextView) view.findViewById(R.id.tv_head_right);
        ib_right = (ImageButton) view.findViewById(R.id.ib_right);
        head_goback.setOnClickListener(onClickListener);
//        head_view_shop.setOnClickListener(onClickListener);
        rl_head_shop.setOnClickListener(onClickListener);
//        iv_head_search.setOnClickListener(onClickListener);
        iv_left.setOnClickListener(onClickListener);
    }

    public void setRightImgBtnShow(boolean isShow) {
        if (isShow) {
            ib_right.setVisibility(VISIBLE);
        } else {
            ib_right.setVisibility(GONE);
        }
    }

    public ImageButton getRightImgBtn() {
        return ib_right;
    }


    public ImageButton getShopImg() {
        return head_view_shop;
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.head_goback:
                    if (mIgobackLister != null) {
                        mIgobackLister.goback();
                    } else {
                        if (context instanceof Activity) {
                            ((Activity)context).onBackPressed();
                        }
                    }
                    break;
                case R.id.iv_left:
                    //TODO
                    break;
//                case R.id.head_view_shop:
//                    if (LoginUtil.isLogin()){
//                        activity.startActivity(new Intent(activity, UserShopCartActivity.class));
//                    }else {
//                        activity.startActivity(new Intent(activity, UserLoginActivity.class));
//                    }
//                    break;
                case R.id.rl_head_shop:
//                    if (LoginUtil.isLogin()){
//                        activity.startActivity(new Intent(activity, UserShopCartActivity.class));
//                    }else {
//                        activity.startActivity(new Intent(activity, UserLoginActivity.class));
//                    }
                    break;
                case R.id.iv_head_search:
//                    activity.startActivity(new Intent(activity, UserSearchActivity.class));
                    break;
            }
        }
    };
    public void setRightTxtOnClickListener(OnClickListener onRightTxtClickListener) {
        if (onRightTxtClickListener != null) {
            tv_head_right.setOnClickListener(onRightTxtClickListener);
        }
    }
    public void setTipsNum(int num) {
        if (num > 0) {
            tv_tip_num.setVisibility(VISIBLE);
            tv_tip_num.setText(String.valueOf(num));
        } else {
            tv_tip_num.setVisibility(GONE);
        }
    }

    public void setHeadGoBackShow(boolean isShow) {
        if (isShow) {
            head_goback.setVisibility(View.VISIBLE);
        } else {
            head_goback.setVisibility(View.GONE);
        }
    }

    public void setIvLeft(int imgId) {
        iv_left.setVisibility(VISIBLE);
        iv_left.setImageResource(imgId);
    }

    public ImageButton getIvLeft() {
        return iv_left;
    }

    public void setHeadSearchShow(boolean isShow) {
        if (isShow) {
            iv_head_search.setVisibility(View.VISIBLE);
        } else {
            iv_head_search.setVisibility(View.GONE);
        }

    }

    public void setHeadShopShow(boolean isShow) {
        if (isShow) {
            rl_head_shop.setVisibility(VISIBLE);
//            head_view_shop.setVisibility(VISIBLE);
        } else {
            rl_head_shop.setVisibility(GONE);
//            head_view_shop.setVisibility(GONE);
        }

    }


    public void setHeadLogoShow(boolean isShow) {
        if (isShow) {
            iv_center_logo.setVisibility(View.VISIBLE);
        } else {
            iv_center_logo.setVisibility(View.GONE);
        }

    }

    public void setHeadCenterTxtShow(boolean isShow, int resId) {
        if (isShow) {
            head_center_tv.setVisibility(View.VISIBLE);
            head_center_tv.setText(resId);
        } else {
            head_center_tv.setVisibility(View.GONE);
        }

    }

    public void setHeadCenterTxtShow(boolean isShow, String string) {
        if (isShow) {
            head_center_tv.setVisibility(View.VISIBLE);
            head_center_tv.setText(string);
        } else {
            head_center_tv.setVisibility(View.GONE);
        }

    }

    public void setHeadRightTxtShow(boolean isShow, int resId) {
        if (isShow) {
            tv_head_right.setVisibility(View.VISIBLE);
            tv_head_right.setText(resId);
        } else {
            tv_head_right.setVisibility(View.GONE);
        }

    }

    public void setHeadRightTxtShow(boolean isShow, String str) {
        if (isShow) {
            tv_head_right.setVisibility(View.VISIBLE);
            tv_head_right.setText(str);
        } else {
            tv_head_right.setVisibility(View.GONE);
        }

    }

    public TextView getHeadRightTV() {
        return tv_head_right;
    }

    /**
     * 背景色
     * @param color color
     */
    public void setBackgroundColor(int color){
        rootView.setBackgroundColor(color);
    }

    /**
     * 中间文字
     * @param string string
     */
    public void setCenterTxtShow(String string) {
        setCenterTxtShow(string, 0);
    }

    public void setCenterTxtShow(String string, int color) {
        head_center_tv.setText(string);
        head_center_tv.setVisibility(View.VISIBLE);
        if (color != 0) {
            head_center_tv.setTextColor(color);
        }
    }

    /**
     * 右边文字
     * @param str str
     */
    public void setRightTxt(String str) {
        setRightTxt(str, 0);
    }

    public void setRightTxt(String str, int color) {
        setRightTxt(str, color, null);
    }

    public void setRightTxt(String str, int color, OnClickListener onClickListener) {
        tv_head_right.setText(str);
        tv_head_right.setVisibility(View.VISIBLE);
        if (color != 0) {
            tv_head_right.setTextColor(color);
        }
        if (onClickListener != null) {
            tv_head_right.setOnClickListener(onClickListener);
        }
    }

    public void setLeftImageButton(int imgId){
        head_goback.setVisibility(VISIBLE);
        head_goback.setImageResource(imgId);
    }
}
