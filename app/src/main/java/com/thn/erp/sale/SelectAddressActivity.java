package com.thn.erp.sale;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.layoutmanagers.ScrollSmoothLineaerLayoutManager;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeItemManagerInterface;
import com.thn.erp.R;
import com.thn.erp.base.BasicFunctions;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.adapter.AddressListAdapter;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 选择地址
 */
public class SelectAddressActivity extends BasicFunctions{

    public static final int REQUEST_ADDRESS_CODE = 0x000010;
    private ScrollSmoothLineaerLayoutManager mLayoutManager;
//    @BindView(R.id.customHeadView)
//    CustomHeadView customHeadView;
//    @BindView(R.id.ultimateRecyclerView)
//    UltimateRecyclerView ultimateRecyclerView;
//    @BindView(R.id.addAddress)
//    LinearLayout addAddress;

    private List<AddressData.DataBean> list = new ArrayList<>();
    private AddressListAdapter adapter;
    //网络请求
    private int currentPage = 1;
    private String addressId;
    private LinearLayoutManager linearLayoutManager;
    private boolean isRefreshing = false;


    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            addressId = intent.getStringExtra(TAG);
        }
    }

    protected void initView() {
        super.initView();
//        customHeadView.setHeadCenterTxtShow(true, R.string.select_address_title);
//        customHeadView.setHeadRightTxtShow(true,R.string.add_new_address);
//        dialog = new THNWaittingDialog(this);
//        linearLayoutManager = new LinearLayoutManager(this);
//        adapter = new AddressListAdapter(SelectAddressActivity.this, list,addressId);
//        ultimateRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void requestNet() {
        getAddressList();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (listViewAdapter != null) {
//            listViewAdapter.setAddressId(addressId);
//            listViewAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    protected void installListener() {
//        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SelectAddressActivity.this, AddNewAddressActivity.class);
//                startActivityForResult(intent, DataConstants.REQUESTCODE_ADDNEWADDRESS);
//            }
//        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case REQUEST_ADDRESS_CODE:
                int result = data.getIntExtra("address", 0);
                if (result == 1) {
                    list.clear();
                    currentPage = 1;
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    getAddressList();
                }
                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).isSelected) {
//                intent.putExtra("addressBean", list.get(i));
//                break;
//            }
//        }
//        setResult(DataConstants.REQUESTCODE_ADDRESS, intent);
//        super.onBackPressed();
//    }

    public void deleteAddress(String id) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
//        HashMap<String, String> params = ClientDiscoverAPI.getdeleteAddressNetRequestParams(id);
//        HttpRequest.post(params, URL.URLSTRING_DELETE_ADDRESS, new GlobalDataCallBack() {
//            @Override
//            public void onSuccess(String json) {
//                HttpResponse netBean = JsonUtil.fromJson(json, HttpResponse.class);
//                if (netBean.isSuccess()) {
//                    ToastUtils.showSuccess("删除成功");
//                    currentPage = 1;
//                    getAddressList(currentPage + "");
//                } else {
//                    dialog.dismiss();
//                    ToastUtils.showError(netBean.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                dialog.dismiss();
//                progressBar.setVisibility(View.GONE);
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
    }

    //获得收货地址列表
    private void getAddressList() {
        HashMap<String, String> params = ClientParamsAPI.getAddressListParams();
        HttpRequest.sendRequest(HttpRequest.GET,URL.ADDRESS_LIST,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }
            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                AddressData addressData = JsonUtil.fromJson(json,AddressData.class);
                if (addressData.success == true) {
                    updateData(addressData.data);
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

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onLoadmore() {

    }

    @Override
    protected void doURV(UltimateRecyclerView urv) {
        AddressData.DataBean bean = new AddressData.DataBean();
        bean.full_name="hahhahhha";
        list.add(bean);
        adapter = new AddressListAdapter(SelectAddressActivity.this,list,"");
        adapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mLayoutManager = new ScrollSmoothLineaerLayoutManager(this, LinearLayoutManager.VERTICAL, false, 500);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(mLayoutManager);
        ultimateRecyclerView.setAdapter(adapter);
    }

    private void updateData(List<AddressData.DataBean> dataBeans) {
        if (isRefreshing) {
            adapter.insert(dataBeans);
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            adapter.insertLast(dataBeans.get(0));
        }
    }
}
