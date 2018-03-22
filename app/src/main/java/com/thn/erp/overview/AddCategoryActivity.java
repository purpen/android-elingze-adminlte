package com.thn.erp.overview;

import android.graphics.Color;
import android.os.Bundle;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/21 9:48
 * Email: 895745843@qq.com
 */

public class AddCategoryActivity extends BaseStyle2Activity {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_add_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopbar();
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        publicTopBar.setTopBarCenterTextView("添加分类", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("确认", Color.parseColor("#27AE59"));
    }
}
