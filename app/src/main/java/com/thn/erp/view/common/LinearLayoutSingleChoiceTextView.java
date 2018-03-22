package com.thn.erp.view.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/20 15:25
 * Email: 895745843@qq.com
 */

public class LinearLayoutSingleChoiceTextView extends LinearLayout {
    private TextView textView;
    private CheckBox checkbox1;

    public LinearLayoutSingleChoiceTextView(Context context) {
        this(context, null);
    }

    public LinearLayoutSingleChoiceTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutSingleChoiceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_linearlayout_item_single_choice_textview, this, true);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
    }

    public void setName(String str){
        textView.setText(str);
    }


    public void setName(String str, boolean defaultChecked){
        textView.setText(str);
    }

    public void setName(String str, boolean defaultChecked, CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        textView.setText(str);
        checkbox1.setOnCheckedChangeListener(onCheckedChangeListener);
        checkbox1.setChecked(defaultChecked);
    }
}
