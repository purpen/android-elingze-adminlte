package com.thn.erp.goods.brand;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.goods.brand.entity.BrandResultBean;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.net.paramsBean.UpdateBrandBean;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;
import com.thn.erp.view.svprogress.WaitingDialog;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/4/8 17:45
 * Email: 895745843@qq.com
 */

public class BrandDetailsActivity extends BaseActivity implements ImpTopbarOnClickListener {
    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.linearLayout_repository_delete_repository)
    LinearLayout linearLayoutRepositoryDeleteRepository;
    @BindView(R.id.textView1)
    EditText textView1;
    @BindView(R.id.textView2)
    EditText textView2;
    @BindView(R.id.textView3)
    EditText textView3;
    @BindView(R.id.textView4)
    EditText textView4;
    @BindView(R.id.linearLayoutSwitchView1)
    LinearLayoutCustomerAddSwitchView linearLayoutSwitchView1;
    @BindView(R.id.textView5)
    EditText textView5;
    @BindView(R.id.button_edit_confirm)
    TextView buttonEditConfirm;
    @BindView(R.id.button_edit_cancel)
    TextView buttonEditCancel;

    private WaitingDialog dialog;
    private String brandId;
    private String brandName;

    private boolean isEditable; //默认不可编辑

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_brand_detials;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void getIntentData() {
        brandId = getIntent().getStringExtra(ExtraKey.BRAND_ID);
        brandName = getIntent().getStringExtra(ExtraKey.BRAND_NAME);
    }


    @Override
    protected void initView() {
        initTopBar();
        setLookStatus();
    }

    private void initTopBar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("品牌详情", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("编辑", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
    }

    @Override
    protected void requestNet() {
        dialog = new WaitingDialog(this);
        getBrandDetails();
    }

    private void getBrandDetails() {
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.PRODUCT_BRAND + "/" + brandId;
        HttpRequest.sendRequest(HttpRequest.GET, url, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                LogUtil.e(json);
                dialog.dismiss();
                BrandBean customerBean = JsonUtil.fromJson(json, BrandBean.class);
                if (customerBean.getSuccess()) {
                    refreshUi(customerBean.getData());
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

    private void refreshUi(BrandBean.DataEntity data) {
        THNGlideUtil.displayImage(data.getBanner(), imageView);
        THNGlideUtil.displayImage(data.getLogo(), imageView2);
        textView1.setText(data.getDescription());
        textView2.setText(data.getFeatures());
        textView3.setText(data.getName());
    }

    @Override
    public void onTopBarClick(View view, int position) {
        switch (position) {
            case ImpTopbarOnClickListener.LEFT:
                this.finish();
                break;
            case ImpTopbarOnClickListener.CENTER:
                break;
            case ImpTopbarOnClickListener.RIGHT:
                isEditable = !isEditable;
                refreshUi(isEditable);
                break;
        }
    }

    @OnClick({R.id.button_edit_confirm, R.id.button_edit_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_edit_confirm:
                update();
                ToastUtils.showInfo("保存");
                break;
            case R.id.button_edit_cancel:
                delete();
                ToastUtils.showInfo("取消");
                break;
        }
    }

    /**
     * 删除
     */
    private void delete() {
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.PRODUCT_BRAND + "/" + brandId;
        HttpRequest.sendRequest(HttpRequest.DELETE, url, params, new HttpRequestCallback() {
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

    /**
     * 更新
     */
    private void update() {
        String url = URL.PRODUCT_BRAND + "/" + brandId;
        String name = textView1.getText().toString();
        String features = textView2.getText().toString();
        String description = textView3.getText().toString();
        String supplier_id = textView4.getText().toString();
        boolean is_recommended = linearLayoutSwitchView1.getSwitchStatus();
        String sort_order = textView5.getText().toString();

        int supplierId = TextUtils.isEmpty(supplier_id) ? 0 : Integer.valueOf(supplier_id);
        int sortOrder = TextUtils.isEmpty(sort_order) ? 1 : Integer.valueOf(sort_order);

        UpdateBrandBean brandBean = new UpdateBrandBean(name, features, description, supplierId, is_recommended, sortOrder);
        HashMap<String, Object> brandUpdateParams = ClientParamsAPI.getBrandUpdateParams(brandBean);
        HttpRequest.sendRequest(HttpRequest.PUT, url, brandUpdateParams, new HttpRequestCallback() {
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

    /**
     * 刷新UI状态
     * @param isEditable 当前状态
     */
    private void refreshUi(boolean isEditable) {
        if (isEditable) {
            setEditable();
        } else {
            setLookStatus();
        }
    }

    /**
     * 查看状态
     */
    private void setLookStatus() {
        textView1.setEnabled(false);
        textView2.setEnabled(false);
        textView3.setEnabled(false);
        textView4.setEnabled(false);
        linearLayoutSwitchView1.setEnabled(false);
        textView5.setEnabled(false);
        linearLayoutRepositoryDeleteRepository.setVisibility(View.GONE);
        publicTopBar.setTopBarRightTextView("编辑", Color.parseColor("#27AE59"));
    }

    /**
     * 编辑状态
     */
    private void setEditable() {
        textView1.setEnabled(true);
        textView2.setEnabled(true);
        textView3.setEnabled(true);
        textView4.setEnabled(true);
        linearLayoutSwitchView1.setEnabled(true);
        textView5.setEnabled(true);
        linearLayoutRepositoryDeleteRepository.setVisibility(View.VISIBLE);
        publicTopBar.setTopBarRightTextView("取消", Color.parseColor("#27AE59"));
    }
}
