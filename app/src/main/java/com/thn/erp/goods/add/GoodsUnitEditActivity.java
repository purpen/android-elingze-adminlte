package com.thn.erp.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;
import com.thn.erp.view.common.PublicTopBar;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/29 16:43
 * Email: 895745843@qq.com
 */

public class GoodsUnitEditActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;

    private RecyclerViewArrayAdapter arrayAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add_unit;
    }

    @Override
    protected void initView() {
        initTopbar();
        initRecyclerView();
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("商品销售单位", getResources().getColor(R.color.white));
//        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.RIGHT:
//                String str = editText1.getText().toString();
//                if (!TextUtils.isEmpty(str)) {
//                    Intent intent = new Intent();
//                    intent.putExtra(GoodsAddActivity.class.getSimpleName(), str);
//                    setResult(-1, intent);
//                    this.finish();
//                }
                break;
        }
    }

    private void initRecyclerView() {
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        String[] stringArray = getResources().getStringArray(R.array.GoodsAddUnit);
        final List<String> strings = Arrays.asList(stringArray);
        arrayAdapter = new RecyclerViewArrayAdapter(this, android.R.layout.simple_list_item_1, strings, new OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int i) {
                Intent intent = new Intent();
                intent.putExtra(GoodsAddActivity.class.getSimpleName(), arrayAdapter.getItem(i));
                setResult(-1, intent);
                GoodsUnitEditActivity.this.finish();
            }
        });
        recyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setAdapter(arrayAdapter);
    }
}
