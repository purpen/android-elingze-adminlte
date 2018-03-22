package com.thn.erp.overview.usermanage;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.overview.usermanage.adapter.SimpleAdapter;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 编辑客户分类
 */

public class EditCustomerClassActivity extends BaseActivity {
    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    @BindView(R.id.addClassBtn)
    Button addClassBtn;
    private LinearLayoutManager linearLayoutManager;
    private SimpleAdapter simpleRecyclerViewAdapter;
    private int moreNum=2;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_customer_class;
    }

    @Override
    protected void initView() {
//        customHeadView.setBackgroundColor(getResources().getColor(android.R.color.white));
        customHeadView.setHeadCenterTxtShow(true, R.string.select_class_title);
        customHeadView.setHeadRightTxtShow(true, R.string.confirm);
        final List<String> stringList = new ArrayList<>();
        stringList.add("111");
        stringList.add("aaa");
        stringList.add("222");
        stringList.add("33");
        stringList.add("44");
        stringList.add("55");
        stringList.add("66");
        stringList.add("11771");
        simpleRecyclerViewAdapter = new SimpleAdapter(stringList);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.WHITE);
        ultimateRecyclerView.reenableLoadmore();
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showInfo("保存");
            }
        });

        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert(moreNum++ + "  Refresh things", 0);
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
//                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(simpleRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        simpleRecyclerViewAdapter.setOnDragStartListener(new SimpleAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });


        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                        // linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition,-1);
                        //   linearLayoutManager.scrollToPosition(maxLastVisiblePosition);

                    }
                }, 1000);
            }
        });


    }

    @Override
    protected void requestNet() {
        super.requestNet();
    }

    //    @OnClick({R.id.itemUserClass})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.itemUserClass:
//
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 重置密码
     */
//    private void updatePassword() {
//        HashMap<String, String> params = ClientParamsAPI.getResetPasswordRequestParams(account, userPsw, checkCode);
//        HttpRequest.sendRequest(HttpRequest.POST, URL.RESET_PASSWORD, params, new HttpRequestCallback() {
//            @Override
//            public void onStart() {
//                btnSubmit.setEnabled(false);
//            }
//
//            @Override
//            public void onSuccess(String json) {
//                btnSubmit.setEnabled(true);
//                ResetPasswordBean resetPasswordBean = JsonUtil.fromJson(json, ResetPasswordBean.class);
//                if (resetPasswordBean.meta.status_code == Constants.SUCCESS) {
//                    ToastUtils.showSuccess(resetPasswordBean.meta.message);
//                    finish();
//                } else {
//                    ToastUtils.showError(resetPasswordBean.meta.message);
//                }
//
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                btnSubmit.setEnabled(true);
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
//    }


}
