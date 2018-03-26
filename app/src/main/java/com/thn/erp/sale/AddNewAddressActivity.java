package com.thn.erp.sale;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.DataConstants;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.sale.bean.DeleteAddressData;
import com.thn.erp.sale.bean.ProvinceCityRestrict;
import com.thn.erp.sale.bean.TownsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomHeadView;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author lilin
 *         created at 2016/10/26 14:11
 */
public class AddNewAddressActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.et_consignee_name)
    EditText etConsigneeName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_zip_code)
    EditText etZipCode;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_details)
    EditText etAddressDetails;
    @BindView(R.id.ibtn_set_default)
    ImageButton ibtnSetDefault;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    //从选择地址界面传递过来的数据
    private AddressData.DataBean addressBean;
    private String provinceId = "0";
    private String cityId = "0";
    private String countyId = "0";
    private String townId = "0";
    private boolean isdefault = true;//设置此地址是否为默认地址
    private THNWaittingDialog dialog;
    private AddressSelectFragment addressSelectFragment;
    private ProvinceCityRestrict.DataBean curProvince;
    private ProvinceCityRestrict.DataBean curCity;
    private ProvinceCityRestrict.DataBean curCounty;
    private TownsData.DataBean curVillage;
    private boolean isAddressSelected;


    @Override
    protected int getLayout() {
        return R.layout.activity_add_new_address;
    }

    @Override
    protected void getIntentData() {
        AddressData.DataBean extra= getIntent().getParcelableExtra(CreateOrderActivity.class.getSimpleName());
        if (extra!=null) {
            addressBean = extra;
        }
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(this);
        if (addressBean != null) {
            customHead.setHeadCenterTxtShow(true, R.string.edit_address);
            customHead.setHeadRightTxtShow(true, R.string.delete);
            customHead.getHeadRightTV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DefaultDialog(AddNewAddressActivity.this, getResources().getString(R.string.hint_dialog_delete_address_title), getResources().getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {

                        @Override
                        public void clickRight() {
                            HashMap<String, String> params = ClientParamsAPI.deleteAddressParams();
                            Call httpHandler = HttpRequest.sendRequest(HttpRequest.DELETE,URL.DELETE_ADDRESS, params,new HttpRequestCallback() {
                                @Override
                                public void onStart() {
                                    if (dialog != null && !activity.isFinishing()) dialog.show();
                                }

                                @Override
                                public void onSuccess(String json) {
                                    DeleteAddressData deleteAddressData = JsonUtil.fromJson(json, DeleteAddressData.class);
                                    if (deleteAddressData.success) {
                                        ToastUtils.showSuccess("删除成功");
                                        Intent intent = new Intent();
                                        intent.putExtra("address", 1);
                                        setResult(DataConstants.RESULTCODE_ADDNEWADDRESS, intent);
                                        finish();
                                    } else {
                                        ToastUtils.showError(deleteAddressData.status.message);
                                    }
                                }

                                @Override
                                public void onFailure(IOException e) {
                                    dialog.dismiss();
                                    ToastUtils.showError(R.string.network_err);
                                }
                            });
                        }
                    });
                }
            });
            etConsigneeName.setText(addressBean.full_name);
            etPhone.setText(addressBean.phone);
            String builder = addressBean.province +
                    " " +
                    addressBean.city +
                    " " +
                    addressBean.town +
                    " " +
                    addressBean.area;
            tvAddress.setText(builder);
//            provinceId = addressBean.province_id;
//            cityId = addressBean.city_id;
//            townId = addressBean.town_id;
            etAddressDetails.setText(addressBean.street_address);
