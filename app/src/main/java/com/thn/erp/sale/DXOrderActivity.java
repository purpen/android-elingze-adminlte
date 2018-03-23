package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.sale.adapter.DXOrderGoodsAdapter;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 代下单
 */

public class DXOrderActivity extends BaseActivity {
    public static final int REQUEST_GOODS_CODE = 0x000010;
    public static final int REQUEST_CUSTOMER_CODE = 0x000011;
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.submitOrder)
    Button submitOrder;

    private DXOrderGoodsAdapter adapter;
    private List<GoodsData.DataBean.ProductsBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_dx_order;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        customHeadView.setHeadCenterTxtShow(true, R.string.dx_order_title);
        adapter = new DXOrderGoodsAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.disableLoadmore();
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.WHITE);
        ultimateRecyclerView.setAdapter(adapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {

    }

    @OnClick({R.id.selectBtn,R.id.selectGoodsBtn})
    void performClick(View view){
        switch (view.getId()){
            case R.id.selectBtn:
                startActivityForResult(new Intent(activity,SelectCustomerActivity.class),REQUEST_GOODS_CODE);
                break;
            case R.id.selectGoodsBtn:
                startActivityForResult(new Intent(activity,SelectGoodsActivity.class),REQUEST_GOODS_CODE);
                break;
        }
    }
    @Override
    protected void requestNet() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GOODS_CODE:
                    GoodsData.DataBean.ProductsBean goods = (GoodsData.DataBean.ProductsBean) data.getParcelableExtra(GoodsData.class.getSimpleName());
                    adapter.insert(goods,adapter.getAdapterItemCount());
                    break;
            }
        }
    }
}
