package com.thn.erp.overview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.interfaces.GlobalCallBack;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.bean.SlidesData;
import com.thn.erp.overview.usermanage.CustomerListActivity;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
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
        dialog=new THNWaittingDialog(getContext());
        slideList= new ArrayList<>();
        initRecyclerView();
        initListAdapter();
    }

    @Override
    protected void requestNet() {
        getSlides();
    }

    /**
     * 获取轮播图
     */
    private void getSlides() {
        HashMap<String, String> params = ClientParamsAPI.slideParam("erpapp_index_slide",1);
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
        for (SlidesData.DataBean.SlidesBean slidesBean:slides){
            slideList.add(slidesBean.image);
        }
        initScrollView();
    }

    private void initScrollView() {
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter<>(activity, slideList);
            scrollableView.setAdapter(viewPagerAdapter);
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

    private void initRecyclerView() {
        listFragment.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        listFragment.setLayoutManager(layoutManager);
//        listFragment.addItemDecoration(new RecycleViewItemDecoration(getActivity(), 30.0f));
        mListAdapter = new ListRecyclerViewAdapter(getActivity(), new GlobalCallBack() {
            @Override
            public void callBack(Object o) {
//                startActivity(new Intent(getActivity(), CustomerManageActivity.class));
//                LogUtil.e(o.toString());
                if (o instanceof Map){
                    if (((Map) o).get("name").equals("客户管理")){
                        startActivity(new Intent(activity, CustomerListActivity.class));
                    }
                }
            }
        });
        listFragment.setAdapter(mListAdapter);
    }

    private void initListAdapter(){
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < ITEMS.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", String.valueOf(IMGS[i]));
            map.put("name", ITEMS[i]);
            list.add(map);
        }
        mListAdapter.putList(list);
    }

    @OnClick(R.id.llSearchGoods)
    void performClick(View v){
        switch (v.getId()){
            case R.id.llSearchGoods:
                Intent intent=new Intent(activity,SearchGoodsActivity.class);
                startActivity(intent);
                break;
        }
    }


    public static final String[] ITEMS = {"商品管理", "采购单", "销售单", "库存查询", "客户管理", "经营概况","推荐有奖", "增值服务"};
    public static final int[] IMGS = {R.mipmap.icon_overview_main_category_01, R.mipmap.icon_overview_main_category_02,
            R.mipmap.icon_overview_main_category_03,
            R.mipmap.icon_overview_main_category_04,
            R.mipmap.icon_overview_main_category_05,
            R.mipmap.icon_overview_main_category_06,
            R.mipmap.icon_overview_main_category_07,
            R.mipmap.icon_overview_main_category_08,
            R.mipmap.icon_overview_main_category_09};
}
