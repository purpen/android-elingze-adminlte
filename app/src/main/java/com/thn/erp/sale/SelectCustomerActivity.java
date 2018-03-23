package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.AddCustomActivity;
import com.thn.erp.overview.usermanage.adapter.CustomerListAdapter;
import com.thn.erp.overview.usermanage.bean.CustomerData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.SearchView;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lilin on 2018/3/23.
 */

public class SelectCustomerActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private WaitingDialog dialog;
    private int page;
    private List<CustomerData.DataBean.CustomersBean> list;
    private Boolean isRefreshing = false;
    private CustomerListAdapter simpleRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_customer;
    }

    @Override
    protected void initView() {
        page = 1;
        dialog = new WaitingDialog(activity);
        list = new ArrayList<>();
        customHeadView.setHeadCenterTxtShow(true, R.string.select_customer_title);
        simpleRecyclerViewAdapter = new CustomerListAdapter(list);
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
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {
        // 实现TextWatcher监听即可
        searchView.setOnSearchClickListener(new SearchView.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view) {
                ToastUtils.showInfo("going search");
            }
        });
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, AddCustomActivity.class));
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getCustomers();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(simpleRecyclerViewAdapter);
        final ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        simpleRecyclerViewAdapter.setOnDragStartListener(new CustomerListAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                getCustomers();
                page++;
            }
        });
    }

    @Override
    protected void requestNet() {
        getCustomers();
    }

    private void getCustomers() {
        HashMap<String, String> params = ClientParamsAPI.getCustomerList(page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.CUSTOMER_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                CustomerData customerBean = JsonUtil.fromJson(json, CustomerData.class);
                if (customerBean.success == true) {
                    updateData(customerBean.data.customers);
                } else {
                    ToastUtils.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<CustomerData.DataBean.CustomersBean> customers) {
        if (isRefreshing) {
            for (CustomerData.DataBean.CustomersBean customer : customers) {
                simpleRecyclerViewAdapter.insert(customer, 0);
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            simpleRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            for (CustomerData.DataBean.CustomersBean customer : customers) {
                simpleRecyclerViewAdapter.insert(customer, simpleRecyclerViewAdapter.getAdapterItemCount());
            }
        }
        dialog.dismiss();
    }
}
