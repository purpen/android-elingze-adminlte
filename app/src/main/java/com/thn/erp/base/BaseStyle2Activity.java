package com.thn.erp.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.stephen.taihuoniaolibrary.utils.THNStatusBarUtils;
import com.thn.erp.R;

/**
 * Created by Stephen on 2018/3/21 16:52
 * Email: 895745843@qq.com
 */

public abstract class BaseStyle2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.THN_color_bgColor_content)));
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        THNStatusBarUtils.chenjin(this, R.color.THN_color_bgColor_topbar);
    }
}
