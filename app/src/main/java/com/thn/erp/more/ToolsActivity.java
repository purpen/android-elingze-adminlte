package com.thn.erp.more;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.more.adapter.ToolsAdapter;
import com.thn.erp.view.common.PublicTopBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Stephen on 2018/3/21 15:22
 * Email: 895745843@qq.com
 */

public class ToolsActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;

    private static final String[] STOCK_ITEMS = {"扫码出库", "发货核验", "扫码入库", "扫码盘点"};
    private static final int[] STOCK_ICON_ITEMS = {R.mipmap.icon_out_storehouse,R.mipmap.icon_check_delivery, R.mipmap.icon_in_storehouse,R.mipmap.icon_scan_code};
    private static final String[] ORDER_ITEMS = {"扫码下单"};
    private static final int[] ORDER_ICON_ITEMS = {R.mipmap.icon_submit_order};

    @Override
    protected int getLayout() {
        return R.layout.activity_more_tools;
    }

    @Override
    protected void initView() {
        initTopBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        initStorehouseToolRecycleView();
        initOrderToolRecycleView();
    }

    private void initOrderToolRecycleView() {
        ArrayList<ItemBean> orderToolData = new ArrayList<>();
        ItemBean itemBean;
        for (int i=0;i<ORDER_ITEMS.length;i++){
            itemBean=new ItemBean();
            itemBean.resId = ORDER_ICON_ITEMS[i];
            itemBean.title = ORDER_ITEMS[i];
            orderToolData.add(itemBean);
        }
        ToolsAdapter adapter2 = new ToolsAdapter(orderToolData, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0: //扫码下单
                        startActivity(new Intent(ToolsActivity.this, PrepareOrderingActivity.class));
                        break;
                }
            }
        });
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(adapter2);
    }


    private void initStorehouseToolRecycleView() {
        int size=STOCK_ITEMS.length;
        ArrayList<ItemBean> storeHouseData = new ArrayList<>();
        ItemBean itemBean;
        for (int i=0;i<size;i++){
           itemBean = new ItemBean();
           itemBean.title = STOCK_ITEMS[i];
           itemBean.resId = STOCK_ICON_ITEMS[i];
           storeHouseData.add(itemBean);
        }
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ToolsAdapter adapter1 = new ToolsAdapter(storeHouseData, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0: // 扫码出库
                        startActivity(new Intent(ToolsActivity.this, PrepareExportStockActivity.class));
                        break;
                }
            }
        });
        recyclerView1.setAdapter(adapter1);
    }

    private void initTopBar() {
        publicTopBar.setTopBarCenterTextView("工具箱", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }


    public static class ItemBean{
        public String title;
        public int resId;
    }
}
