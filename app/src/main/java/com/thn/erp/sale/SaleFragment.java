package com.thn.erp.sale;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.goods.TitleRecyclerViewAdapter;
import com.thn.erp.view.common.PublicTopBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by lilin on 2018/3/7.
 */

public class SaleFragment extends BaseFragment {
    @BindView(R.id.publicTopBar)
    PublicTopBar myTopbar;
    @BindView(R.id.ry_menu_item)
    RecyclerView ryMenuItem;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private TitleRecyclerViewAdapter titleRecyclerViewAdapter;
    @Override
    protected int getLayout() {
        return R.layout.fragment_sale;
    }


    @Override
    protected void initView() {
        myTopbar.setTopBarCenterTextView("销售", getResources().getColor(R.color.THN_color_bgColor_white));
        initRecyclerView();
    }

    @Override
    protected void requestNet() {
        super.requestNet();
    }

    @Override
    protected void installListener() {
        titleRecyclerViewAdapter.setOnItemClickListener(new TitleRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onClick(View view, int i) {
                startActivity(new Intent(activity,DXOrderActivity.class));
            }
        });
    }

    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ryMenuItem.setLayoutManager(linearLayoutManager);
        titleRecyclerViewAdapter = new TitleRecyclerViewAdapter(getActivity(), generateAdapterDatas(),true);
        ryMenuItem.setAdapter(titleRecyclerViewAdapter);
    }

    private List<Map<String, Object>> generateAdapterDatas(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < ITEMS.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", IMGS[i]);
            map.put("name", ITEMS[i]);
            list.add(map);
        }
        return list;
    }

    public static final String[] ITEMS = {"客户", "查询", "扫码出库", "代下单"};
    public static final int[] IMGS = {R.mipmap.icon_customer_btn, R.mipmap.icon_search_btn,R.mipmap.icon_scan_btn, R.mipmap.icon_order_btn};
}