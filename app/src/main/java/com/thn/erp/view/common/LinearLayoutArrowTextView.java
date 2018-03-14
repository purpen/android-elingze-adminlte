package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/10 20:56
 * Email: 895745843@qq.com
 */

public class LinearLayoutArrowTextView extends LinearLayout{
    private TextView textView;

    public LinearLayoutArrowTextView(Context context) {
        this(context, null);
    }

    public LinearLayoutArrowTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutArrowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.include_view_layout_item, this, true);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
    }

    public void setName(String str){
        textView.setText(str);
    }
}
