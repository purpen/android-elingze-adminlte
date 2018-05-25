package com.thn.erp.overview;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.basemodule.tools.LogUtil;
import com.thn.erp.R;
import com.thn.erp.SqliteHelper;
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
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lilin on 2018/3/7.
 */

public class OverViewFragment extends BaseFragment {

    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ViewPagerAdapter<String> viewPagerAdapter;
    private ListRecyclerViewAdapter mListAdapter;
    private THNWaittingDialog dialog;
    private List<String> slideList;
    private List<CustomMenuBean> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(getContext());
        slideList = new ArrayList<>();
        list = new ArrayList<>();
        initRecyclerView();
    }

    @Override
    protected void requestNet() {
//        getSlides();
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
            viewPagerAdapter = new ViewPagerAdapter<>(activity, slideList, Util.getScreenWidth(), getResources().getDimensionPixelSize(R.dimen.dp100));
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
        resetMenus();
        if (scrollableView != null) {
            scrollableView.start();
        }

    }

    public void resetMenus() {
        if (list == null) return;
        list.clear();
        list.addAll(getMenuData());
        LogUtil.e("list.size()="+list.size());
        if (mListAdapter!=null) {
            mListAdapter.notifyDataSetChanged();
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
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new RecycleViewItemDecoration(getActivity(), 30.0f));

        mListAdapter = new ListRecyclerViewAdapter(list, new GlobalCallBack<CustomMenuBean>() {
            @Override
            public void callBack(CustomMenuBean customMenuBean) {
                if (TextUtils.equals("客户管理", customMenuBean.title)) {
                    startActivity(new Intent(activity, CustomerListActivity.class));
                }
            }
        });
        recyclerView.setAdapter(mListAdapter);
    }


    /**
     * 获取选中的菜单列表
     */
    private List<CustomMenuBean> getMenuData() {
        SqliteHelper helper = new SqliteHelper(getContext());
        List<CustomMenuBean> beans = helper.queryItemsBySelection(1);
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
        if (beans != null) list.addAll(beans);
        return list;
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


    public static final String[] menuTitles = {"推荐有奖", "增值服务"};
    public static final int[] menuIcons = {
            R.mipmap.icon_menu_prize,
            R.mipmap.icon_menu_value_added,
    };

}
