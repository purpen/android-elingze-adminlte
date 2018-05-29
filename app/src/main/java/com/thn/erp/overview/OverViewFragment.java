package com.thn.erp.overview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.basemodule.tools.ScreenUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.basemodule.tools.ToastUtil;
import com.thn.erp.R;
import com.thn.erp.SqliteHelper;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.more.chat.MessageListActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.adapter.IndexMenuAdapter;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.overview.bean.SlidesData;
import com.thn.erp.overview.usermanage.CustomerListActivity;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.Util;
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ViewPagerAdapter<String> viewPagerAdapter;
    private IndexMenuAdapter adapter;
    private WaitingDialog dialog;
    private List<String> slideList;
    private List<IndexMenuAdapter.MultipleItem> list;
    private ScrollableView scrollableView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void initView() {
        dialog = new WaitingDialog(activity);
        slideList = new ArrayList<>();
        list = new ArrayList<>();
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
                    ToastUtil.showError(slidesData.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
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
            viewPagerAdapter = new ViewPagerAdapter<>(activity, slideList, ScreenUtil.getScreenWidth(), getResources().getDimensionPixelSize(R.dimen.dp100));
            scrollableView.setAdapter(viewPagerAdapter.setInfiniteLoop(true));
//            scrollableView.setOnPageChangeListener(this);
            scrollableView.setAutoScrollDurationFactor(8);
            scrollableView.showIndicators();
            scrollableView.start();
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
        if (adapter != null) {
            adapter.notifyDataSetChanged();
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
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IndexMenuAdapter(list);
        adapter.addHeaderView(getHeaderView());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获得头布局
     * @return
     */
    private ScrollableView getHeaderView() {
        scrollableView = (ScrollableView) LayoutInflater.from(getContext()).inflate(R.layout.layout_scrollabel_view, null);
        scrollableView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.dp100)));
        return scrollableView;
    }


    @Override
    protected void installListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = adapter.getItemViewType(position);
                if (type == IndexMenuAdapter.MultipleItem.IMAGE) {
                    startActivity(new Intent(activity, CustomerListActivity.class));
                } else {
                    if (list == null || list.isEmpty()) return;
                    IndexMenuAdapter.MultipleItem item = list.get(position);
                    switch (item.iconId) {
                        case R.mipmap.icon_menu_customer_manage:
                            //客户管理
                            startActivity(new Intent(activity, CustomerListActivity.class));
                            break;
                        case R.mipmap.icon_menu_more:
                            //更多菜单
                            startActivity(new Intent(activity,CustomMenuSelectActivity.class));
                            break;
                        default:
                            break;
                    }

                }
            }
        });
    }

    /**
     * 获取菜单列表
     */
    private List<IndexMenuAdapter.MultipleItem> getMenuData() {
        int length = menuTitles.length;
        ArrayList<CustomMenuBean> beanList = new ArrayList<>();
        CustomMenuBean bean;

        //添加固定menu
        for (int i = 0; i < length; i++) {
            bean = new CustomMenuBean();
            bean.pos = i;
            bean.selected = false;
            bean.iconId = menuIcons[i];
            bean.title = menuTitles[i];
            beanList.add(bean);
        }

        //读取动态添加menu
        SqliteHelper helper = new SqliteHelper(getContext());
        List<CustomMenuBean> beans = helper.queryItemsBySelection(1);
        if (beans != null) {
            beanList.addAll(beans);
        }

        ArrayList<IndexMenuAdapter.MultipleItem> list = new ArrayList<>();
        IndexMenuAdapter.MultipleItem item;

        for (CustomMenuBean beanItem : beanList) {
            item = new IndexMenuAdapter.MultipleItem(IndexMenuAdapter.MultipleItem.IMAGE_TEXT, beanItem);
            list.add(item);
        }

        //添加选择更多菜单
        IndexMenuAdapter.MultipleItem imageItem = new IndexMenuAdapter.MultipleItem(IndexMenuAdapter.MultipleItem.IMAGE, R.mipmap.icon_menu_more);
        list.add(imageItem);
        return list;
    }


    @OnClick(R.id.llSearchGoods)
    void performClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.llSearchGoods:
                intent = new Intent(activity, SearchGoodsHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.idButtonLeft:
                //消息列表
                intent = new Intent(activity, MessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.idButtonRight:
                //扫码
                break;
        }
    }


    public static final String[] menuTitles = {"推荐有奖", "增值服务"};
    public static final int[] menuIcons = {
            R.mipmap.icon_menu_prize,
            R.mipmap.icon_menu_value_added,
    };

}
