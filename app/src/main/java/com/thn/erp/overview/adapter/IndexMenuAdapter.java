package com.thn.erp.overview.adapter;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.thn.erp.R;
import com.thn.erp.overview.bean.CustomMenuBean;

import java.util.List;

public class IndexMenuAdapter extends BaseMultiItemQuickAdapter<IndexMenuAdapter.MultipleItem, BaseViewHolder> {

    public static class MultipleItem implements MultiItemEntity {
        public static final int IMAGE = 0x0010;
        public static final int IMAGE_TEXT = 0x0011;
        private int itemType;
        public CustomMenuBean item;
        public int iconId;
        public MultipleItem(int itemType,CustomMenuBean bean) {
            this.itemType = itemType;
            this.item = bean;
            this.iconId = bean.iconId;
        }

        public MultipleItem(int itemType,@DrawableRes int iconId) {
            this.itemType = itemType;
            this.iconId = iconId;
        }


        @Override
        public int getItemType() {
            return itemType;
        }
    }


    public IndexMenuAdapter(List data) {
        super(data);
        addItemType(MultipleItem.IMAGE, R.layout.item_image);
        addItemType(MultipleItem.IMAGE_TEXT, R.layout.item_recyclerview_product2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.IMAGE:
                helper.setImageResource(R.id.imageView, R.mipmap.icon_menu_more);
                break;
            case MultipleItem.IMAGE_TEXT:
                helper.setText(R.id.name,item.item.title);
                ((ImageView)helper.getView(R.id.product_img)).setImageResource(item.item.iconId);
                break;
            default:
                break;

        }
    }

}
