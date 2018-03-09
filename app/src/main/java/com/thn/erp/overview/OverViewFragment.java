package com.thn.erp.overview;

import android.content.Intent;
import android.os.Bundle;
import android.os.StatFs;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.common.GlobalCallBack;
import com.thn.erp.view.MyTopBar;
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

    public static final String URL1 = "https://mmbiz.qpic.cn/mmbiz_jpg/TCHicQEF6XKDbGWldXqGLNK5B4W02lnWXWNdCDpD3DLicWzP93PSibGZVMxDk1jHK0cwZnUwXwciciaAMKagVS0wmuA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1";
    public static final String URL2 = "https://mmbiz.qpic.cn/mmbiz_jpg/TCHicQEF6XKDbGWldXqGLNK5B4W02lnWXWNdCDpD3DLicWzP93PSibGZVMxDk1jHK0cwZnUwXwciciaAMKagVS0wmuA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1";
    public static final String URL3 = "https://mmbiz.qpic.cn/mmbiz_jpg/TCHicQEF6XKDbGWldXqGLNK5B4W02lnWXWNdCDpD3DLicWzP93PSibGZVMxDk1jHK0cwZnUwXwciciaAMKagVS0wmuA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1";
    @BindView(R.id.my_topbar)
    MyTopBar myTopbar;
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

            }
        });
        listFragment.setAdapter(mListAdapter);
    }

    private void initListAdapter(){
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < ITEMS.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("img", "");
            map.put("name", ITEMS[i]);
            list.add(map);
        }
        mListAdapter.putList(list);
    }

    public static final String[] ITEMS = {"商品管理", "采购单", "销售单", "库存查询", "客户管理", "经营概况","推荐有奖", "增值服务"};
}
