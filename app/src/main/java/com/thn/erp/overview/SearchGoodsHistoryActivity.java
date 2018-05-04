package com.thn.erp.overview;

import android.content.Intent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.overview.bean.SearchHistoryData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.CustomSearchHeadView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * 商品搜索界面
 */
public class SearchGoodsHistoryActivity extends BaseActivity {
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.customSearchHeadView)
    CustomSearchHeadView customSearchHeadView;
    private int page = 1;
    private THNWaittingDialog dialog;
    private ArrayList<SearchHistoryData.DataBean.SearchItemsBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void initView() {
        dialog = new THNWaittingDialog(this);
        list = new ArrayList<>();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String stringExtra = intent.getStringExtra(SearchGoodsHistoryActivity.class.getSimpleName());
        if(!TextUtils.isEmpty(stringExtra)){
            customSearchHeadView.setContent(stringExtra);
        }
    }

    @Override
    protected void requestNet() {
        getSearchHistory();
    }

    private void getSearchHistory() {
        HashMap<String, String> params = ClientParamsAPI.searchHistoryParams(page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.SEARCH_HISTORY, params, new HttpRequestCallback() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(String json) {
                dialog.dismiss();
                SearchHistoryData data = JsonUtil.fromJson(json, SearchHistoryData.class);
                if (data.success == true) {
                    updateUI(data);
                } else {
                    ToastUtils.showError(data.status.message);
                }

            }

            @Override
            public void onFailure(IOException e) {
                dialog.dismiss();
                ToastUtils.showError(R.string.network_err);
            }
        });
    }

    private void updateUI(SearchHistoryData data) {
        list.addAll(data.data.search_items);
        flowLayout.setAdapter(new TagAdapter<SearchHistoryData.DataBean.SearchItemsBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHistoryData.DataBean.SearchItemsBean item) {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextColor(getResources().getColor(R.color.color_666));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                textView.setText(item.query_word);
                return textView;
            }
        });
    }

//    @OnClick(R.id.ibBack)
//    void performClick(View v) {
//        switch (v.getId()) {
//            case R.id.ibBack:
//                finish();
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    protected void installListener() {
        customSearchHeadView.setOnLeftButtonClickListener(new CustomSearchHeadView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customSearchHeadView.setOnSearchClickListener(new CustomSearchHeadView.OnSearchClickListener() {
            @Override
            public void onSearch(String s) {
                if (TextUtils.isEmpty(s)){
                    ToastUtils.showInfo("请输入关键字");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), SearchGoodsResultActivity.class);
                intent.putExtra(SearchGoodsResultActivity.class.getSimpleName(),s);
                startActivity(intent);
            }
        });
    }


}
