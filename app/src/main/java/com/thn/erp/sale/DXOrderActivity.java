package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.overview.usermanage.bean.CustomerData;
import com.thn.erp.sale.adapter.DXOrderGoodsAdapter;
import com.thn.erp.sale.bean.SKUListData;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;

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
    @BindView(R.id.etCustomer)
    EditText etCustomer;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.submitOrder)
    Button submitOrder;

    private DXOrderGoodsAdapter adapter;
    private ArrayList<SKUListData.DataBean.ItemsBean> list;
    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_dx_order;
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(this);
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

    @OnClick({R.id.selectBtn,R.id.selectGoodsBtn,R.id.submitOrder})
    void performClick(View view){
        switch (view.getId()){
            case R.id.selectBtn:
                startActivityForResult(new Intent(activity,SelectCustomerActivity.class),REQUEST_CUSTOMER_CODE);
                break;
            case R.id.selectGoodsBtn:
                startActivityForResult(new Intent(activity,SelectGoodsActivity.class),REQUEST_GOODS_CODE);
                break;
            case R.id.submitOrder:
                if (TextUtils.isEmpty(etCustomer.getText())){
                    ToastUtil.showInfo("请选择客户名");
                    return;
                }

                if (list.size()==0){
                    ToastUtil.showInfo("请选择商品");
                    return;
                }

                String perPrice = adapter.getPerPrice();
                if (TextUtils.isEmpty(perPrice)){
                    ToastUtil.showInfo("请输入单价");
                    return;
                }

                int quantity = adapter.getQuantity();

                if (quantity==0){
                    ToastUtil.showInfo("请输入数量");
                    return;
                }

                Intent intent = new Intent(activity, CreateOrderActivity.class);
                intent.putParcelableArrayListExtra(DXOrderActivity.class.getSimpleName(),list);
                startActivity(intent);
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
                    SKUListData.DataBean.ItemsBean dataBean = data.getParcelableExtra(SKUListData.class.getSimpleName());
                    adapter.insert(dataBean,adapter.getAdapterItemCount());
                    break;
                case REQUEST_CUSTOMER_CODE:
                    CustomerData.DataBean.CustomersBean customersBean = data.getParcelableExtra(CustomerData.class.getSimpleName());
                    etCustomer.setText(customersBean.name);
                    break;
            }
        }
    }
}
