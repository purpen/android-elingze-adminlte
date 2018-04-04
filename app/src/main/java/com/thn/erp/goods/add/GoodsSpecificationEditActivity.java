package com.thn.erp.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;

/**
 * Created by Stephen on 2018/3/29 16:43
 * Email: 895745843@qq.com
 */

public class GoodsSpecificationEditActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayoutSwitchView1)
    LinearLayoutCustomerAddSwitchView linearLayoutSwitchView1;
    @BindView(R.id.linearLayout_container)
    LinearLayout linearLayoutContainer;
    @BindView(R.id.textView_goods_add_specification_add)
    TextView textViewGoodsAddSpecificationAdd;
    @BindView(R.id.textView_goods_add_specification_confirm)
    TextView textViewGoodsAddSpecificationConfirm;

    private boolean isMultiPrice = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add_specification;
    }

    @Override
    protected void initView() {
        initTopbar();
        initLinearLayotContainer();
    }

    private void initLinearLayotContainer() {
        linearLayoutSwitchView1.setInitKeyAndHint("多价格", new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                isMultiPrice = true;
                setMultiPrice(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                isMultiPrice = false;
                setMultiPrice(false);
            }
        }, true);
        SpecificationView specificationView = new SpecificationView(this);
        linearLayoutContainer.addView(specificationView);
        linearLayoutSwitchView1.setEnabled(true);
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("商品规格参数", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.RIGHT:
//                String str = editText1.getText().toString();
//                if (!TextUtils.isEmpty(str)) {
//                    Intent intent = new Intent();
//                    intent.putExtra(GoodsAddActivity.class.getSimpleName(), str);
//                    setResult(-1, intent);
//                    this.finish();
//                }
                break;
        }
    }

    @OnClick({R.id.textView_goods_add_specification_add, R.id.textView_goods_add_specification_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView_goods_add_specification_add:
                addMultiPriceLayout();
                break;
            case R.id.textView_goods_add_specification_confirm:
                break;
        }
    }

    /**
     * 设置多价格
     * @param b isMultiPrice
     */
    private void setMultiPrice(boolean b) {
        for(int i =0 ; i < linearLayoutContainer.getChildCount(); i ++) {
            SpecificationView childAt = (SpecificationView) linearLayoutContainer.getChildAt(i);
            childAt.setMultiPrices(b);
        }
    }

    /**
     * 添加多价格布局
     */
    private void addMultiPriceLayout(){
        SpecificationView specificationView = new SpecificationView(this, isMultiPrice);
        linearLayoutContainer.addView(specificationView);
        linearLayoutSwitchView1.setEnabled(true);
    }
}
