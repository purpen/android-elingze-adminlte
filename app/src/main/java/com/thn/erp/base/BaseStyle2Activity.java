package com.thn.erp.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/21 16:52
 * Email: 895745843@qq.com
 */

public abstract class BaseStyle2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.THN_color_bgColor_content));
        getWindow().setBackgroundDrawable(colorDrawable);
    }
}
