package com.thn.erp.more.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/3/21 15:22
 * Email: 895745843@qq.com
 */

public class SupplierManageActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayout_customer_search)
    LinearLayout linearLayoutCustomerSearch;
    @BindView(R.id.linearLayout_supplier1)
    LinearLayout linearLayoutSupplier1;
    @BindView(R.id.linearLayout_supplier2)
    LinearLayout linearLayoutSupplier2;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_manage_supplier;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        publicTopBar.setTopBarCenterTextView("供应商管理", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("新增供应商", getResources().getColor(R.color.white));
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.LEFT:
                this.finish();
                break;
            case ImpTopbarOnClickListener.CENTER:
                break;
            case ImpTopbarOnClickListener.RIGHT:
                startActivity(new Intent(this, SupplierAddActivity.class));
                break;
        }
    }

    @OnClick({R.id.linearLayout_supplier1, R.id.linearLayout_supplier2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayout_supplier1:
            case R.id.linearLayout_supplier2:
                startActivity(new Intent(activity, SupplierLookActivity.class));
                break;
        }
    }
}
