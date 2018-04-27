package com.thn.erp.overview;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/3/14 10:43
 * Email: 895745843@qq.com
 */

public class CustomerManageActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayout_customer_search)
    LinearLayout linearLayoutCustomerSearch;

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_ganage;
    }

    @Override
    protected void initView() {
        publicTopBar.setTopBarCenterTextView("客户管理", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("添加客户", getResources().getColor(R.color.white));
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
                Toast.makeText(activity, "添加客户", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AddCustomerActivity.class));
                break;
        }
    }

    @OnClick(R.id.linearLayout_customer_search)
    public void onViewClicked() {
        Toast.makeText(activity, "客户搜索", Toast.LENGTH_SHORT).show();
    }
}
