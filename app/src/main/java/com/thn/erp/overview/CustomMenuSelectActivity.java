package com.thn.erp.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.overview.adapter.CustomMainMenuAdapter;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;

import butterknife.BindView;

public class CustomMenuSelectActivity extends BaseActivity {

    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CustomMenuBean> list;
    private CustomMainMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_custom_menu_select;
    }

    @Override
    protected void initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.custom_main_menu_title);
        customHeadView.setHeadRightTxtShow(true,R.string.confirm);
        int length = menuTitles.length;
        ArrayList<CustomMenuBean> list = new ArrayList<>();
        CustomMenuBean bean;
        for (int i = 0; i < length; i++) {
            bean = new CustomMenuBean();
            bean.pos = i;
            bean.selected = false;
            bean.iconId = menuIcons[i];
            bean.title = menuTitles[i];
            list.add(bean);
        }

        adapter = new CustomMainMenuAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.disableLoadmore();
        ultimateRecyclerView.setAdapter(adapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/5/24  更新数据库
                finish();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                int size = list.size();
                if (size == 0) return;
                CustomMenuBean bean = list.get(i);
                if (bean.selected) {
                    bean.selected = false;
                } else {
                    bean.selected = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static final String[] menuTitles = {"经营概况","销售单","销售退货单","进销对比","采购单","采购退货单", "商品管理", "人员管理", "客户管理","供应商管理","出库单历史","入库单历史","库存查询","入库报表","出库报表","物流管理"};
    public static final int[] menuIcons = {
            R.mipmap.icon_menu_status,
            R.mipmap.icon_menu_sale_order,
            R.mipmap.icon_menu_sales_return,
            R.mipmap.icon_menu_import_export_compare,
            R.mipmap.icon_menu_purchase_order,
            R.mipmap.icon_menu_purchase_return,
            R.mipmap.icon_menu_goods_manage,
            R.mipmap.icon_menu_person_manage,
            R.mipmap.icon_menu_customer_manage,
            R.mipmap.icon_menu_supplier_manage,
            R.mipmap.icon_menu_outgoing_history,
            R.mipmap.icon_menu_warehousing_history,
            R.mipmap.icon_menu_inventory_query,
            R.mipmap.icon_menu_warehousing_report,
            R.mipmap.icon_menu_outgoing_report,
            R.mipmap.icon_menu_logistics_manage,
    };
}
