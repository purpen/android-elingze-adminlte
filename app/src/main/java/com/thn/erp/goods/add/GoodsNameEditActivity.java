package com.thn.erp.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stephen on 2018/3/29 16:43
 * Email: 895745843@qq.com
 */

public class GoodsNameEditActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.editText1)
    EditText editText1;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add_name;
    }

    @Override
    protected void initView() {
        initTopbar();
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("商品名称", getResources().getColor(R.color.white));
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.white));
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
                String str = editText1.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    Intent intent = new Intent();
                    intent.putExtra(GoodsAddActivity.class.getSimpleName(), str);
                    setResult(-1, intent);
                    this.finish();
                }
                break;
        }
    }
}
