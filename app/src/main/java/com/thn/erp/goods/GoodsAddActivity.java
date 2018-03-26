package com.thn.erp.goods;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.view.common.LinearLayoutCustomerAddArrowView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/26 22:33
 * Email: 895745843@qq.com
 */

public class GoodsAddActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.layoutItemView1)
    LinearLayoutCustomerAddArrowView layoutItemView1;
    @BindView(R.id.layoutItemView2)
    LinearLayoutCustomerAddArrowView layoutItemView2;
    @BindView(R.id.layoutItemView3)
    LinearLayoutCustomerAddArrowView layoutItemView3;
    @BindView(R.id.layoutItemView4)
    LinearLayoutCustomerAddArrowView layoutItemView4;
    @BindView(R.id.layoutItemView5)
    LinearLayoutCustomerAddArrowView layoutItemView5;
    @BindView(R.id.layoutItemView6)
    LinearLayoutCustomerAddArrowView layoutItemView6;
    @BindView(R.id.layoutItemView7)
    LinearLayoutCustomerAddArrowView layoutItemView7;
    @BindView(R.id.layoutItemView8)
    LinearLayoutCustomerAddArrowView layoutItemView8;
    @BindView(R.id.layoutItemView9)
    LinearLayoutCustomerAddSwitchView layoutItemView9;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add;
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
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("添加商品", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }
}