//            etZipCode.setText(addressBean.);
            if (!addressBean.is_default) {
                isdefault = false;
//                ibtnSetDefault.setImageResource(R.mipmap.switch_off);
            } else {
                isdefault = true;
//                ibtnSetDefault.setImageResource(R.mipmap.switch_on);
            }
        } else {
            customHead.setHeadCenterTxtShow(true, R.string.new_consignee_address);
        }
    }

    @Override
    protected void requestNet() {

    }

    @OnClick({R.id.tv_address, R.id.btn_commit, R.id.ibtn_set_default})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                if (addressSelectFragment == null) {
                    addressSelectFragment = new AddressSelectFragment();
                    addressSelectFragment.show(activity.getFragmentManager(), AddressSelectFragment.class.getSimpleName());
                    addressSelectFragment.setOnAddressChoosedListener(new AddressSelectFragment.OnAddressChoosedListener() {
                        @Override
                        public void onAddressChoosed(ProvinceCityRestrict.DataBean province, ProvinceCityRestrict.DataBean city, ProvinceCityRestrict.DataBean county, TownsData.DataBean village) {
                            StringBuilder builder = new StringBuilder();
                            if (province != null) {
                                builder.append(province.name);
                                builder.append(" ");
                                curProvince = province;
                            }
                            if (city != null) {
                                builder.append(city.name);
                                builder.append(" ");
                                curCity = city;
                            }
                            if (county != null) {
                                builder.append(county.name);
                                builder.append(" ");
                                curCounty = county;
                            }
                            if (village != null) {
                                builder.append(village.name);
                                builder.append(" ");
                                curVillage = village;
                            }
                            tvAddress.setText(builder.toString());
                            isAddressSelected=true;
                        }

//                        @Override
//                        public void onAddressChoosed(ProvinceCityRestrict. province, ProvinceCityRestrict city, ProvinceCityRestrict county, VillagesData.DataBean village) {
//                            StringBuilder builder = new StringBuilder();
//                            if (province != null) {
//                                builder.append(province.n);
//                                builder.append(" ");
//                                curProvince = province;
//                            }
//                            if (city != null) {
//                                builder.append(city.name);
//                                builder.append(" ");
//                                curCity = city;
//                            }
//                            if (county != null) {
//                                builder.append(county.name);
//                                builder.append(" ");
//                                curCounty = county;
//                            }
//                            if (town != null) {
//                                builder.append(town.name);
//                                builder.append(" ");
//                                curTown = town;
//                            }
//                            tvAddress.setText(builder.toString());
//                            isAddressSelected=true;
//                        }
                    });
                } else {
                    addressSelectFragment.show(activity.getFragmentManager(), AddressSelectFragment.class.getSimpleName());
                }
                break;
            case R.id.ibtn_set_default:
                if (!isdefault) {
//                    ibtnSetDefault.setImageResource(R.mipmap.switch_on);
                    isdefault = true;
                } else {
//                    ibtnSetDefault.setImageResource(R.mipmap.switch_off);
                    isdefault = false;
                }
                break;
            case R.id.btn_commit:
                commitConsigneeAddress();
                break;
            default:
                break;
        }
    }

    private void commitConsigneeAddress() {
        String consigneeName = etConsigneeName.getText().toString().trim();
        if (TextUtils.isEmpty(consigneeName)) {
            ToastUtils.showInfo(R.string.name_is_empty);
            return;
        }

        String phone = etPhone.getText().toString().trim();
//        if (TextUtils.isEmpty(phone)) {
//            ToastUtils.showInfo(R.string.phone_is_empty);
//            return;
//        }
//
//        if (!Util.isMobileNO(etPhone.getText().toString())) {
//            ToastUtils.showInfo(R.string.phone_is_err);
//            return;
//        }

        if (TextUtils.equals(tvAddress.getText(), getResources().getString(R.string.please_select_address))) {
            ToastUtils.showInfo(R.string.please_select_address);
            return;
        }

        String addressDetail = etAddressDetails.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showInfo(R.string.address_details_is_empty);
            return;
        }

//        if (isAddressSelected){
//            if (curProvince != null) {
//                provinceId = String.valueOf(curProvince.oid);
//            } else {
//                provinceId = "0";
//            }
//            if (curCity != null) {
//                cityId = String.valueOf(curCity.oid);
//            } else {
//                cityId = "0";
//            }
//            if (curCounty != null) {
//                countyId = String.valueOf(curCounty.oid);
//            } else {
//                countyId = "0";
//            }
//            if (curTown != null) {
//                townId = String.valueOf(curTown.oid);
//            }else {
//                townId = "0";
//            }
//        }else {
//            if (addressBean!=null){
//                provinceId=addressBean.province_id;
//                cityId=addressBean.city_id;
//                countyId=addressBean.county_id;
//                townId=addressBean.town_id;
//            }else {
//                ToastUtils.showError(R.string.network_err);
//            }
//        }
//        String is_default;
//        if (isdefault) {
//            is_default = "1";
//        } else {
//            is_default = "0";
//        }
//
//        String id;
//        if (addressBean == null) {
//            id = "";
//        } else {
//            id = addressBean._id;
//        }
//
//        HashMap<String, String> params = ClientParamsAPI.commitAddressParams(id, consigneeName, phone, provinceId, cityId, countyId, townId, addressDetail, etZipCode.getText().toString(), is_default);
//        HttpRequest.sendRequest(HttpRequest.POST,params, URL.URLSTRING_NEW_ADDRESS, new GlobalDataCallBack(){
//            @Override
//            public void onSuccess(String json) {
//                dialog.dismiss();
//                HttpResponse netBean = JsonUtil.fromJson(json, HttpResponse.class);
//                if (netBean.isSuccess()) {
//                    ToastUtils.showSuccess(netBean.getMessage());
//                    Intent intent = new Intent();
//                    intent.putExtra("address", 1);
//                    setResult(DataConstants.RESULTCODE_ADDNEWADDRESS, intent);
//                    finish();
//                } else {
//                    ToastUtils.showError(netBean.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                dialog.dismiss();
//                ToastUtils.showError(R.string.network_err);
//            }
//        });
    }
}
