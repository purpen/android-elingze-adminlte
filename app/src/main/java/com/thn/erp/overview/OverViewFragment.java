package com.thn.erp.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.GlobalCallBack;
import com.thn.erp.overview.usermanage.UserManageActivity;
import com.thn.erp.view.autoScrollViewpager.ScrollableView;
import com.thn.erp.view.autoScrollViewpager.ViewPagerAdapter;

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

public class OverViewFragment extends BaseFragment {

    public static final String URL1 = "https://p4.taihuoniao.com/asset/180125/5a69ae57fc8b12b0488c1133-1";
    public static final String URL2 = "https://p4.taihuoniao.com/asset/180125/5a698874fc8b129c418b6c46-1";
    public static final String URL3 = "https://p4.taihuoniao.com/asset/171106/5a0039f3fc8b12a94b8b6223-1";

    public static final String LINK1 = "https://www.taihuoniao.com/tracker?kid=183561214";
    public static final String LINK2 = "https://www.taihuoniao.com/tracker?kid=183548689";
    public static final String LINK3 = "https://www.taihuoniao.com/tracker?kid=178066260";

    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    Unbinder unbinder;
    @BindView(R.id.list_fragment)
    android.support.v7.widget.RecyclerView listFragment;

    private ViewPagerAdapter<String> viewPagerAdapter;
    private ListRecyclerViewAdapter mListAdapter;


    @Override
    protected int getLayout() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void initView() {
        super.initView();
        initScrollView();
        initRecyclerView();
        initListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initScrollView() {
        if (viewPagerAdapter == null) {
            List<String> asset = new ArrayList<>();
            asset.add(URL1);
            asset.add(URL2);
            asset.add(URL3);
            viewPagerAdapter = new ViewPagerAdapter<>(activity, asset);
            scrollableView.setAdapter(viewPagerAdapter.setInfiniteLoop(true));
//            scrollableView.setOnPageChangeListener(this);
            scrollableView.setAutoScrollDurationFactor(8);
            scrollableView.setInterval(4000);
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
                        startActivity(new Intent(activity, UserManageActivity.class));
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
