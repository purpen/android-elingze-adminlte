package com.thn.erp.overview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.view.common.LinearLayoutSingleChoiceTextView;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/15 11:45
 * Email: 895745843@qq.com
 */

public class ChooseCategoryActivity extends BaseActivity {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.linearLayout_singleChoice_textView1)
    LinearLayoutSingleChoiceTextView linearLayoutSingleChoiceTextView1;
    @BindView(R.id.linearLayout_singleChoice_textView2)
    LinearLayoutSingleChoiceTextView linearLayoutSingleChoiceTextView2;
    @BindView(R.id.linearLayout_singleChoice_textView3)
    LinearLayoutSingleChoiceTextView linearLayoutSingleChoiceTextView3;
    @BindView(R.id.linearLayout_singleChoice_textView4)
    LinearLayoutSingleChoiceTextView linearLayoutSingleChoiceTextView4;
    @BindView(R.id.textView_add_category)
    TextView textViewAddCategory;

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_add_choose_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        initTopbar();
        initSingleChoiceTextView();
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        publicTopBar.setTopBarCenterTextView("选择分类", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("确认", Color.parseColor("#27AE59"));
    }

    private void initSingleChoiceTextView() {
        linearLayoutSingleChoiceTextView1.setName("重点客户");
        linearLayoutSingleChoiceTextView2.setName("普通客户");
        linearLayoutSingleChoiceTextView3.setName("小客户");

        linearLayoutSingleChoiceTextView4.setVisibility(View.GONE);
        textViewAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(activity, "添加分类", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, AddCategoryActivity.class));
            }
        });
    }
}
