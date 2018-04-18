package com.thn.erp.sale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.thn.erp.AppApplication;
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
import com.thn.erp.sale.adapter.SpecificationAdapter;
import com.thn.erp.sale.bean.GoodsData;
import com.thn.erp.sale.bean.SKUListData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
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
    private SKUListData.DataBean.ItemsBean dataBean;
    /**
     * 选中的颜色
     */
    private TextView selectedColorTv;
    /**
     * 选中的规格
     */
    private TextView selectedSpecTv;

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
                if (list.size() == 0) return;
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
                selectedColorTv = null;
                selectedSpecTv = null;
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
                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
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
     *
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
     *
     * @param skuListData
     */
    private void setDialogData(SKUListData skuListData) {
        final List<SKUListData.DataBean.ItemsBean> items = skuListData.data.items;
        final List<SKUListData.DataBean.ColorsBean> colors = skuListData.data.colors;
        final List<SKUListData.DataBean.ModesBean> modes = skuListData.data.modes;
        initSkuAttrState(items,colors,modes);
        if (colors.size() == 0) {
            holder.colorSpecUltimateRecyclerView.setVisibility(View.GONE);
            holder.tvColorSpec.setVisibility(View.GONE);
        } else {
            holder.colorSpecUltimateRecyclerView.setVisibility(View.VISIBLE);
            holder.tvColorSpec.setVisibility(View.VISIBLE);
        }

        if (modes.size() == 0) {
            holder.specificUltimateRecyclerView.setVisibility(View.GONE);
            holder.tvSpecification.setVisibility(View.GONE);
        } else {
            holder.specificUltimateRecyclerView.setVisibility(View.VISIBLE);
            holder.tvSpecification.setVisibility(View.VISIBLE);
        }


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppApplication.getContext());
        final LinearLayoutManager modesLayoutManager = new LinearLayoutManager(AppApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        modesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.specificUltimateRecyclerView.setHasFixedSize(true);
        holder.colorSpecUltimateRecyclerView.setHasFixedSize(true);
        holder.colorSpecUltimateRecyclerView.setLayoutManager(linearLayoutManager);
        holder.specificUltimateRecyclerView.setLayoutManager(modesLayoutManager);
        final SKUAdapter skuAdapter = new SKUAdapter(activity, colors);
        final SpecificationAdapter specificationAdapter = new SpecificationAdapter(activity, modes);
        holder.colorSpecUltimateRecyclerView.setAdapter(skuAdapter);
        holder.specificUltimateRecyclerView.setAdapter(specificationAdapter);
        dataBean = items.get(0);
        setSkuInfo(dataBean);
        skuAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                int size = colors.size();
                SKUListData.DataBean.ColorsBean colorsBean;
                for (int j=0;j<size;j++){
                    colorsBean = colors.get(j);
                    if (j==i){
                        colorsBean.selected=!colorsBean.selected;
                    }else {
                        colorsBean.selected =false;
                    }
                }

                skuAdapter.notifyDataSetChanged();

                selectedColorTv = ((TextView) view);
                setSkuSpecInfoByAttr(items);
//                更新规格列表可选状态
                setSpecSelectableState(items, specificationAdapter, modes);
            }
        });

        specificationAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                int size = modes.size();
                SKUListData.DataBean.ModesBean modesBean;
                for (int j=0;j<size;j++){
                    modesBean = modes.get(j);
                    if (j==i){
                        modesBean.selected=!modesBean.selected;
                    }else {
                        modesBean.selected =false;
                    }
                }
                specificationAdapter.notifyDataSetChanged();
                selectedSpecTv = ((TextView) view);
                setSkuSpecInfoByAttr(items);
