package com.thn.erp.more.repository;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseActivity;
import com.thn.erp.common.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stephen on 2018/3/13 11:01
 * Email: 895745843@qq.com
 */

public class LookRepositoryActivity extends BaseActivity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.linearLayout_repository_delete_repository)
    LinearLayout linearLayoutRepositoryDeleteRepository;

    private int status = RepositoryStatus.LOOK;

    @Override
    protected int getLayout() {
        return R.layout.activity_repository_look;
    }

    @Override
    protected void initView() {
        initTopBar();
    }

    private void initTopBar() {
        publicTopBar.setBackgroundColor(getResources().getColor(R.color.THN_color_bgColor_white));
        publicTopBar.setTopBarCenterTextView("查看仓库", getResources().getColor(R.color.THN_color_fontColor_primary));
        publicTopBar.setTopBarLeftImageView(R.mipmap.ic_launcher);
        publicTopBar.setTopBarRightTextView("编辑", Color.parseColor("#27AE59"));
        publicTopBar.setTopBarOnClickListener(this);
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
                    case RepositoryStatus.LOOK:
                        editText1.setEnabled(true);
                        editText1.setSelection(editText1.getText().length());
                        editText2.setEnabled(true);
                        editText3.setEnabled(true);
                        linearLayoutRepositoryDeleteRepository.setVisibility(View.VISIBLE);
                        publicTopBar.setTopBarRightTextView("确认", Color.parseColor("#27AE59"));
                        status = RepositoryStatus.EDIT;
                        break;
                    case RepositoryStatus.EDIT:
                        Toast.makeText(activity, "确认", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.linearLayout_repository_delete_repository)
    public void onViewClicked() {
        Toast.makeText(activity, "删除仓库", Toast.LENGTH_SHORT).show();
    }

    static class RepositoryStatus {
        public static final int LOOK = 0;
        public static final int EDIT = 1;
    }
}
