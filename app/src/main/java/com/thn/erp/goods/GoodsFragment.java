package com.thn.erp.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.stephen.taihuoniaolibrary.utils.THNLogUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.base.BaseUltimateRecyclerView;
import com.thn.erp.common.OnRecyclerViewItemClickListener;
import com.thn.erp.goods.add.GoodsAddActivity;
import com.thn.erp.goods.brand.GoodsBrandActivity;
import com.thn.erp.goods.category.GoodsCategoryActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lilin on 2018/3/7.
 */

public class GoodsFragment extends BaseFragment {

    @BindView(R.id.publicTopBar)
    PublicTopBar myTopbar;
    @BindView(R.id.ry_menu_item)
    RecyclerView ryMenuItem;
    Unbinder unbinder;
    @BindView(R.id.ultimateRecyclerView)
    BaseUltimateRecyclerView ultimateRecyclerView;

    private TitleRecyclerViewAdapter titleRecyclerViewAdapter;
    private WaitingDialog dialog;

    private int page;
    private List<GoodsData.DataBean.ProductsBean> list;
    private Boolean isRefreshing = false;
    private GoodsListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String cid = "";

    @Override
    protected int getLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initView() {
        super.initView();
        initTopBar();
        initRecyclerView();
        initListAdapter();
        dialog = new WaitingDialog(getActivity());
        getGoodsList();
    }

    private void initListAdapter() {
        page = 1;
        dialog = new WaitingDialog(activity);
        list = new ArrayList<>();
        adapter = new GoodsListAdapter(list);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(adapter);

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefreshing = true;
                getGoodsList();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                GoodsData.DataBean.ProductsBean productsBean = list.get(i);
                Intent intent = new Intent();
                intent.setClass(getActivity(), GoodsDetailsActivity.class);
                intent.putExtra("Extra",productsBean);
                getActivity().startActivity(intent);
            }
        } );

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                getGoodsList();
                page++;
            }
        });
    }

    private void initTopBar() {
        myTopbar.setTopBarCenterTextView("商品", getResources().getColor(R.color.THN_color_bgColor_white));
    }


    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ryMenuItem.setLayoutManager(linearLayoutManager);

        titleRecyclerViewAdapter = new TitleRecyclerViewAdapter(getActivity(), generateAdapterDatas());
        titleRecyclerViewAdapter.setOnItemClickListener(new TitleRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getActivity(), GoodsAddActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), GoodsCategoryActivity.class));
                        break;
                    case 2: break;
                    case 3:
                        startActivity(new Intent(getActivity(), GoodsBrandActivity.class));
                        break;
                    case 4: break;
                    case 5: break;
                    case 6: break;
                }
            }
        });
        ryMenuItem.setAdapter(titleRecyclerViewAdapter);
    }

    private List<Map<String, Object>> generateAdapterDatas() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < ITEMS.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", IMGS[i]);
            map.put("name", ITEMS[i]);
            list.add(map);
        }
        return list;
    }

    public static final String[] ITEMS = {"新增", "分类", "搜索", "品牌", "产品组", "促销"};
    public static final int[] IMGS = {R.mipmap.icon_goods_top_category_01, R.mipmap.icon_goods_top_category_02
            , R.mipmap.icon_goods_top_category_03, R.mipmap.icon_goods_top_category_04
            , R.mipmap.icon_goods_top_category_05, R.mipmap.icon_goods_top_category_06};

    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList("", page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
//                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                THNLogUtil.e("json -----", json);
                dialog.dismiss();
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success) {
                    updateData(customerBean.data.products);
                } else {
                    ToastUtils.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
//                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<GoodsData.DataBean.ProductsBean> goodses) {
        if (isRefreshing) {
            adapter.setList(goodses);
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            adapter.addList(goodses);
        }
        dialog.dismiss();
    }
}
