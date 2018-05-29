package com.thn.erp.goods.category;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.thn.basemodule.tools.ToastUtil;
import com.thn.basemodule.tools.WaitingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.constant.ExtraKey;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.goods.brand.entity.BrandResultBean;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.basemodule.tools.JsonUtil;
import com.thn.basemodule.tools.LogUtil;
import com.thn.erp.view.common.LinearLayoutCustomerAddEditTextView;
import com.thn.erp.view.common.PublicTopBar;

import java.io.IOException;

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
    @BindView(R.id.textView_goods_category_edit_modify)
    TextView textViewGoodsCategoryEditSave;

    private WaitingDialog dialog;

    private GoodsCategoryData.DataEntity.CategoriesEntity categoriesEntity;

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
        if (categoriesEntity != null) {
            addEditText1.setInitKeyAndValue("分类名称", categoriesEntity.getName());
            addEditText2.setInitKeyAndValue("分类描述", categoriesEntity.getDescription());
            addEditText3.setInitKeyAndValue("排序(数字越大越靠前)", "" + categoriesEntity.getSort_order());
        } else {
            addEditText1.setInitKeyAndHint("分类名称", "");
            addEditText2.setInitKeyAndHint("分类描述", "");
            addEditText3.setInitKeyAndHint("排序(数字越大越靠前)", "");
        }
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView((categoriesEntity == null) ?  "添加分类": "编辑分类", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
//        publicTopBar.setTopBarRightTextView("添加", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
    }

    private UpdateCetegoryBean checkCategoryParams(){
        String name = addEditText1.getValue();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showInfo("名称不能为空");
            return null;
        }
        String description = addEditText2.getValue();
        String sort_order = addEditText3.getValue();
        int sortOrder = Integer.valueOf(sort_order);
        return new UpdateCetegoryBean(name, description, sortOrder);
    }

    @OnClick(R.id.textView_goods_category_edit_modify)
    public void onViewClicked() {
        save(checkCategoryParams());
    }

    @Override
    public void onTopBarClick(View view, int position) {

    }

    @Override
    protected void getIntentData() {
        categoriesEntity = getIntent().getParcelableExtra(ExtraKey.CATEGORY_BEAN);
    }

    /**
     * 请求接口
     * @param categoryBean
     */
    private void save(UpdateCetegoryBean categoryBean) {
        if (categoryBean == null) {
            return;
        }
        String url;
        String method;
        boolean b = categoriesEntity == null;
        if (b) {
            url = URL.PRODUCT_CATEGORY;
            method = HttpRequest.POST;
        } else {
            url = URL.PRODUCT_CATEGORY + "/" + categoriesEntity.getId();
            method = HttpRequest.PUT;
        }
        HttpRequest.sendRequest(method, url, ClientParamsAPI.getCategoryUpdateParams(categoryBean), new HttpRequestCallback() {
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
                    ToastUtil.showSuccess(customerBean.getStatus().getMessage());
                    setResult(Activity.RESULT_OK);
                    GoodsCategoryAddActivity.this.finish();
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
