package com.thn.erp.goods.brand;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;

import java.security.SecureRandom;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoodsBrandListAdapter extends UltimateViewAdapter {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(View view, int i);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private List<GoodsBrandData.DataEntity.BrandsEntity> list;

    public GoodsBrandListAdapter(List<GoodsBrandData.DataEntity.BrandsEntity> list) {
        this.list = list;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= list.size() : position < list.size()) && (customHeaderView != null ? position > 0 : true)) {
           final int pos = customHeaderView != null ? position - 1 : position;
            GoodsBrandData.DataEntity.BrandsEntity goods = list.get(pos);
            ViewHolder viewHolder = ((ViewHolder) holder);
            viewHolder.goodsName.setText(goods.getName());
            THNGlideUtil.displayImage(goods.getLogo(),viewHolder.ivCover,R.mipmap.ic_launcher);
            viewHolder.tvNum.setText("编号："+goods.getRid());
            viewHolder.price.setText("￥"+goods.getFeatures());
            viewHolder.stockNum.setText("库存："+ position);
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
            if (mDragStartListener != null) {
//                ((ViewHolder) holder).imageViewSample.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                            mDragStartListener.onStartDrag(holder);
//                        }
//                        return false;
//                    }
//                });

//                ((ViewHolder) holder).itemView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        return false;
//                    }
//                });
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener!=null){
                            onItemClickListener.onClick(view,pos);
                        }
                    }
                });
            }
        }

    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        // return new itemCommonBinder(view, false);
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_goods_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(GoodsBrandData.DataEntity.BrandsEntity goods, int position) {
        insertInternal(list, goods, position);
    }

    public void remove(int position) {
        removeInternal(list, position);
    }

    public void clear() {
        clearInternal(list);
    }


    public void swapPositions(int from, int to) {
        swapPositions(list, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        // URLogs.d("position--" + position + "   " + getItem(position));
//        if (getItem(position).length() > 0)
//            return getItem(position).charAt(0);
//        else return -1;
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
//        textView.setText(String.valueOf(getItem(position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
        }

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition > 0 && toPosition > 0) {
            swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
            super.onItemMove(fromPosition, toPosition);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (position > 0) {
            remove(position);
            // notifyItemRemoved(position);
//        notifyDataSetChanged();
            super.onItemDismiss(position);
        }

    }


    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.itemview)
        RelativeLayout itemView;
        @BindView(R.id.ivCover)
        ImageView ivCover;
        @BindView(R.id.goodsName)
        TextView goodsName;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.stockNum)
        TextView stockNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public GoodsBrandData.DataEntity.BrandsEntity getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < list.size())
            return list.get(position);
        else return null;
    }

}