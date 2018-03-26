package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

import butterknife.BindView;

/**
 * Created by Stephen on 2018/3/10 20:56
 * Email: 895745843@qq.com
 */

public class LinearLayoutCommonTextView extends LinearLayout {
    private TextView textView1;
    private TextView textView2;


    public LinearLayoutCommonTextView(Context context) {
        this(context, null);
    }

    public LinearLayoutCommonTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCommonTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_linearlayout_item_common_textview, this, true);
        initView();
    }

    private void initView() {
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
    }

    public void setName(String str) {
        textView1.setText(str);
    }

    public void setValue(String str){
        textView2.setText(str);
    }
}
