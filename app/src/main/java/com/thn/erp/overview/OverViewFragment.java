package com.thn.erp.overview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.interfaces.GlobalCallBack;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.overview.bean.SlidesData;
import com.thn.erp.overview.usermanage.CustomerListActivity;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.utils.Util;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lilin on 2018/3/7.
 */

public class OverViewFragment extends BaseFragment {
    public static final String LINK1 = "https://www.taihuoniao.com/tracker?kid=183561214";
    public static final String LINK2 = "https://www.taihuoniao.com/tracker?kid=183548689";
    public static final String LINK3 = "https://www.taihuoniao.com/tracker?kid=178066260";

    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    @BindView(R.id.list_fragment)
    RecyclerView listFragment;
    private ViewPagerAdapter<String> viewPagerAdapter;
    private ListRecyclerViewAdapter mListAdapter;
    private THNWaittingDialog dialog;
    List<String> slideList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(getContext());
        slideList = new ArrayList<>();
        initRecyclerView();
    }

    @Override
    protected void requestNet() {
        getSlides();
    }

    /**
     * 获取轮播图
     */
    private void getSlides() {
        HashMap<String, String> params = ClientParamsAPI.slideParam("erpapp_index_slide", 1);
        HttpRequest.sendRequest(HttpRequest.GET, URL.SLIDES, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                SlidesData slidesData = JsonUtil.fromJson(json, SlidesData.class);
                if (slidesData.success == true) {
                    updateData(slidesData.data.slides);
                } else {
                    ToastUtils.showError(slidesData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateData(List<SlidesData.DataBean.SlidesBean> slides) {
        for (SlidesData.DataBean.SlidesBean slidesBean : slides) {
            slideList.add(slidesBean.image);
        }
        initScrollView();
    }

    private void initScrollView() {
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter<>(activity, slideList, Util.getScreenWidth(),getResources().getDimensionPixelSize(R.dimen.dp100));
            scrollableView.setAdapter(viewPagerAdapter.setInfiniteLoop(true));
//            scrollableView.setOnPageChangeListener(this);
            scrollableView.setAutoScrollDurationFactor(8);
            scrollableView.showIndicators();
            scrollableView.start();
        } else {
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scrollableView != null) {
            scrollableView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (scrollableView != null) {
            scrollableView.stop();
        }
    }

    /**
     * 初始化菜单数据
     */
    private void initRecyclerView() {
        int length = menuTitles.length;
        ArrayList<CustomMenuBean> list = new ArrayList<>();
        CustomMenuBean bean;
        for (int i = 0; i < length; i++) {
            bean = new CustomMenuBean();
            bean.pos = i;
            bean.selected = false;
            bean.iconId = menuIcons[i];
            bean.title = menuTitles[i];
            list.add(bean);
        }
//TODO
//        Collections.sort(list,new SortByPosition());

        String s = JsonUtil.list2Json(list);

        listFragment.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        listFragment.setLayoutManager(layoutManager);
//        listFragment.addItemDecoration(new RecycleViewItemDecoration(getActivity(), 30.0f));
        mListAdapter = new ListRecyclerViewAdapter(list,new GlobalCallBack<CustomMenuBean>() {
            @Override
            public void callBack(CustomMenuBean customMenuBean) {
                if (TextUtils.equals("客户管理",customMenuBean.title)) {
                    startActivity(new Intent(activity, CustomerListActivity.class));
                }
            }
        });
        listFragment.setAdapter(mListAdapter);
    }



    @OnClick(R.id.llSearchGoods)
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.llSearchGoods:
                Intent intent = new Intent(activity, SearchGoodsHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }



    public static final String[] menuTitles = {"推荐有奖","增值服务","经营概况","销售单","销售退货单","进销对比","采购单","采购退货单", "商品管理", "人员管理", "客户管理","供应商管理","出库单历史","入库单历史","库存查询","入库报表","出库报表","物流管理"};
    public static final int[] menuIcons = {
            R.mipmap.icon_menu_prize,
            R.mipmap.icon_menu_value_added,
            R.mipmap.icon_menu_status,
            R.mipmap.icon_menu_sale_order,
            R.mipmap.icon_menu_sales_return,
            R.mipmap.icon_menu_import_export_compare,
            R.mipmap.icon_menu_purchase_order,
            R.mipmap.icon_menu_purchase_return,
            R.mipmap.icon_menu_goods_manage,
            R.mipmap.icon_menu_person_manage,
            R.mipmap.icon_menu_customer_manage,
            R.mipmap.icon_menu_supplier_manage,
            R.mipmap.icon_menu_outgoing_history,
            R.mipmap.icon_menu_warehousing_history,
            R.mipmap.icon_menu_inventory_query,
            R.mipmap.icon_menu_warehousing_report,
            R.mipmap.icon_menu_outgoing_report,
            R.mipmap.icon_menu_logistics_manage,
           };

    private static class SortByPosition implements Comparator<CustomMenuBean>{
        @Override
        public int compare(CustomMenuBean o1, CustomMenuBean o2) {
            if (o1.pos<o2.pos){
                return -1;
            }else if(o1.pos>o2.pos){
                return 1;
            }
            return 0;
        }
    }
}
