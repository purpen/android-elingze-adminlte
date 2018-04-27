package com.thn.erp.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.base.BaseUltimateViewAdapter;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.more.adapter.PrepareExportStockAdapter;
import com.thn.erp.more.bean.PrepareExportStockBean;
import com.thn.erp.view.common.PublicTopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/4/16 19:04
 * Email: 895745843@qq.com
 */

public class PrepareExportStockActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener, View.OnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;
    private BaseUltimateViewAdapter mBaseUltimateViewAdapter;
    private List<PrepareExportStockBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_tool_export_prepare;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        list = generateList();
        setLinearLayoutManagerVertical(ultimateRecyclerView);
        mBaseUltimateViewAdapter = new PrepareExportStockAdapter(PrepareExportStockActivity.this, list, this);
        ultimateRecyclerView.setAdapter(mBaseUltimateViewAdapter);
    }

    private void initTopBar() {
        publicTopBar.setTopBarCenterTextView("待出库订单", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }

    /**
     * 生成假数据
     * @return list
     */
    private List<PrepareExportStockBean> generateList(){
        List<PrepareExportStockBean> list = new ArrayList<>();
        PrepareExportStockBean bean1 = new PrepareExportStockBean("DH-O-2151129-008737", "佛山市卓越电子科技有限公司", "5077.10","商品 6 数量 29", "2018-04-17 18:20:02");
        PrepareExportStockBean bean2 = new PrepareExportStockBean("DH-O-2151129-008737", "深圳市大荣立伟业科技有限公司", "1038477.84","商品 5 数量 2113", "2018-04-17 18:22:05");
        PrepareExportStockBean bean3 = new PrepareExportStockBean("DH-O-2151129-008737", "郑州中鼎泰达科技有线公司", "784521.26","商品 7 数量 842", "2018-04-17 18:24:06");
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_stock_order_export:
                PrepareExportStockBean bean = (PrepareExportStockBean) view.getTag(R.id.textView_stock_order_export);
                Intent intent = new Intent(PrepareExportStockActivity.this, ScanExportStockActivity.class);
                intent.putExtra(ExtraKey.PARCElABLE, bean);
//                ToastUtils.showInfo("出库" + bean.toString());
                startActivity(intent);
                break;
        }
    }
}
