package com.thn.erp.more.supplier;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.overview.ChooseCategoryActivity;
import com.thn.erp.view.common.LinearLayoutCustomerAddArrowView;
import com.thn.erp.view.common.LinearLayoutCustomerAddEditTextView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.ielse.view.SwitchView;

/**
 * Created by Stephen on 2018/3/22 17:07
 * Email: 895745843@qq.com
 */

public class SupplierLookActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {
    public static final String[] BASIC_INFOS = {"供应商名称", "联系人", "手机", "分类", "启用"}; // 基本信息
    public static final String[] BASIC_INFOS_VALUES = {"太火红鸟科技有限公司", "田帅", "15001120509", "电商", "开"}; // 基本信息
    public static final String[] Additional_INFOS = {"电话", "邮箱", "传真", "性别", "生日"}; // 扩展信息
    public static final String[] Additional_INFOS_VALUES = {"400-83846", "tianshuai@tiahuoniao.com", "86  519-85125379", "男", "1989/09/02"}; // 扩展信息
    public static final String[] Address_INFOS = {"省市", "城市", "区县", "详细地址", "备注"}; // 地址信息
    public static final String[] Address_INFOS_VALUES = {"北京市", "北京市", "朝阳区", "酒仙桥798艺术设计广场B7", "BalaBalaBala"}; // 地址信息

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.editTextView_customer_add_basic_info1)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddBasicInfo1;
    @BindView(R.id.editTextView_customer_add_basic_info2)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddBasicInfo2;
    @BindView(R.id.editTextView_customer_add_basic_info3)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddBasicInfo3;
    @BindView(R.id.editTextView_customer_add_basic_info4)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddBasicInfo4;
    @BindView(R.id.editTextView_customer_add_basic_info5)
    LinearLayoutCustomerAddSwitchView editTextViewCustomerAddBasicInfo5;
    @BindView(R.id.editTextView_customer_add_additional_info1)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddAdditionalInfo1;
    @BindView(R.id.editTextView_customer_add_additional_info2)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddAdditionalInfo2;
    @BindView(R.id.editTextView_customer_add_additional_info3)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddAdditionalInfo3;
    @BindView(R.id.editTextView_customer_add_additional_info4)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddAdditionalInfo4;
    @BindView(R.id.editTextView_customer_add_additional_info5)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddAdditionalInfo5;
    @BindView(R.id.editTextView_customer_add_address_info1)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddAddressInfo1;
    @BindView(R.id.editTextView_customer_add_address_info2)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddAddressInfo2;
    @BindView(R.id.editTextView_customer_add_address_info3)
    LinearLayoutCustomerAddArrowView editTextViewCustomerAddAddressInfo3;
    @BindView(R.id.editTextView_customer_add_address_info4)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddAddressInfo4;
    @BindView(R.id.editTextView_customer_add_address_info5)
    LinearLayoutCustomerAddEditTextView editTextViewCustomerAddAddressInfo5;
    @BindView(R.id.linearLayout_repository_delete_repository)
    LinearLayout linearLayoutRepositoryDeleteRepository;
    @BindView(R.id.linearLayout_container)
    LinearLayout linearLayoutContainer;

    private int status = Status.LOOK;

    @Override
    protected int getLayout() {
        return R.layout.activity_more_manage_supplier_look;
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
        initBasicInfos();
        initAdditionalInfos();
        initAddressInfos();

        setEnabled(false);  // 禁止触发事件
    }

    private void initTopbar() {
        publicTopBar.setTopBarCenterTextView("新增供应商", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("编辑",getResources().getColor(R.color.white));
        publicTopBar.setTopBarOnClickListener(this);
    }

    /**
     * 初始化基本信息
     */
    private void initBasicInfos() {
        editTextViewCustomerAddBasicInfo1.setName(BASIC_INFOS[0]);
        editTextViewCustomerAddBasicInfo2.setName(BASIC_INFOS[1]);
        editTextViewCustomerAddBasicInfo3.setName(BASIC_INFOS[2]);
        editTextViewCustomerAddBasicInfo4.setInitKeyAndHint(BASIC_INFOS[3], new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupplierLookActivity.this, ChooseCategoryActivity.class));
            }
        });
        editTextViewCustomerAddBasicInfo5.setInitKeyAndHint(BASIC_INFOS[4], new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {

            }

            @Override
            public void toggleToOff(SwitchView view) {

            }
        });

        editTextViewCustomerAddBasicInfo1.setValue(BASIC_INFOS_VALUES[0]);
        editTextViewCustomerAddBasicInfo2.setValue(BASIC_INFOS_VALUES[1]);
        editTextViewCustomerAddBasicInfo3.setValue(BASIC_INFOS_VALUES[2]);
        editTextViewCustomerAddBasicInfo4.setValue(BASIC_INFOS_VALUES[3]);
        editTextViewCustomerAddBasicInfo5.setValue(BASIC_INFOS_VALUES[4]);
    }

    /**
     * 初始化扩展信息
     */
    private void initAdditionalInfos() {
        editTextViewCustomerAddAdditionalInfo1.setName(Additional_INFOS[0]);
        editTextViewCustomerAddAdditionalInfo2.setName(Additional_INFOS[1]);
        editTextViewCustomerAddAdditionalInfo3.setName(Additional_INFOS[2]);
        editTextViewCustomerAddAdditionalInfo4.setInitKeyAndHint(Additional_INFOS[3], new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextViewCustomerAddAdditionalInfo5.setInitKeyAndHint(Additional_INFOS[4], new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextViewCustomerAddAdditionalInfo1.setValue(Additional_INFOS_VALUES[0]);
        editTextViewCustomerAddAdditionalInfo2.setValue(Additional_INFOS_VALUES[1]);
        editTextViewCustomerAddAdditionalInfo3.setValue(Additional_INFOS_VALUES[2]);
        editTextViewCustomerAddAdditionalInfo4.setValue(Additional_INFOS_VALUES[3]);
        editTextViewCustomerAddAdditionalInfo5.setValue(Additional_INFOS_VALUES[4]);
    }

    /**
     * 初始化地址信息
     */
    private void initAddressInfos() {
        editTextViewCustomerAddAddressInfo1.setInitKeyAndHint(Address_INFOS[0], new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextViewCustomerAddAddressInfo2.setInitKeyAndHint(Address_INFOS[1], new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextViewCustomerAddAddressInfo3.setInitKeyAndHint(Address_INFOS[2], new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editTextViewCustomerAddAddressInfo4.setName(Address_INFOS[3]);
        editTextViewCustomerAddAddressInfo5.setName(Address_INFOS[4]);

        editTextViewCustomerAddAddressInfo1.setValue(Address_INFOS_VALUES[0]);
        editTextViewCustomerAddAddressInfo2.setValue(Address_INFOS_VALUES[1]);
        editTextViewCustomerAddAddressInfo3.setValue(Address_INFOS_VALUES[2]);
        editTextViewCustomerAddAddressInfo4.setValue(Address_INFOS_VALUES[3]);
        editTextViewCustomerAddAddressInfo5.setValue(Address_INFOS_VALUES[4]);
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
                switch (status) {
                    case Status.LOOK:
                        setEnabled(true);
                        linearLayoutRepositoryDeleteRepository.setVisibility(View.VISIBLE);
                        publicTopBar.setTopBarRightTextView("确认", Color.parseColor("#27AE59"));
                        status = Status.EDIT;
                        break;
                    case Status.EDIT:
                        Toast.makeText(activity, "确认", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    static class Status {
        public static final int LOOK = 0;
        public static final int EDIT = 1;
    }

    private void setEnabled(boolean enabled){
        editTextViewCustomerAddBasicInfo2.setEnabled(enabled);
        editTextViewCustomerAddBasicInfo3.setEnabled(enabled);
        editTextViewCustomerAddBasicInfo4.setEnabled(enabled);
        editTextViewCustomerAddBasicInfo5.setEnabled(enabled);

        editTextViewCustomerAddAdditionalInfo1.setEnabled(enabled);
        editTextViewCustomerAddAdditionalInfo2.setEnabled(enabled);
        editTextViewCustomerAddAdditionalInfo3.setEnabled(enabled);
        editTextViewCustomerAddAdditionalInfo4.setEnabled(enabled);
        editTextViewCustomerAddAdditionalInfo5.setEnabled(enabled);

        editTextViewCustomerAddAddressInfo1.setEnabled(enabled);
        editTextViewCustomerAddAddressInfo2.setEnabled(enabled);
        editTextViewCustomerAddAddressInfo3.setEnabled(enabled);
        editTextViewCustomerAddAddressInfo4.setEnabled(enabled);
        editTextViewCustomerAddAddressInfo5.setEnabled(enabled);

        editTextViewCustomerAddBasicInfo1.setEnabled(enabled);
    }
}
