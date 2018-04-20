package com.thn.erp.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.WindowManager;

import com.stephen.taihuoniaolibrary.utils.THNDpUtil;
import com.stephen.taihuoniaolibrary.utils.THNStatusBarUtils;
import com.thn.erp.R;
import com.thn.erp.common.RecycleViewItemDecorationVertical;
import com.thn.erp.view.common.PublicTopBar;

import butterknife.BindView;

/**
 * Created by Stephen on 2018/3/21 16:52
 * Email: 895745843@qq.com
 */

public abstract class BaseStyle2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.THN_color_bgColor_content)));
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        THNStatusBarUtils.chenjin(this, R.color.THN_color_primaryColor);
    }














    protected void setLinearLayoutManagerVertical(BaseUltimateRecyclerView ultimateRecyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.removeItemDividerDecoration();
        ultimateRecyclerView.addItemDecoration(new RecycleViewItemDecorationVertical(THNDpUtil.dp2px(this, 10)));
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
    }

    protected void setLinearLayoutManagerHorizontal(BaseUltimateRecyclerView ultimateRecyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ultimateRecyclerView.setHasFixedSize(true);
        ultimateRecyclerView.removeItemDividerDecoration();
        ultimateRecyclerView.addItemDecoration(new RecycleViewItemDecorationVertical(THNDpUtil.dp2px(this, 10)));
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
