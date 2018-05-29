package com.thn.erp.more;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseFragment;
import com.thn.erp.more.chat.MessageListActivity;
import com.thn.erp.more.repository.RepositoryManageActivity;
import com.thn.erp.more.supplier.SupplierManageActivity;
import com.thn.erp.view.common.LinearLayoutArrowTextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lilin on 2018/3/7.
 */

public class MoreFragment extends BaseFragment {
    @BindView(R.id.imageView_user_photo)
    ImageView imageViewUserPhoto;
    @BindView(R.id.textView_user_name)
    TextView textViewUserName;
    @BindView(R.id.textView_user_company)
    TextView textViewUserCompany;
    @BindView(R.id.linearLayout_service1)
    LinearLayout linearLayoutService1;
    @BindView(R.id.linearLayout_service2)
    LinearLayout linearLayoutService2;
    @BindView(R.id.imageView_service_online)
    ImageView imageViewServiceOnline;
    @BindView(R.id.TextView_service_online)
    TextView TextViewServiceOnline;
    @BindView(R.id.linearLayout_service3)
    LinearLayout linearLayoutService3;
    @BindView(R.id.layoutItemView1)
    LinearLayoutArrowTextView linearLayoutArrowTextView1;
    @BindView(R.id.layoutItemView2)
    LinearLayoutArrowTextView linearLayoutArrowTextView2;
    @BindView(R.id.layoutItemView3)
    LinearLayoutArrowTextView linearLayoutArrowTextView3;
    @BindView(R.id.layoutItemView4)
    LinearLayoutArrowTextView linearLayoutArrowTextView4;
    @BindView(R.id.layoutItemView5)
    LinearLayoutArrowTextView linearLayoutArrowTextView5;
    @BindView(R.id.layoutItemView6)
    LinearLayoutArrowTextView linearLayoutArrowTextView6;
    Unbinder unbinder;

    @Override
    protected int getLayout() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initView() {
        linearLayoutArrowTextView1.setName(NAMES[0]);
        linearLayoutArrowTextView2.setName(NAMES[1]);
        linearLayoutArrowTextView3.setName(NAMES[2]);
        linearLayoutArrowTextView4.setName(NAMES[3]);
        linearLayoutArrowTextView5.setName(NAMES[4]);
        linearLayoutArrowTextView6.setName(NAMES[5]);
    }

    @OnClick({R.id.imageView_user_photo, R.id.textView_user_name, R.id.textView_user_company, R.id.linearLayout_service1, R.id.linearLayout_service2, R.id.linearLayout_service3, R.id.layoutItemView1, R.id.layoutItemView2, R.id.layoutItemView3, R.id.layoutItemView4, R.id.layoutItemView5, R.id.layoutItemView6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_user_photo:
                break;
            case R.id.textView_user_name:
                break;
            case R.id.textView_user_company:
                break;
            case R.id.linearLayout_service1:
               startActivity(new Intent(getContext(), MessageListActivity.class));
                break;
            case R.id.linearLayout_service2:
                CustomDialog dialog = new CustomDialog();
                dialog.show(getFragmentManager(), CustomDialog.class.getSimpleName());
                break;
            case R.id.linearLayout_service3:
                Toast.makeText(activity, "提工单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutItemView1:
                Toast.makeText(activity, NAMES[0], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, DemoActivity.class));
                break;
            case R.id.layoutItemView2:
                startActivity(new Intent(activity, ToolsActivity.class));
                break;
            case R.id.layoutItemView3:
                Toast.makeText(activity, NAMES[2], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, SettingActivity.class));
                break;
            case R.id.layoutItemView4:
                Toast.makeText(activity, NAMES[3], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), RepositoryManageActivity.class));
                break;
            case R.id.layoutItemView5:
                Toast.makeText(activity, NAMES[4], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, SupplierManageActivity.class));
                break;
            case R.id.layoutItemView6:
                Toast.makeText(activity, NAMES[5], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity, AccountManageActivity.class));
                break;
        }
    }

    private static String[] NAMES = {"快速上手", "工具箱", "设置", "仓库管理", "供应商管理", "账户管理"};
}
