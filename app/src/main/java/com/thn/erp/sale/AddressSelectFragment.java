package com.thn.erp.sale;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.adapter.SimpleTextAdapter;
import com.thn.erp.sale.adapter.TownsListAdapter;
import com.thn.erp.sale.bean.ProvinceCityRestrict;
import com.thn.erp.sale.bean.TownsData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.LogUtil;
import com.thn.erp.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/10/25 20:16
 */
public class AddressSelectFragment extends DialogFragment {
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_county)
    TextView tvCounty;
    @BindView(R.id.tv_town)
    TextView tvTown;
    @BindView(R.id.recycler_view_province)
    RecyclerView recyclerViewProvince;
    @BindView(R.id.recycler_view_city)
    RecyclerView recyclerViewCity;
    @BindView(R.id.recycler_view_county)
    RecyclerView recyclerViewCounty;
    @BindView(R.id.recycler_view_town)
    RecyclerView recyclerViewTown;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private List<ProvinceCityRestrict.DataBean> provinceList;
    private List<ProvinceCityRestrict.DataBean> cityList;
//    县级
    private List<ProvinceCityRestrict.DataBean> countyList;
//    镇
    private List<TownsData.DataBean> townsList;
    private static final int PROVINCE_TYPE = 1;
    private static final int CITY_TYPE = 2;
    private static final int COUNTY_TYPE = 3;
    private static final int TOWN_TYPE = 4;
    private static final int OTHERS_TYPE = 5;
    private SimpleTextAdapter adapterProvince;
    private SimpleTextAdapter adapterCity;
    private SimpleTextAdapter adapterCounty;
    private TownsListAdapter adapterTown;
    private ProvinceCityRestrict.DataBean curProvince;
    private ProvinceCityRestrict.DataBean curCity;
    private ProvinceCityRestrict.DataBean curCounty;
    private TownsData.DataBean curTowns;
    private int oid;
    private int pid;
    private int layer;
    private Dialog dialog;
    private OnAddressChoosedListener listener;
    public interface OnAddressChoosedListener {
        void onAddressChoosed(ProvinceCityRestrict.DataBean province, ProvinceCityRestrict.DataBean city, ProvinceCityRestrict.DataBean county, TownsData.DataBean village);
    }

    public void setOnAddressChoosedListener(OnAddressChoosedListener listener){
        this.listener=listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (dialog==null){
            dialog = new Dialog(getActivity(), R.style.BottomDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = dialog.getWindow();
            dialog.setContentView(R.layout.view_bottom_list);
            dialog.setCanceledOnTouchOutside(true);
            if (window != null) {
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM;
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height= getResources().getDimensionPixelSize(R.dimen.dp300);
                window.setAttributes(wlp);
            }
            ButterKnife.bind(this, dialog);
            initData();
            installListener();
        }
        return dialog;
    }

    private void initData() {
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        countyList = new ArrayList<>();
        townsList = new ArrayList<>();
        //省
        adapterProvince = new SimpleTextAdapter(getActivity(), provinceList);
        recyclerViewProvince.setAdapter(adapterProvince);
        recyclerViewProvince.setLayoutManager(new LinearLayoutManager(getActivity()));
        //市
        adapterCity = new SimpleTextAdapter(getActivity(), cityList);
        recyclerViewCity.setAdapter(adapterCity);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));

        //区、县
        adapterCounty = new SimpleTextAdapter(getActivity(), countyList);
        recyclerViewCounty.setAdapter(adapterCounty);
        recyclerViewCounty.setLayoutManager(new LinearLayoutManager(getActivity()));

        //乡,镇
        adapterTown = new TownsListAdapter(getActivity(), townsList);
        recyclerViewTown.setAdapter(adapterTown);
        recyclerViewTown.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestNet();
    }

    private void installListener() {
        if (adapterProvince != null) {
            adapterProvince.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //获取省的下一级市
                    if (provinceList == null || provinceList.size() == 0) return;
                    curProvince = provinceList.get(position);

                    if (!TextUtils.equals(tvProvince.getText(), curProvince.name)) {
                        tvCity.setText(R.string.please_select);
                        tvCity.setVisibility(View.VISIBLE);
                        curCity=null;
                        tvCounty.setVisibility(View.GONE);
                        tvCounty.setText(R.string.please_select);
                        curCounty=null;
                        tvTown.setVisibility(View.GONE);
                        tvTown.setText(R.string.please_select);
                        curTowns=null;
                    }
                    tvProvince.setText(curProvince.name);
                    oid = curProvince.oid;
                    getCities();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

        if (adapterCity != null) { //市被点击
            adapterCity.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //获取市下一级县/区
                    if (cityList == null || cityList.size() == 0) return;
                    curCity = cityList.get(position);
                    if (!TextUtils.equals(tvCity.getText(), curCity.name)) {
                        tvCounty.setText(R.string.please_select);
                        tvCounty.setVisibility(View.VISIBLE);
                        curCounty=null;
                        tvTown.setVisibility(View.GONE);
                        tvTown.setText(R.string.please_select);
                        curTowns=null;
                    }
                    tvCity.setVisibility(View.VISIBLE);
                    tvCity.setText(curCity.name);
                    oid = curCity.oid;
                    getRestrictCounty();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

        if (adapterCounty != null) { //点击县/区获取乡镇
            adapterCounty.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (countyList == null || countyList.size() == 0) return;
                    curCounty = countyList.get(position);
                    if (!TextUtils.equals(tvCounty.getText(), curCounty.name)) {
                        tvCounty.setVisibility(View.VISIBLE);
                        tvTown.setText(R.string.please_select);
                        curTowns=null;
                    }
                    tvCounty.setVisibility(View.VISIBLE);
                    tvCounty.setText(curCounty.name);
                    oid = curCounty.oid;
                    getTowns();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }

        if (adapterTown != null) { //点击乡镇item
            adapterTown.setOnItemClickListener(new TownsListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (townsList == null || townsList.size() == 0) return;
                    curTowns = townsList.get(position);
                    tvTown.setVisibility(View.VISIBLE);
                    tvTown.setText(curTowns.name);
                    oid = curTowns.oid;
                    layer = OTHERS_TYPE;
//                    选完镇地址，直接设置
                    setAddressData();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }
    }

    /**
     * app暂时最多四级，设置地址数据
     */
    private void setAddressData() {
        if (listener!=null){
            listener.onAddressChoosed(curProvince,curCity,curCounty,curTowns);
        }
        dismiss();
    }

    /**
     * 获取镇列表
     */
    private void getTowns() {
        layer = TOWN_TYPE ;
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.CITIES_LIST+oid+"/areas";
        LogUtil.e(url);
        HttpRequest.sendRequest(HttpRequest.GET,url,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(String json) {
                progressbar.setVisibility(View.GONE);
                TownsData townsData = JsonUtil.fromJson(json, TownsData.class);
                if (townsData.success){
                    townsList.clear();
                    townsList.addAll(townsData.data);
                    refreshUI();
                }else {
                    ToastUtils.showError(townsData.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
                setItemClickable(true);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 县级行政区
     */
    private void getRestrictCounty() {
        layer = COUNTY_TYPE;
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.CITIES_LIST+oid+"/towns";
        LogUtil.e(url);
        HttpRequest.sendRequest(HttpRequest.GET,url,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(String json) {
                progressbar.setVisibility(View.GONE);
                ProvinceCityRestrict provinceCityRestrict = JsonUtil.fromJson(json, ProvinceCityRestrict.class);
                if (provinceCityRestrict.success){
                    countyList.clear();
                    countyList.addAll(provinceCityRestrict.data);
                    refreshUI();
                }else {
                    ToastUtils.showError(provinceCityRestrict.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
                setItemClickable(true);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取城市列表
     */
    private void getCities() {
        layer = CITY_TYPE;
        HashMap<String, String> params = ClientParamsAPI.getDefaultParams();
        String url = URL.CITIES_LIST+oid+"/cities";
        LogUtil.e(url);
        HttpRequest.sendRequest(HttpRequest.GET,url,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(String json) {
                progressbar.setVisibility(View.GONE);
                ProvinceCityRestrict provinceCityRestrict = JsonUtil.fromJson(json, ProvinceCityRestrict.class);
                if (provinceCityRestrict.success){
                    cityList.clear();
                    cityList.addAll(provinceCityRestrict.data);
                    refreshUI();
                }else {
                    ToastUtils.showError(provinceCityRestrict.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
                setItemClickable(true);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void requestNet() {
        getProvinces();
    }

    /**
     * 获取省列表
     */
    private void getProvinces() {
        layer = PROVINCE_TYPE;
        HashMap<String, String> params = ClientParamsAPI.getProvinceParams();
        HttpRequest.sendRequest(HttpRequest.GET,URL.PROVINCE_LIST,params,new HttpRequestCallback() {
            @Override
            public void onStart() {
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(String json) {
                progressbar.setVisibility(View.GONE);
                ProvinceCityRestrict provinceCityRestrict = JsonUtil.fromJson(json, ProvinceCityRestrict.class);
                if (provinceCityRestrict.success){
                    provinceList.clear();
                    provinceList.addAll(provinceCityRestrict.data);
                    refreshUI();
                }else {
                    ToastUtils.showError(provinceCityRestrict.status.message);
                }
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showError(R.string.network_err);
                setItemClickable(true);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void refreshUI() {
        resetListUI();
        switch (layer) {
            case PROVINCE_TYPE:
                recyclerViewProvince.setVisibility(View.VISIBLE);
                if (adapterProvince == null) {
                    adapterProvince = new SimpleTextAdapter(getActivity(), provinceList);
                    recyclerViewProvince.setAdapter(adapterProvince);
                } else {
                    adapterProvince.notifyDataSetChanged();
                }
                break;
            case CITY_TYPE:
                recyclerViewCity.setVisibility(View.VISIBLE);
                if (adapterCity == null) {
                    adapterCity = new SimpleTextAdapter(getActivity(), cityList);
                    recyclerViewCity.setAdapter(adapterCity);
                } else {
                    adapterCity.notifyDataSetChanged();
                }
                break;
            case COUNTY_TYPE:
//                没有县一级数据直接设置地址同时将市级列表显示
                if (countyList.size()==0){
                    recyclerViewCity.setVisibility(View.VISIBLE);
                    setAddressData();
                    return;
                }
                recyclerViewCounty.setVisibility(View.VISIBLE);
                if (adapterCounty == null) {
                    adapterCounty = new SimpleTextAdapter(getActivity(), countyList);
                    recyclerViewCounty.setAdapter(adapterCounty);
                } else {
                    adapterCounty.notifyDataSetChanged();
                }
                break;
            case TOWN_TYPE:
//                没有镇一级数据直接设置地址同时将县级列表显示
                if (townsList.size()==0){
                    recyclerViewCounty.setVisibility(View.VISIBLE);
                    setAddressData();
                    return;
                }
                recyclerViewTown.setVisibility(View.VISIBLE);
                if (adapterTown == null) {
                    adapterTown = new TownsListAdapter(getActivity(), townsList);
                    recyclerViewTown.setAdapter(adapterTown);
                } else {
                    adapterTown.notifyDataSetChanged();
                }

                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_province, R.id.tv_city, R.id.tv_county, R.id.tv_town})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_province:
                resetListUI();
                recyclerViewProvince.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_city:
                resetListUI();
                recyclerViewCity.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_county:
                resetListUI();
                recyclerViewCounty.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_town:
                resetListUI();
                recyclerViewTown.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 隐藏地址列表
     */
    private void resetListUI() {
        recyclerViewProvince.setVisibility(View.GONE);
        recyclerViewCity.setVisibility(View.GONE);
        recyclerViewCounty.setVisibility(View.GONE);
        recyclerViewTown.setVisibility(View.GONE);
    }


    private void setItemClickable(boolean clickable) {
        adapterProvince.setClickable(clickable);
        adapterCity.setClickable(clickable);
        adapterCounty.setClickable(clickable);
        adapterTown.setClickable(clickable);
    }
}
