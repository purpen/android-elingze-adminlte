package com.thn.erp.goods.category;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNTypeConversionUtils;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.goods.brand.entity.BrandResultBean;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.PopupWindowUtil;
import com.thn.erp.view.common.LinearLayoutCustomerAddEditTextView;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/4/11 18:48
 * Email: 895745843@qq.com
 */

public class GoodsCategoryAddActivity extends BaseActivity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.addEditText1)
    LinearLayoutCustomerAddEditTextView addEditText1;
    @BindView(R.id.addEditText2)
    LinearLayoutCustomerAddEditTextView addEditText2;
    @BindView(R.id.addEditText3)
    LinearLayoutCustomerAddEditTextView addEditText3;
    @BindView(R.id.textView_goods_category_edit_save)
    TextView textViewGoodsCategoryEditSave;

    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_category_add;
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
        initCategoryLayout();
        dialog = new WaitingDialog(this);
    }

    private void initCategoryLayout() {
        addEditText1.setInitKeyAndHint("分类名称", "");
        addEditText2.setInitKeyAndHint("分类描述", "");
        addEditText3.setInitKeyAndHint("排序(数字越大越靠前)", "");
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("添加分类", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
//        publicTopBar.setTopBarRightTextView("增加", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
    }

    private UpdateCetegoryBean checkCategoryParams(){
        String name = addEditText1.getValue();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showInfo("名称不能为空");
            return null;
        }
        String description = addEditText2.getValue();
        String sort_order = addEditText3.getValue();
        int sortOrder = THNTypeConversionUtils.StringConvertInt(sort_order);
        return new UpdateCetegoryBean(name, description, sortOrder);
    }

    /**
     * 更新
     */
    private void save(UpdateCetegoryBean categoryBean) {
        if (categoryBean == null) {
            return;
        }
        String url = URL.PRODUCT_CATEGORY;
        HashMap<String, Object> brandUpdateParams = ClientParamsAPI.getCategoryUpdateParams(categoryBean);
        HttpRequest.sendRequest(HttpRequest.POST, url, brandUpdateParams, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                BrandResultBean customerBean = JsonUtil.fromJson(json, BrandResultBean.class);
                if (customerBean.getSuccess()) {
                    ToastUtils.showSuccess(customerBean.getStatus().getMessage());
                    setResult(Activity.RESULT_OK);
                    GoodsCategoryAddActivity.this.finish();
                } else {
                    ToastUtils.showError(customerBean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    @OnClick(R.id.textView_goods_category_edit_save)
    public void onViewClicked() {
        save(checkCategoryParams());
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }
}
