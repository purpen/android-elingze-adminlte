package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

import butterknife.BindView;

/**
 * Created by Stephen on 2018/3/14 16:53
 * Email: 895745843@qq.com
 */

public class LinearLayoutCustomerAddEditTextView extends LinearLayout {

    private TextView textView1;
    private EditText editText1;

    public LinearLayoutCustomerAddEditTextView(Context context) {
        this(context, null);
    }

    public LinearLayoutCustomerAddEditTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCustomerAddEditTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_linearlayout_customer_add_edit, this, true);
        initView();
    }

    private void initView() {
        textView1 = (TextView) findViewById(R.id.textView1);
        editText1 = (EditText) findViewById(R.id.editText1);
    }

//    public void setInitKey(String str1) {
//        textView1.setText(str1);
//    }

    public void setInitKeyAndHint(String str1, String str2) {
        textView1.setText(str1);
        editText1.setHint(str2);
    }


    public void setName(String str) {
        textView1.setText(str);
    }

    public void setValue(String str) {
        editText1.setText(str);
    }
}
