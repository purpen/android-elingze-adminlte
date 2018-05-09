package com.thn.erp.sale;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.goods.adapter.TitleRecyclerViewAdapter;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.CustomerListActivity;
import com.thn.erp.sale.adapter.OrderListAdapter;
import com.thn.erp.sale.bean.OrderData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;
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
    private boolean isRefreshing = false;
    private TitleRecyclerViewAdapter titleRecyclerViewAdapter;
    private OrderListAdapter adapter;
    private THNWaittingDialog dialog;
    private int page = 1;
    private String status = "";
    private LinearLayoutManager linearLayoutManager;
    private List<OrderData.DataBean.OrdersBean> list;
    private boolean isLoadingMore = false;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sale;
    }


    @Override
    protected void initView() {
        page = 1;
        dialog= new THNWaittingDialog(activity);
        myTopbar.setTopBarCenterTextView("销售", getResources().getColor(R.color.THN_color_bgColor_white));
        initRecyclerView();
        list = new ArrayList<>();
        adapter = new OrderListAdapter(activity,list);
        linearLayoutManager = new LinearLayoutManager(activity);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(activity)
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
    protected void installListener() {
        titleRecyclerViewAdapter.setOnItemClickListener(new TitleRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onClick(View view, int i) {
                switch (i){
                    case 0:
                        startActivity(new Intent(activity,CustomerListActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        startActivity(new Intent(activity,DXOrderActivity.class));
                        break;
                }

            }
        });
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                LogUtil.e("i="+i);
                if (list.size()==0 || i<0) return;
                Intent intent = new Intent(activity,OrderDetailsActivity.class);
                intent.putExtra(OrderDetailsActivity.class.getSimpleName(),list.get(i));
                activity.startActivity(intent);
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getOrderList();
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isLoadingMore=true;
                page++;
                getOrderList();
            }
        });
    }

    @Override
    protected void requestNet() {
        getOrderList();
    }

    /**
     * 获取订单列表
     */
    private void getOrderList() {
        HashMap<String, String> params = ClientParamsAPI.getOrderList(status,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.ORDER_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (!isRefreshing || !isLoadingMore) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                OrderData orderData = JsonUtil.fromJson(json, OrderData.class);
                if (orderData.success == true) {
                    LogUtil.e(json);
                    List<OrderData.DataBean.OrdersBean> orders = orderData.data.orders;
                    updateData(orders);
                } else {
                    ToastUtils.showError(orderData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<OrderData.DataBean.OrdersBean> ordersBeans) {
        if (isRefreshing) {
            adapter.clear();
            for (OrderData.DataBean.OrdersBean order : ordersBeans) {
                adapter.insert(order, adapter.getAdapterItemCount());
            }
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (OrderData.DataBean.OrdersBean order : ordersBeans) {
                adapter.insert(order, adapter.getAdapterItemCount());
            }
        }
        ultimateRecyclerView.setRefreshing(false);
        if (ordersBeans.size() == 0 && adapter.getAdapterItemCount()>0) ultimateRecyclerView.disableLoadmore();
        if (adapter.getAdapterItemCount() == 0) ultimateRecyclerView.showEmptyView();
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