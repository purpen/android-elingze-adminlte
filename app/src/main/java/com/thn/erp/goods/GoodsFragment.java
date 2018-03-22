package com.thn.erp.goods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.view.common.PublicTopBar;

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

    private TitleRecyclerViewAdapter titleRecyclerViewAdapter;

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
//        initListAdapter();
    }

    private void initTopBar() {
        myTopbar.setTopBarCenterTextView("商品", getResources().getColor(R.color.THN_color_bgColor_white));
    }


    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        ryMenuItem.setLayoutManager(linearLayoutManager);

        titleRecyclerViewAdapter = new TitleRecyclerViewAdapter(getActivity(), generateAdapterDatas());
        titleRecyclerViewAdapter.setOnBindListener(new TitleRecyclerViewAdapter.OnBindListener() {
            @Override
            public void onBind(View view, int i) {
//                mViewPager.setCurrentItem(i, false);
//                currentPostion = i;
            }
        });
        ryMenuItem.setAdapter(titleRecyclerViewAdapter);

    }

    private List<Map<String, Object>> generateAdapterDatas(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < ITEMS.length; i++) {
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
}
