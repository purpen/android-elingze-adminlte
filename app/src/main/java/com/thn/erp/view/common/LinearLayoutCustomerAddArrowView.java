package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/14 16:53
 * Email: 895745843@qq.com
 */

public class LinearLayoutCustomerAddArrowView extends LinearLayout {

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView1;

    public LinearLayoutCustomerAddArrowView(Context context) {
        this(context, null);
    }

    public LinearLayoutCustomerAddArrowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCustomerAddArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_linearlayout_customer_add_arrow, this, true);
        initView();
    }

    private void initView() {

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }


    public void setInitKeyAndHint(String str1, OnClickListener onClickListener) {
        setInitKeyAndHint(str1, "请选择", onClickListener, R.mipmap.icon_more_other_arrow);
    }

    public void setInitKeyAndHint(String str1, String str2, OnClickListener onClickListener, int imageid) {
        textView1.setText(str1);
        textView2.setHint(str2);
        imageView1.setImageResource(imageid);
        setOnClickListener(onClickListener);
    }

    public void setValue(String str){
        textView2.setText(str);
    }

    public CharSequence getValue() {
        return textView2.getText();
    }
}
