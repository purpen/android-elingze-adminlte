package com.thn.erp.goods.brand;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.thn.basemodule.tools.ToastUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.goods.brand.entity.BrandResultBean;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.net.paramsBean.UpdateBrandBean;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.view.common.LinearLayoutCustomerAddEditTextView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/4/11 19:03
 * Email: 895745843@qq.com
 */

public class GoodsBrandAddActivity extends BaseActivity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.addEditText1)
    LinearLayoutCustomerAddEditTextView addEditText1;
    @BindView(R.id.addEditText2)
    LinearLayoutCustomerAddEditTextView addEditText2;
    @BindView(R.id.addEditText3)
    LinearLayoutCustomerAddEditTextView addEditText3;
    @BindView(R.id.addEditText4)
    LinearLayoutCustomerAddEditTextView addEditText4;
    @BindView(R.id.switchView1)
    LinearLayoutCustomerAddSwitchView switchView1;
    @BindView(R.id.addEditText6)
    LinearLayoutCustomerAddEditTextView addEditText6;
    @BindView(R.id.button_edit_confirm)
    TextView buttonEditConfirm;

    private WaitingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_brand_add;
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
        initEditLayout();
        dialog = new WaitingDialog(this);
    }

    private void initEditLayout() {
        addEditText1.setInitKeyAndHint("品牌名称", "");
        addEditText2.setInitKeyAndHint("品牌亮点", "");
        addEditText3.setInitKeyAndHint("品牌说明", "");
        addEditText4.setInitKeyAndHint("所属供应商", "");
        switchView1.setInitKeyAndHint("是否推荐", null);
        addEditText6.setInitKeyAndHint("排序(数字越大越靠前)", "");
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("添加品牌", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarOnClickListener(this);
    }

    @OnClick(R.id.button_edit_confirm)
    public void onViewClicked() {
        update(checkInputParams());
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }

    private UpdateBrandBean checkInputParams(){
        String name = addEditText1.getValue();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showInfo("名称不能为空");
            return null;
        }
        String features = addEditText2.getValue();
        String description = addEditText3.getValue();
        String supplier_id = addEditText4.getValue();
        boolean is_recommended = switchView1.getSwitchStatus();
        String sort_order = addEditText6.getValue();
        int supplierId = Integer.valueOf(supplier_id);
        int sortOrder = Integer.valueOf(sort_order);
        return new UpdateBrandBean(name, features, description, supplierId, is_recommended, sortOrder);
    }

    /**
     * 更新
     */
    private void update(UpdateBrandBean brandBean) {
        if (brandBean == null) {
            return;
        }
        String url = URL.PRODUCT_BRAND ;
        HashMap<String, Object> brandUpdateParams = ClientParamsAPI.getBrandUpdateParams(brandBean);
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
                    setResult(-1);
                    finish();
                    ToastUtil.showSuccess(customerBean.getStatus().getMessage());
                } else {
                    ToastUtil.showError(customerBean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtil.showError(R.string.network_err);
            }
        });
    }
}
