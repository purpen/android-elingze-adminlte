package com.thn.erp.more;

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

public class ToolsActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayout_more_stock_export)
    LinearLayout linearLayoutMoreStockExport;
    @BindView(R.id.linearLayout_more_scan_order)
    LinearLayout linearLayoutMoreScanOrder;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_tools;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopBar();
    }

    private void initTopBar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("工具箱", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
//        publicTopBar.setTopBarRightTextView("编辑", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }

    @OnClick({R.id.linearLayout_more_stock_export, R.id.linearLayout_more_scan_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearLayout_more_stock_export:
                startActivity(new Intent(ToolsActivity.this, PrepareExportStockActivity.class));
                break;
            case R.id.linearLayout_more_scan_order:
                startActivity(new Intent(ToolsActivity.this, PrepareOrderingActivity.class));
                break;
        }
    }
}
