package com.thn.erp.goods.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.thn.basemodule.tools.GlideUtil;
import com.thn.erp.R;
import com.thn.erp.sale.bean.GoodsData;

/**
 * Created by Stephen on 2018/3/26 22:17
 * Email: 895745843@qq.com
 */

public class GoodsListAdapter extends BaseQuickAdapter<GoodsData.DataBean.ProductsBean, BaseViewHolder> {

    public GoodsListAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }


    @Override
    protected void convert(BaseViewHolder helper, GoodsData.DataBean.ProductsBean item) {
        helper.setText(R.id.goodsName, item.name);
        helper.setText(R.id.tvNum, "编号："+item.rid);
        helper.setText(R.id.price,"￥"+item.sale_price);
//        helper.setText(R.id.stockNum,"￥"+item.sale_price);
        View view = helper.getView(R.id.ivCover);
        GlideUtil.loadImageWithDimen(item.cover,(ImageView) view, view.getContext().getResources().getDimensionPixelSize(R.dimen.dp90));
    }

//    @Override
//    public void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position) {
//        GoodsData.DataBean.ProductsBean goods = (GoodsData.DataBean.ProductsBean) list.get(position);
//        GoodsListAdapter.ViewHolder viewHolder = ((GoodsListAdapter.ViewHolder) ultimateRecyclerviewViewHolder);
//        viewHolder.goodsName.setText(goods.name);
//        GlideUtil.loadImage(goods.cover,viewHolder.ivCover);
//        viewHolder.tvNum.setText("编号："+goods.rid);
//        viewHolder.price.setText("￥"+goods.sale_price);
//        viewHolder.stockNum.setText("库存："+position);
//    }

//    @Override
//    public UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent) {
//        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_goods_adapter, parent, false);
//        return new GoodsListAdapter.ViewHolder(v);
//    }

//    class ViewHolder extends UltimateRecyclerviewViewHolder {
//        @BindView(R.id.itemview)
//        RelativeLayout itemView;
//        @BindView(R.id.ivCover)
//        ImageView ivCover;
//        @BindView(R.id.goodsName)
//        TextView goodsName;
//        @BindView(R.id.tvNum)
//        TextView tvNum;
//        @BindView(R.id.price)
//        TextView price;
//        @BindView(R.id.stockNum)
//        TextView stockNum;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//
//        @Override
//        public void onItemSelected() {
//            itemView.setBackgroundColor(Color.LTGRAY);
//        }
//
//        @Override
//        public void onItemClear() {
//            itemView.setBackgroundColor(0);
//        }
//    }
}
