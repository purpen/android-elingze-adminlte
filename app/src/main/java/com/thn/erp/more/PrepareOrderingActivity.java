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
import com.thn.erp.more.adapter.PrepareOrderingAdapter;
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

public class PrepareOrderingActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener, View.OnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;
    private BaseUltimateViewAdapter mBaseUltimateViewAdapter;
    private List<PrepareExportStockBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_tool_scan_order;
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

    private void initTopBar() {
        publicTopBar.setTopBarCenterTextView("选择客户", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    private void initRecyclerView() {
        list = generateList();
        setLinearLayoutManagerVertical(ultimateRecyclerView);
        mBaseUltimateViewAdapter = new PrepareOrderingAdapter(PrepareOrderingActivity.this, list, this);
        ultimateRecyclerView.setAdapter(mBaseUltimateViewAdapter);
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
        PrepareExportStockBean bean1 = new PrepareExportStockBean("陕西黄金先机电科技有限公司", "一级代理", "广东省佛山市顺德区");
        PrepareExportStockBean bean2 = new PrepareExportStockBean("无锡市邦邦科技有限公司", "二级代理", "江苏省常州市新北区");
        PrepareExportStockBean bean3 = new PrepareExportStockBean("山东德润北机电设备制造有限公司", "三级代理", "广东省东莞市长安镇");
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
                Intent intent = new Intent(PrepareOrderingActivity.this, ScanOrderingActivity.class);
                intent.putExtra(ExtraKey.PARCElABLE, bean);
//                ToastUtils.showInfo("出库" + bean.toString());
                startActivity(intent);
                break;
        }
    }
}