//                更新颜色列表可选状态
                setColorSelectableState(items, skuAdapter, colors);
            }
        });


        holder.dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colors.size() > 0 && selectedColorTv == null) {
                    ToastUtils.showInfo("请选择颜色分类");
                    return;
                }

                if (modes.size() > 0 && selectedSpecTv == null) {
                    ToastUtils.showInfo("请选择规格");
                    return;
                }

                dataBean.buyNum = holder.counterView.getNum();
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.putExtra(SKUListData.class.getSimpleName(), dataBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 初始化sku属性状态
     * @param items
     * @param colors
     * @param modes
     */
    private void initSkuAttrState(List<SKUListData.DataBean.ItemsBean> items, List<SKUListData.DataBean.ColorsBean> colors, List<SKUListData.DataBean.ModesBean> modes) {
        long stockCount = 0;
        for (SKUListData.DataBean.ColorsBean color:colors){
            for (SKUListData.DataBean.ItemsBean item:items){
                if (TextUtils.equals(item.s_color, color.name)){
                    stockCount += item.stock_count;
                }
            }
            if (stockCount==0){
                color.valid = false;
            }else {
                color.valid = true;
            }
        }

//        重置库存
        stockCount =0;

        for (SKUListData.DataBean.ModesBean mode:modes){
            for (SKUListData.DataBean.ItemsBean item:items){
                if (TextUtils.equals(item.s_model, mode.name)){
                    stockCount += item.stock_count;
                }
            }
            if (stockCount==0){
                mode.valid = false;
            }else {
                mode.valid = true;
            }
        }
    }

    /**
     * 根据规格找出库存为0的颜色，更新颜色列表
     * @param items
     * @param adapter
     * @param colors
     */
    private void setColorSelectableState(List<SKUListData.DataBean.ItemsBean> items, SKUAdapter adapter, List<SKUListData.DataBean.ColorsBean> colors) {
        if (!holder.colorSpecUltimateRecyclerView.isShown()) return;
        String specTvText = selectedSpecTv.getText().toString();
        HashMap<String,SKUListData.DataBean.ItemsBean> existItems=new HashMap<>();
        for (SKUListData.DataBean.ItemsBean item : items) {
            if (TextUtils.equals(specTvText, item.s_model)){
                existItems.put(item.s_color,item);
            }
        }

        for (SKUListData.DataBean.ColorsBean color : colors){
            SKUListData.DataBean.ItemsBean bean = existItems.get(color.name);
            if(null==bean){
                color.valid = false;
            }else {
                if (bean.stock_count==0){
                    color.valid = false;
                }else {
                    color.valid = true;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据颜色找出库存为0的规格，更新规格列表
     * @param items
     * @param adapter
     * @param modes
     */
    private void setSpecSelectableState(List<SKUListData.DataBean.ItemsBean> items, SpecificationAdapter adapter, List<SKUListData.DataBean.ModesBean> modes) {
        if (!holder.specificUltimateRecyclerView.isShown()) return;
        String colorTvText = selectedColorTv.getText().toString();

//       得到选中颜色的所有规格
        HashMap<String,SKUListData.DataBean.ItemsBean> existItems=new HashMap<>();
        for (SKUListData.DataBean.ItemsBean item : items) {
            if (TextUtils.equals(colorTvText, item.s_color)){
                existItems.put(item.s_model,item);
            }
        }

//        根据规格存在情况设置是否可选
        for (SKUListData.DataBean.ModesBean mode : modes){
            SKUListData.DataBean.ItemsBean bean = existItems.get(mode.name);
            if(null==bean){
                mode.valid = false;
            }else {
                if (bean.stock_count==0){
                    mode.valid = false;
                }else {
                    mode.valid = true;
                }
            }
        }
//        更新规格列表
        adapter.notifyDataSetChanged();
    }

    /**
     * 根据颜色/规格显示指定SKU
     * @param items
     * @return
     */
    private void setSkuSpecInfoByAttr(List<SKUListData.DataBean.ItemsBean> items) {
//        只显示颜色列表
        if (holder.colorSpecUltimateRecyclerView.isShown() && !holder.specificUltimateRecyclerView.isShown()) {
            if (selectedColorTv == null) return;
            CharSequence colorTvText = selectedColorTv.getText();
            if (TextUtils.isEmpty(colorTvText)) return;
            for (SKUListData.DataBean.ItemsBean item : items) {
                if (TextUtils.equals(item.s_color, colorTvText)) {
                    setSkuInfo(item);
                    return;
                }
            }
//            只显示规格列表
        } else if (!holder.colorSpecUltimateRecyclerView.isShown() && holder.specificUltimateRecyclerView.isShown()) {
            if (selectedSpecTv == null) return;
            CharSequence specTvText = selectedSpecTv.getText();
            if (TextUtils.isEmpty(specTvText)) return;
            for (SKUListData.DataBean.ItemsBean item : items) {
                if (TextUtils.equals(item.s_model, specTvText)) {
                    setSkuInfo(item);
                    return;
                }
            }
//            颜色规格列表都显示
        } else if (holder.colorSpecUltimateRecyclerView.isShown() && holder.specificUltimateRecyclerView.isShown()) {
            if (selectedColorTv == null || selectedSpecTv == null) return;
            CharSequence colorTvText = selectedColorTv.getText();
            CharSequence specTvText = selectedSpecTv.getText();
            if (TextUtils.isEmpty(colorTvText) || TextUtils.isEmpty(specTvText)) return;
            LogUtil.e("选中颜色="+colorTvText+"&&选中规格="+specTvText);
            for (SKUListData.DataBean.ItemsBean item : items) {
                if (TextUtils.equals(item.s_color, colorTvText) && TextUtils.equals(item.s_model, specTvText)) {
                    LogUtil.e(item.cover);
                    setSkuInfo(item);
                    return;
                }
            }
        }


    }

    /**
     * 设置Sku的信息
     *
     * @param dataBean
     */
    private void setSkuInfo(SKUListData.DataBean.ItemsBean dataBean) {
        this.dataBean = dataBean;
        holder.counterView.setStorageNum(dataBean.stock_count);
        THNGlideUtil.displayImageFadein(dataBean.cover, holder.dialogCartProductimg);
        holder.dialogCartPrice.setText("￥" + dataBean.sale_price);
        holder.dialogCartProducttitle.setText(dataBean.product_name);
        holder.dialogCartSkusnumber.setText("库存：" + dataBean.stock_count);
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
        @BindView(R.id.colorSpecUltimateRecyclerView)
        UltimateRecyclerView colorSpecUltimateRecyclerView;
        @BindView(R.id.specificUltimateRecyclerView)
        UltimateRecyclerView specificUltimateRecyclerView;
        @BindView(R.id.tvSpecification)
        TextView tvSpecification;
        @BindView(R.id.tvColorSpec)
        TextView tvColorSpec;
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
