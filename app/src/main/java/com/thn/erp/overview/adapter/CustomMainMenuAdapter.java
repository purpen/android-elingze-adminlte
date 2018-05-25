package com.thn.erp.overview.adapter;
import android.widget.CheckBox;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.erp.R;
import com.thn.erp.overview.bean.CustomMenuBean;
import java.util.List;

public class CustomMainMenuAdapter extends BaseQuickAdapter<CustomMenuBean,BaseViewHolder> {
    public CustomMainMenuAdapter(int layoutResId,List<CustomMenuBean> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomMenuBean item) {
        helper.setText(R.id.name, item.title);
        ((CheckBox)helper.getView(R.id.checkBox)).setChecked(item.selected);
    }
}