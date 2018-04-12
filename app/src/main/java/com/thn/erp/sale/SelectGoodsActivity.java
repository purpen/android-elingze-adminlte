package com.thn.erp.sale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.usermanage.AddCustomActivity;
import com.thn.erp.sale.adapter.GoodsAdapter;
import com.thn.erp.sale.adapter.SKUAdapter;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.sale.bean.SKUListData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CounterView;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.SearchView;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lilin on 2018/3/23.
 */

public class SelectGoodsActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private WaitingDialog dialog;
    private int page;
    private List<GoodsData.DataBean.ProductsBean> list;
    private Boolean isRefreshing = false;
    private GoodsAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private String cid = "";
    private PopupWindow popupWindow;
    private ViewHolder holder;
    private TextView lastTv;
    private SKUListData.DataBean.ItemsBean dataBean;
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
        initPopWindow();
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

        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                if (list.size()==0) return;
                getSKUList(list.get(i));
                showPopupWindow();


            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                isRefreshing = false;
                page++;
                getGoodsList();
            }
        });
    }

    private void initPopWindow() {
        final View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_select_goods, null);
        holder = new ViewHolder(popupView);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.popupwindow_style);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(params);
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupView.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(popupView.getWindowToken(), 0);
                return false;
            }
        });

    }

    /**
     * 显示弹出框
     */
    private void showPopupWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = 0.4f;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//这行代码可以使window后的所有东西边暗淡
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.white));
        popupWindow.showAtLocation(rl, Gravity.BOTTOM, 0, 0);

    }

    @Override
    protected void requestNet() {
        getGoodsList();
    }

    /**
     * 获取SKU列表
     * @param productsBean
     */
    private void getSKUList(final GoodsData.DataBean.ProductsBean productsBean) {
        HashMap<String, String> params = ClientParamsAPI.getSKUListParams(productsBean.rid);
        HttpRequest.sendRequest(HttpRequest.GET, URL.SKU_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                SKUListData skuListData = JsonUtil.fromJson(json, SKUListData.class);

                if (skuListData.success == true) {
                    setDialogData(skuListData);
                } else {
                    ToastUtils.showError(skuListData.status.message);
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
     * 设置商品采购的商品sku信息
     * @param skuListData
     */
    private void setDialogData(SKUListData skuListData) {
        final List<SKUListData.DataBean.ItemsBean> items = skuListData.data.items;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.dialogUltimateRecyclerView.setHasFixedSize(true);
        holder.dialogUltimateRecyclerView.setLayoutManager(linearLayoutManager);
        SKUAdapter skuAdapter = new SKUAdapter(activity, items);
        holder.dialogUltimateRecyclerView.setAdapter(skuAdapter);
         dataBean = items.get(0);

        setSkuInfo(dataBean);
        skuAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                dataBean = items.get(i);
                if (lastTv!=null){
                    lastTv.setTextColor(getResources().getColor(R.color.color_27AE59));
                    lastTv.setBackgroundResource(R.drawable.corner_border_27ae59);
                }
                TextView curTv = ((TextView)view);
                setSkuInfo(dataBean);
                curTv.setBackgroundResource(R.drawable.corner_bg_27ae59);
                curTv.setTextColor(getResources().getColor(android.R.color.white));
                lastTv = curTv;
            }
        });

        holder.dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBean.buyNum= holder.counterView.getNum();
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.putExtra(SKUListData.class.getSimpleName(),dataBean);
                setResult(RESULT_OK,intent);
                finish();
//                addShopCart(dataBean);
            }
        });
    }

    /**
     * 添加代下单界面购物车
     * @param dataBean
     */
//    private void addShopCart(SKUListData.DataBean.ItemsBean dataBean) {
//        HashMap<String, String> params = ClientParamsAPI.getShopCartParams(dataBean.rid,dataBean.buyNum);
//        HttpRequest.sendRequest(HttpRequest.POST, URL.ADD_SHOPCART, params, new HttpRequestCallback() {
//            @Override
//            public void onStart() {
//                if (!isRefreshing) dialog.show();
//            }
//
//            @Override
//            public void onSuccess(String json) {
//                dialog.dismiss();
//                AddShopCartData addShopCartData = JsonUtil.fromJson(json, AddShopCartData.class);
//                if (addShopCartData.success == true) {
//
//                } else {
//                    ToastUtils.showError(addShopCartData.status.message);
//                }
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                dialog.dismiss();
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
//    }

    /**
     * 设置Sku的信息
     * @param dataBean
     */
    private void setSkuInfo(SKUListData.DataBean.ItemsBean dataBean) {
        holder.counterView.setStorageNum(dataBean.stock_count);
        THNGlideUtil.displayImageFadein(dataBean.cover,holder.dialogCartProductimg);
        holder.dialogCartPrice.setText("￥"+dataBean.sale_price);
        holder.dialogCartProducttitle.setText(dataBean.product_name);
        holder.dialogCartSkusnumber.setText("库存："+dataBean.stock_count);
    }

    /**
     * 获取商品列表
     */
    private void getGoodsList() {
        HashMap<String, String> params = ClientParamsAPI.getGoodsList(cid, page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GOODS_LIST, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                if (!isRefreshing) dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                GoodsData customerBean = JsonUtil.fromJson(json, GoodsData.class);
                if (customerBean.success == true) {
                    List<GoodsData.DataBean.ProductsBean> products = customerBean.data.products;
                    if (products.size() == 0) ultimateRecyclerView.disableLoadmore();
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
            adapter.clear();
            for (GoodsData.DataBean.ProductsBean goods : goodses) {
                adapter.insert(goods, adapter.getAdapterItemCount());
            }
            ultimateRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
            adapter.notifyDataSetChanged();
        } else {
            for (GoodsData.DataBean.ProductsBean goods : goodses) {
                adapter.insert(goods, adapter.getAdapterItemCount());
            }
        }
        if (adapter.getAdapterItemCount() == 0) ultimateRecyclerView.showEmptyView();
    }


    /**
     * 商品选择数量对话框
     */
    class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.dialogUltimateRecyclerView)
        UltimateRecyclerView dialogUltimateRecyclerView;
        @BindView(R.id.dialog_cart_productimg)
        ImageView dialogCartProductimg;
        @BindView(R.id.dialog_cart_producttitle)
        TextView dialogCartProducttitle;
        @BindView(R.id.dialog_cart_price)
        TextView dialogCartPrice;
        @BindView(R.id.dialog_cart_skusnumber)
        TextView dialogCartSkusnumber;
        @BindView(R.id.counterView)
        CounterView counterView;
        @BindView(R.id.dialog_confirm_btn)
        Button dialogConfirmBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
