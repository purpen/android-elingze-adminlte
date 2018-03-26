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
import com.thn.erp.sale.adapter.GoodsAdapter;
import com.thn.erp.sale.bean.GoodsData;
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

public class SelectGoodsActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private WaitingDialog dialog;
    private int page;
    private List<GoodsData.DataBean.ProductsBean> list;
    private Boolean isRefreshing = false;
    private GoodsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String cid="";

    @Override
    protected int getLayout() {
        return R.layout.activity_select_goods;
    }

    @Override
    protected void initView() {
        page = 1;
        dialog = new WaitingDialog(activity);
        list = new ArrayList<>();
        customHeadView.setHeadCenterTxtShow(true, R.string.select_goods_title);
        adapter = new GoodsAdapter(list);
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
                getGoodsList();
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        final ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        adapter.setOnDragStartListener(new GoodsAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });

        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra(GoodsData.class.getSimpleName(),list.get(i));
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                getGoodsList();
                page++;
            }
        });
    }

    @Override
    protected void requestNet() {
        getGoodsList();
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(cid,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success == true) {
                    updateData(customerBean.data.products);
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

    private void updateData(List<GoodsData.DataBean.ProductsBean> goodses) {
        if (isRefreshing) {
            for (GoodsData.DataBean.ProductsBean goods : goodses) {
                adapter.insert(goods, 0);
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (GoodsData.DataBean.ProductsBean goods : goodses) {
                adapter.insert(goods, adapter.getAdapterItemCount());
            }
        }
    }
}
