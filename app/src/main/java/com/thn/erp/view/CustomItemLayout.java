package com.thn.erp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thn.erp.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author lilin
 * created at 2016/4/11 16:58
 */
public class CustomItemLayout extends RelativeLayout {
    private TextView tv_content;
    private BadgeView tv_tip_num;
    private TextView tv_right_txt;
    private ImageView iv_more_arrow;
    private EditText et_right;
    private TextView tv_arrow_left;
    private TextView tv_arrow_left1;
    private CircleImageView iv_user_avatar;
    private RelativeLayout rl_item_box;
    private ImageView iv_arrow_left;
    public CustomItemLayout(Context context) {
        this(context,null);
    }

    public CustomItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflatelayout(context);
    }

    private void inflatelayout(Context context){
        View view = View.inflate(context,R.layout.custom_item_layout, this);
        rl_item_box = (RelativeLayout) view.findViewById(R.id.rl_item_box);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_tip_num = (BadgeView) view.findViewById(R.id.tv_tip_num);
        iv_more_arrow = (ImageView) view.findViewById(R.id.iv_more_arrow);
        tv_right_txt = (TextView) view.findViewById(R.id.tv_right_txt);
        et_right = (EditText) view.findViewById(R.id.et_right);
        tv_arrow_left = (TextView) view.findViewById(R.id.tv_arrow_left);
        tv_arrow_left1 = (TextView) view.findViewById(R.id.tv_arrow_left1);
        iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
        iv_arrow_left = (ImageView) view.findViewById(R.id.iv_bar_code);
    }

    public void setRightMoreImgStyle(boolean isShow){
        if (isShow){
            iv_more_arrow.setVisibility(VISIBLE);
        }else{
            iv_more_arrow.setVisibility(GONE);
        }
    }

    public void setIvArrowLeftShow(boolean isShow){
        if (isShow){
            iv_arrow_left.setVisibility(VISIBLE);
        }else {
            iv_arrow_left.setVisibility(GONE);
        }
    }
    public CircleImageView getAvatarIV(){
        return iv_user_avatar;
    }

    public void setUserAvatar(Bitmap bitmap){
        iv_user_avatar.setVisibility(VISIBLE);
        if(bitmap!=null){
            iv_user_avatar.setImageBitmap(bitmap);
        }
    }
    public void setTvArrowLeftStyle(boolean isShow,int resId){
        if (isShow){
            tv_arrow_left.setVisibility(VISIBLE);
            tv_arrow_left.setText(resId);
        }else{
            tv_arrow_left.setVisibility(GONE);
        }
    }

    public void setTvArrowLeft1Style(boolean isShow,int resId){
        if (isShow){
            tv_arrow_left1.setVisibility(VISIBLE);
            tv_arrow_left1.setText(resId);
        }else{
            tv_arrow_left1.setVisibility(GONE);
        }
    }
    public void setTvArrowLeft1Style(boolean isShow, String txt, int color){
        if (isShow){
            tv_arrow_left1.setVisibility(VISIBLE);
            tv_arrow_left1.setText(txt);
            tv_arrow_left1.setTextColor(getResources().getColor(color));
        }else{
            tv_arrow_left1.setVisibility(GONE);
        }
    }
    public void setTvArrowLeftStyle(boolean isShow, String txt, int color){
        if (isShow){
            tv_arrow_left.setVisibility(VISIBLE);
            tv_arrow_left.setText(txt);
            tv_arrow_left.setTextColor(getResources().getColor(color));
        }else{
            tv_arrow_left.setVisibility(GONE);
        }
    }
    public String getTvarrowLeftTxt(){
        return  tv_arrow_left.getText().toString();
    }

    public String getTvarrowLeft1Txt(){
        return  tv_arrow_left1.getText().toString();
    }

    public void sertTVRightTxt(String txt){
        if (!TextUtils.isEmpty(txt)){
            tv_right_txt.setVisibility(VISIBLE);
            tv_right_txt.setText(txt);
        }
    }
    public void setTVRightTxt(String txt, int txtColor){
        if (!TextUtils.isEmpty(txt)){
            tv_right_txt.setVisibility(VISIBLE);
            tv_right_txt.setText(txt);
            tv_right_txt.setTextColor(getResources().getColor(txtColor));
        }
    }


    public void setRightETStyle(int hint,int hintColor,int inputType,boolean isShow){
        if (isShow){
            et_right.setVisibility(VISIBLE);
        }else {
            et_right.setVisibility(GONE);
        }
        et_right.setHint(hint);
        et_right.setHintTextColor(getResources().getColor(hintColor));
        et_right.setInputType(inputType);
    }

    public EditText getETRight(){
        return et_right;
    }

    public void setEditTextContent(String content){
        et_right.setText(content);
    }

    public String getRightETTxt(){
        return et_right.getText().toString();
    }

    public void setTVStyle(int drawableLeft,int strId,int txtColor){
        tv_content.setText(strId);
        tv_content.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
        tv_content.setTextColor(getResources().getColor(txtColor));
    }
//    public void setTVStyle(int drawableRight,String str,int txtColor){
//        tv_content.setText(str);
//        tv_content.setCompoundDrawablesWithIntrinsicBounds(0, 0,drawableRight, 0);
//        tv_content.setTextColor(getResources().getColor(txtColor));
//    }


    public void setTVStyle(int drawableLeft, String str, int txtColor){
        tv_content.setText(str);
        tv_content.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
        tv_content.setTextColor(getResources().getColor(txtColor));
    }

    public void setTipsNum(int count){
        if (count > 0) {
            tv_tip_num.setVisibility(View.VISIBLE);
            tv_tip_num.setBackground(15, getResources().getColor(R.color.color_ff5a5f));
            tv_tip_num.setBadgeCount(count);
        } else {
            tv_tip_num.setVisibility(View.GONE);
        }
    }

    public void setHeight(int dimenId){
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(dimenId));
        rl_item_box.setLayoutParams(lp);
    }

    public TextView getTVContent(){
        if (tv_content!=null){
            return tv_content;
        }else {
            return null;
        }
    }
}
