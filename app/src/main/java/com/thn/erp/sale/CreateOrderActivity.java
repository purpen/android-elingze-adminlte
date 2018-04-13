package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.stephen.taihuoniaolibrary.utils.THNToastUtil;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.adapter.CreateOrderAdapter;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.sale.bean.CreateOrderData;
import com.thn.erp.sale.bean.DefaultAddressData;
import com.thn.erp.sale.bean.FreightData;
import com.thn.erp.sale.bean.OrderGoodsItem;
import com.thn.erp.sale.bean.SKUListData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.CustomItemLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.thn.erp.sale.SelectAddressActivity.REQUEST_ADDRESS_CODE;

/**
 * 创建订单
 */

public class CreateOrderActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.sumPrice)
    TextView sumPrice;
    @BindView(R.id.submitOrder)
    Button submitOrder;
    @BindView(R.id.customName)
    CustomItemLayout customName;
    @BindView(R.id.shopName)
    CustomItemLayout shopName;
    @BindView(R.id.tvFreight)
    TextView tvFreight;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvNoAddressTips)
    TextView tvNoAddressTips;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    //    运费
    private int freight;
    private CreateOrderAdapter adapter;
    private List<SKUListData.DataBean.ItemsBean> list;
    private THNWaittingDialog dialog;
    private DefaultAddressData.DataBean address;

    @Override
    protected void getIntentData() {
        list = new ArrayList<>();
        ArrayList<SKUListData.DataBean.ItemsBean> extra = getIntent().getParcelableArrayListExtra(DXOrderActivity.class.getSimpleName());
        if (null != extra) list.addAll(extra);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_create_order;
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(activity);
        customHeadView.setHeadCenterTxtShow(true, R.string.create_order_title);
        adapter = new CreateOrderAdapter(list);
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

    @OnClick({R.id.submitOrder, R.id.rlAddress})
    void performClick(View view) {
        switch (view.getId()) {
            case R.id.submitOrder:
                createOrder();
                break;
            case R.id.rlAddress:
                startActivityForResult(new Intent(activity, SelectAddressActivity.class), REQUEST_ADDRESS_CODE);
                break;
        }
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        if (null == address) {
            THNToastUtil.showInfo("请选择收货地址");
            return;
        }
        long storeId = 0;
        String addressId = address.rid;
        ArrayList<OrderGoodsItem> items = new ArrayList<>();
        OrderGoodsItem item;
        for (SKUListData.DataBean.ItemsBean itemsBean : list) {
            item = new OrderGoodsItem();
            item.rid = itemsBean.rid;
//            TODO 这是测试数据
            item.rid = "118260884497";
            item.quantity = 1;
            item.deal_price = itemsBean.sale_price;
            items.add(item);
        }
        HashMap params = ClientParamsAPI.createOrderParams(storeId, addressId, freight, items);
        HttpRequest.sendRequest(HttpRequest.POST, URL.ADD_ORDER, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CreateOrderData createOrderData = JsonUtil.fromJson(json, CreateOrderData.class);
                if (createOrderData.success == true) {
                    ToastUtils.showInfo(R.string.add_order_success);
                } else {
                    ToastUtils.showError(createOrderData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    @Override
    protected void requestNet() {
        getFreight();
        getDefaultAddress();
    }

    /**
     * 获取默认地址
     */
    private void getDefaultAddress() {
        HashMap<String, String> params = ClientParamsAPI.getDefaultAddressParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_DEFAULT_ADDRESS, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                DefaultAddressData addressData = JsonUtil.fromJson(json, DefaultAddressData.class);
                if (addressData.success == true) {
                    if (addressData.data == null) return;
                    address = addressData.data;
                    setConsigneeAddress(address);
                } else {
                    ToastUtils.showError(addressData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    /**
     * 设置收货地址Ad
     *
     * @param dataBean
     */
    private void setConsigneeAddress(DefaultAddressData.DataBean dataBean) {
        tvNoAddressTips.setVisibility(View.GONE);
        tvName.setText("收货人：" + dataBean.full_name);
        tvAddress.setText("收货地址：" + dataBean.province + dataBean.city + dataBean.town + dataBean.street_address);
        tvPhone.setText(dataBean.mobile);
    }

    /**
     * 设置收货地址
     *
     * @param dataBean
     */
    private void setConsigneeAddress(AddressData.DataBean dataBean) {
        tvNoAddressTips.setVisibility(View.GONE);
        tvName.setText("收货人：" + dataBean.full_name);
        tvAddress.setText("收货地址：" + dataBean.province + dataBean.city + dataBean.town + dataBean.street_address);
        tvPhone.setText(dataBean.mobile);
    }


    /**
     * 获取运费
     */
    private void getFreight() {
        HashMap<String, String> params = ClientParamsAPI.getFreightParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_FREIGHT, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                FreightData freightData = JsonUtil.fromJson(json, FreightData.class);
                if (freightData.success == true) {
                    freight = freightData.data.freight;
                    tvFreight.setText("运费：￥" + freightData.data.freight);
                } else {
                    ToastUtils.showError(freightData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADDRESS_CODE:
                    AddressData.DataBean dataBean = data.getParcelableExtra(SelectAddressActivity.class.getSimpleName());
                    if (dataBean != null) {
                        setConsigneeAddress(dataBean);
                    }
                    break;
                default:
                    break;

            }
        }
    }
}
