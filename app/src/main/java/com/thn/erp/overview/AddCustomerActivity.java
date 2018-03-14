package com.thn.erp.overview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.view.common.LinearLayoutCustomerAddArrowView;
import com.thn.erp.view.common.LinearLayoutCustomerAddEditTextView;
import com.thn.erp.view.common.LinearLayoutCustomerAddSwitchView;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.ielse.view.SwitchView;

/**
 * Created by Stephen on 2018/3/14 16:27
 * Email: 895745843@qq.com
 */

public class AddCustomerActivity extends BaseActivity implements ImpTopbarOnClickListener {

    public static final String[] BASIC_INFOS = {"客户名称", "联系人", "手机", "分类", "启用"}; // 基本信息
    public static final String[] Additional_INFOS = {"电话", "邮箱", "传真", "性别","生日"}; // 扩展信息
    public static final String[] Address_INFOS = {"省市","城市","区县", "详细地址", "备注"}; // 地址信息

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

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_add;
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
    }

    private void initTopbar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("新增客户", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(R.mipmap.ic_launcher);
        publicTopBar.setTopBarRightTextView("保存", Color.parseColor("#27AE59"));
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
    }

    @Override
    public void onTopBarClick(View view, int position) {
        if (position == ImpTopbarOnClickListener.LEFT) {
            this.finish();
        }else if (position == ImpTopbarOnClickListener.RIGHT) {
            Toast.makeText(activity, "保存", Toast.LENGTH_SHORT).show();
        }
    }
}
