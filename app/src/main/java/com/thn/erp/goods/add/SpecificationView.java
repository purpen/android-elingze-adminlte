package com.thn.erp.goods.add;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thn.erp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/4/3 18:42
 * Email: 895745843@qq.com
 */

public class SpecificationView extends LinearLayout implements View.OnClickListener {
    private EditText editText1;
    private ImageView imageView1;
    private LinearLayout linearLayoutPrice;
    private EditText editText2;
    private boolean isMultiPrice = true;

    public SpecificationView(Context context) {
        this(context, null);
    }

    public SpecificationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecificationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_goods_add_specificationview, this, true);
        initView(inflate);
    }

    public SpecificationView(Context context, boolean defaultIsMultiPrice) {
        this(context, null);
        this.isMultiPrice = defaultIsMultiPrice;
    }

    private void initView(View inflate) {
        editText1 = (EditText) findViewById(R.id.editText1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        linearLayoutPrice = (LinearLayout) findViewById(R.id.linearLayout_price);
        editText2 = (EditText) findViewById(R.id.editText2);
        imageView1.setOnClickListener(this);
        setMultiPrices(isMultiPrice);
    }

    public String getSpecificationValues() {
        return editText1.getText().toString();
    }

    public String getPriceValues() {
        return editText2.getText().toString();
    }

    public void setMultiPrices(boolean isMultiPrice){
        linearLayoutPrice.setVisibility(isMultiPrice ? VISIBLE : GONE);
        this.isMultiPrice = isMultiPrice;
    }

    @Override
    public void onClick(View view) {
        ViewParent parent1 = getParent();
        if (parent1 instanceof LinearLayout) {
            LinearLayout parent = (LinearLayout) parent1;
            parent.removeView(this);
        }
    }
}
