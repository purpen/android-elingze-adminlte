package com.thn.erp.overview;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.thn.erp.R;
import com.thn.erp.SqliteHelper;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.overview.adapter.CustomMainMenuAdapter;
import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.view.CustomHeadView;

import java.util.ArrayList;

import butterknife.BindView;

public class CustomMenuSelectActivity extends BaseActivity {

    @BindView(R.id.customHeadView)
    CustomHeadView customHeadView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
        adapter = new CustomMainMenuAdapter(R.layout.item_customer_class,list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        View view = new View(getApplicationContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.dp10)));
        view.setBackgroundColor(Color.parseColor("#f8f8f8"));
        adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int size = list.size();
                if (size == 0) return;
                CustomMenuBean bean = list.get(position);
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
