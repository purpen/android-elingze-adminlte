package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/14 16:53
 * Email: 895745843@qq.com
 */

public class LinearLayoutCustomerAddEditTextView extends LinearLayout {

    private TextView viewLinearlayoutCustomerAddEditTextView;
    private EditText viewLinearlayoutCustomerAddEditEditText;

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
        viewLinearlayoutCustomerAddEditTextView = (TextView) findViewById(R.id.view_linearlayout_customer_add_edit_textView);
        viewLinearlayoutCustomerAddEditEditText = (EditText) findViewById(R.id.view_linearlayout_customer_add_edit_editText);
    }

//    public void setInitKey(String str1) {
//        textView1.setText(str1);
//    }

    public void setInitKeyAndHint(String str1, String str2) {
        viewLinearlayoutCustomerAddEditTextView.setText(str1);
        viewLinearlayoutCustomerAddEditEditText.setHint(str2);
    }

    public void setInitKeyAndValue(String str1, String str2) {
        viewLinearlayoutCustomerAddEditTextView.setText(str1);
        viewLinearlayoutCustomerAddEditEditText.setText(str2);
    }
    public void setName(String str) {
        viewLinearlayoutCustomerAddEditTextView.setText(str);
    }

    public void setValue(String str) {
        viewLinearlayoutCustomerAddEditEditText.setText(str);
    }

    public void setEnabled(boolean enabled){
        viewLinearlayoutCustomerAddEditEditText.setFocusable(enabled);
        viewLinearlayoutCustomerAddEditEditText.setFocusableInTouchMode(enabled);
        viewLinearlayoutCustomerAddEditEditText.setEnabled(enabled);
        if (enabled) {
            viewLinearlayoutCustomerAddEditEditText.requestFocus();
        }
    }

    public String getValue(){
        return viewLinearlayoutCustomerAddEditEditText.getText().toString();
    }
}
