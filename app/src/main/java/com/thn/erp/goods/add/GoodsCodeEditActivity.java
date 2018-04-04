package com.thn.erp.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.common.constant.RequestCode;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.zxing.activity.CaptureActivityZxing;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/3/29 16:43
 * Email: 895745843@qq.com
 */

public class GoodsCodeEditActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_add_code;
    }

    @Override
    protected void initView() {
        initTopbar();
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("商品编码/条形码", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.THN_color_fontColor_assist));
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

    @OnClick(R.id.imageView)
    public void onViewClicked() {
//        THNQrCodeUtil.
        startActivityForResult(new Intent(activity, CaptureActivityZxing.class), RequestCode.CODE_GOODS_ADD_QRCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == RequestCode.CODE_GOODS_ADD_QRCODE) {
            editText1.setText(data.getStringExtra("text"));
        }
    }
}
