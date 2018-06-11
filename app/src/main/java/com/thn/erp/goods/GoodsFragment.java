package com.thn.erp.goods;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.Constants;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.goods.adapter.GoodsListAdapter;
import com.thn.erp.goods.adapter.TitleRecyclerViewAdapter;
import com.thn.erp.goods.add.GoodsAddActivity;
import com.thn.erp.goods.brand.GoodsBrandActivity;
import com.thn.erp.goods.category.GoodsCategoryActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.SearchGoodsHistoryActivity;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.view.DropdownMenu.DropdownMenu;
import com.thn.erp.view.DropdownMenu.DropdownMenuAdapter;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by lilin on 2018/3/7.
 */

public class GoodsFragment extends BaseFragment {

    @BindView(R.id.publicTopBar)
    PublicTopBar myTopbar;
    @BindView(R.id.ry_menu_item)
    RecyclerView ryMenuItem;
    //    @BindView(R.id.ultimateRecyclerView)
//    BaseUltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.menu0)
    DropdownMenu menu0;
    @BindView(R.id.menu1)
    DropdownMenu menu1;
    @BindView(R.id.menu2)
    DropdownMenu menu2;
    private TitleRecyclerViewAdapter titleRecyclerViewAdapter;
    private WaitingDialog dialog;

    private int page;
//    private List<GoodsData.DataBean.ProductsBean> list;
    private Boolean isRefreshing = false;
    private Boolean isLoadingMore = false;
    private GoodsListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String cid = "";

    @Override
    protected int getLayout() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initView() {
        initTopBar();
        initRecyclerView();
        initDropdownMenu();
        initListAdapter();
        dialog = new WaitingDialog(activity);
        getGoodsList();
    }


    private void initDropdownMenu() {
        String[] classifyArr = getResources().getStringArray(R.array.classify);
        DropdownMenuAdapter adapter0 = new DropdownMenuAdapter(Arrays.asList(classifyArr));
        menu0.setTitle("分类");
        menu0.setAdapter(adapter0);

        String[] sortArr = getResources().getStringArray(R.array.sort);
        DropdownMenuAdapter adapter1 = new DropdownMenuAdapter(Arrays.asList(sortArr));
        menu1.setTitle("排序");
        menu1.setAdapter(adapter1);

        String[] putawayArr = getResources().getStringArray(R.array.putaway);
        DropdownMenuAdapter adapter2 = new DropdownMenuAdapter(Arrays.asList(putawayArr));
        menu2.setTitle("筛选");
        menu2.setAdapter(adapter2);
    }

    @Override
    protected void installListener() {
        menu0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu0.show();
            }
        });
        menu0.setOnItemClickListener(new DropdownMenuAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                ToastUtil.showInfo(((TextView) v).getText().toString());
                menu0.dismiss();
            }
        });

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu1.show();
            }
        });
        menu1.setOnItemClickListener(new DropdownMenuAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                ToastUtil.showInfo(((TextView) v).getText().toString());
                menu1.dismiss();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu2.show();
            }
        });
        menu2.setOnItemClickListener(new DropdownMenuAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                ToastUtil.showInfo(((TextView) v).getText().toString());
                menu2.dismiss();
            }
        });

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

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadingMore = true;
                page++;
                getGoodsList();
            }
        }, recyclerView);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setEnableLoadMore(false);
                page = 1;
                isRefreshing = true;
                getGoodsList();
            }
        });

    }

    private void initListAdapter() {
        page = 1;
        adapter = new GoodsListAdapter(R.layout.layout_goods_adapter);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.color_27AE59));
        swipeLayout.setRefreshing(true);
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



//        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
//                intent.putExtra(GoodsDetailsActivity.class.getSimpleName(), productsBean);
//                getActivity().startActivity(intent);
//            }
//        });

//        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
//                ultimateRecyclerView.disableLoadmore();
//                isLoadingMore = true;
//                page++;
//                getGoodsList();
//            }
//        });
    }


    private void initTopBar() {
        myTopbar.setTopBarCenterTextView("商品", getResources().getColor(R.color.THN_color_bgColor_white));
    }


    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ryMenuItem.setLayoutManager(linearLayoutManager);

        titleRecyclerViewAdapter = new TitleRecyclerViewAdapter(getActivity(), generateAdapterDatas());
        titleRecyclerViewAdapter.setOnItemClickListener(new TitleRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getActivity(), GoodsAddActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), GoodsCategoryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), SearchGoodsHistoryActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), GoodsBrandActivity.class));
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
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
                if (!isRefreshing || !isLoadingMore) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                swipeLayout.setRefreshing(false);
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success) {
                    updateData(customerBean.data.products);
                } else {
                    ToastUtil.showError(customerBean.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                swipeLayout.setRefreshing(false);
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<GoodsData.DataBean.ProductsBean> goodses) {
//        if (isRefreshing) {
//            adapter.setList(goodses);
//            ultimateRecyclerView.setRefreshing(false);
//            linearLayoutManager.scrollToPosition(0);
//            adapter.notifyDataSetChanged();
//        } else {
//            adapter.addList(goodses);
//        }
        final int size = goodses == null ? 0 : goodses.size();
        if (isRefreshing){
            isRefreshing = false;
            swipeLayout.setRefreshing(false);
            adapter.setEnableLoadMore(true);
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
