package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.DataConstants;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.adapter.AddressListAdapter;
import com.thn.erp.sale.bean.AddressData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.view.CustomHeadView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择地址
 */
public class SelectAddressActivity extends BaseActivity{

    public static final int REQUEST_ADDRESS_CODE = 0x000010;
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.addAddress)
    LinearLayout addAddress;
    private int page=1;

    private List<AddressData.DataBean> list = new ArrayList<>();
    private AddressListAdapter adapter;
    //网络请求
    private WaitingDialog dialog;
    private LinearLayoutManager linearLayoutManager;
    private boolean isRefreshing = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();

    }

    protected void initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.select_address_title);
        customHeadView.setHeadRightTxtShow(true,R.string.manage_address);
        dialog = new WaitingDialog(this);
        adapter = new AddressListAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.WHITE);
        ultimateRecyclerView.setEmptyView(
                R.layout.empty_view,
                UltimateRecyclerView.EMPTY_CLEAR_ALL,
                UltimateRecyclerView.STARTWITH_ONLINE_ITEMS);
        ultimateRecyclerView.reenableLoadmore();
        ultimateRecyclerView.setAdapter(adapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
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
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity,ManageAddressActivity.class));
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getAddressList();
            }
        });

        adapter.setOnItemClickListener(new AddressListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra(SelectAddressActivity.class.getSimpleName(),list.get(i));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                page++;
                getAddressList();
            }
        });
    }

    @OnClick(R.id.addAddress)
    void performClick(View v){
        switch (v.getId()){
            case R.id.addAddress:
                Intent intent = new Intent(SelectAddressActivity.this, AddNewAddressActivity.class);
                startActivityForResult(intent, DataConstants.REQUESTCODE_ADDNEWADDRESS);
                break;
            default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case REQUEST_ADDRESS_CODE:

                break;
        }
    }

    //获得收货地址列表
    private void getAddressList() {
        HashMap<String, String> params = ClientParamsAPI.getAddressListParams();
        HttpRequest.sendRequest(HttpRequest.GET,URL.ADDRESS_LIST,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
               if (!isRefreshing) dialog.show();
            }
            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                AddressData addressData = JsonUtil.fromJson(json,AddressData.class);
                if (addressData.success == true) {
                    List<AddressData.DataBean> data = addressData.data;
                    if (data.size()==0) ultimateRecyclerView.disableLoadmore();
                    updateData(addressData.data);
                } else {
                    ToastUtil.showError(addressData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<AddressData.DataBean> addresses) {
        if (isRefreshing) {
            adapter.clear();
            for (AddressData.DataBean address : addresses) {
                adapter.insert(address, 0);
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (AddressData.DataBean address : addresses) {
                adapter.insert(address, adapter.getAdapterItemCount());
            }
        }

        if (adapter.getAdapterItemCount()==0) ultimateRecyclerView.showEmptyView();
    }
}
