package com.thn.erp.overview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.thn.erp.R;
import com.thn.erp.SqliteHelper;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.overview.adapter.CustomMainMenuAdapter;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CustomMenuSelectActivity extends BaseActivity {

    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.ultimateRecyclerView)
    UltimateRecyclerView ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CustomMenuBean> list;
    private CustomMainMenuAdapter adapter;
    private SqliteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_custom_menu_select;
    }

    @Override
    protected void initView() {
        customHeadView.setHeadCenterTxtShow(true, R.string.custom_main_menu_title);
        customHeadView.setHeadRightTxtShow(true,R.string.confirm);
        list = new ArrayList<>();
        helper = new SqliteHelper(getApplicationContext());
        list.addAll(helper.queryAll());
        adapter = new CustomMainMenuAdapter(list);
        linearLayoutManager = new LinearLayoutManager(this);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.disableLoadmore();
        ultimateRecyclerView.setAdapter(adapter);
        ultimateRecyclerView.addItemDividerDecoration(activity);
    }

    @Override
    protected void installListener() {
        customHeadView.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (list==null) return;
                helper.update(list);
                finish();
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                int size = list.size();
                if (size == 0) return;
                CustomMenuBean bean = list.get(i);
                if (bean.selected) {
                    bean.selected = false;
                } else {
                    bean.selected = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

}
