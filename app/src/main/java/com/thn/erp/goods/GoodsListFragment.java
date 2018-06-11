package com.thn.erp.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.goods.adapter.GoodsListAdapter;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.basemodule.tools.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Stephen on 2018/4/4 16:46
 * Email: 895745843@qq.com
 */

public class GoodsListFragment extends BaseFragment {
//
//    @BindView(R.id.ultimateRecyclerView)
//    BaseUltimateRecyclerView ultimateRecyclerView;

//    @BindView(R.id.ultimateRecyclerView)
//    BaseUltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager linearLayoutManager;
    private Boolean isRefreshing = false;
    private GoodsListAdapter adapter;
    private WaitingDialog dialog;
    private int page;

    private String brandId;

    @Override
    protected int getLayout() {
        return R.layout.fragment_goods_list;
    }

    public static GoodsListFragment newInstance(String brandId) {
        Bundle args = new Bundle();
        args.putString(ExtraKey.BRAND_ID, brandId);
        GoodsListFragment fragment = new GoodsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandId = getArguments().getString(ExtraKey.BRAND_ID, null);
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(getActivity());
        initListAdapter();
        getGoodsList();
    }

    private void initListAdapter() {
        page = 1;
        adapter = new GoodsListAdapter(R.layout.layout_goods_adapter);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

//        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                page = 1;
//                isRefreshing = true;
//                getGoodsList();
//            }
//        });

//        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
//            @Override
//            public void onClick(View view, int i) {
//                GoodsData.DataBean.ProductsBean productsBean = list.get(i);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), GoodsDetailsActivity.class);
//                intent.putExtra(GoodsDetailsActivity.class.getSimpleName(),productsBean);
//                getActivity().startActivity(intent);
//            }
//        } );

//        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
//                ultimateRecyclerView.disableLoadmore();
//                isRefreshing = false;
//                page++;
//                getGoodsList();
//            }
//        });
    }

    @Override
    protected void installListener() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getGoodsList();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefreshing = false;
                page++;
                getGoodsList();
            }
        }, recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object item = adapter.getItem(position);
                if (item instanceof GoodsData.DataBean.ProductsBean){
                    GoodsData.DataBean.ProductsBean productsBean = (GoodsData.DataBean.ProductsBean)item;
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), GoodsDetailsActivity.class);
                    intent.putExtra(GoodsDetailsActivity.class.getSimpleName(), productsBean);
                    activity.startActivity(intent);
                }
            }
        });
    }

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(brandId, page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
//                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success) {
                    updateData(customerBean.data.products);
                } else {
                    ToastUtil.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
//                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<GoodsData.DataBean.ProductsBean> goodses) {
        final int size = goodses == null ? 0 : goodses.size();
        if (isRefreshing){
            swipeLayout.setRefreshing(false);
            adapter.setNewData(goodses);
        }else {
            if (goodses.size()>0) adapter.addData(goodses);
        }
        dialog.dismiss();
        if (size < Integer.valueOf(Constants.PAGE_SIZE)) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefreshing);

        } else {
            adapter.loadMoreComplete();
        }
    }
}
