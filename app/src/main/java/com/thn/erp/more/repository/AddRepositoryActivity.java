package com.thn.erp.more.repository;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thn.erp.R;
import com.thn.erp.base.BaseStyle2Activity;
import com.thn.erp.common.interfaces.ImpTopbarOnClickListener;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.ielse.view.SwitchView;

/**
 * Created by Stephen on 2018/3/13 11:47
 * Email: 895745843@qq.com
 */

public class AddRepositoryActivity extends BaseStyle2Activity implements ImpTopbarOnClickListener {

    @BindView(R.id.publicTopBar)
    PublicTopBar publicTopBar;
    @BindView(R.id.editText1)
    EditText editText1;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.switchView_open)
    SwitchView switchViewOpen;
    @BindView(R.id.editText3)
    EditText editText3;

    @Override
    protected int getLayout() {
        return R.layout.activity_repository_add;
    }


    @Override
    protected void initView() {
        initTopBar();
        switchViewOpen.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);
            }
        });
    }

    private void initTopBar() {
        publicTopBar.setTopBarCenterTextView("新增仓库", getResources().getColor(R.color.white));
        publicTopBar.setTopBarLeftImageView(true);
        publicTopBar.setTopBarRightTextView("保存", getResources().getColor(R.color.white));
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
                Toast.makeText(activity, "Right", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
