package com.thn.erp.base;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.thn.erp.R;
import com.thn.erp.common.OnRecyclerViewItemClickListener;

import java.security.SecureRandom;
import java.util.List;


public abstract class BaseUltimateViewAdapter<T> extends UltimateViewAdapter {
    private OnRecyclerViewItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    protected List<T> list;

    public BaseUltimateViewAdapter(List<T> list) {
        this.list = list;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= list.size() : position < list.size()) && (customHeaderView == null || position > 0)) {
           final int pos = customHeaderView != null ? position - 1 : position;
//            Object goods = list.get(pos);
            UltimateRecyclerviewViewHolder viewHolder = ((UltimateRecyclerviewViewHolder) holder);
            onBindItemHolder2(viewHolder, pos);
            if (mDragStartListener != null) {
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
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder<>(view);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
//        View v = LayoutInflater.from(parent.getContext()) .inflate(R.layout.layout_goods_adapter, parent, false);
        return onCreateViewHolder2(parent);
    }

    public void insert(T goods, int position) {
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
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) { };
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

    public T getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        } else {
            return null;
        }
    }

    /************** abstract method ***************/
    public abstract void onBindItemHolder2(UltimateRecyclerviewViewHolder ultimateRecyclerviewViewHolder, int position);
    public abstract UltimateRecyclerviewViewHolder onCreateViewHolder2(ViewGroup parent);


    public void setList(List<T> lists) {
        clear();
        for (T t : lists) {
            insert(t, 0);
        }
    }

    public void addList(List<T> lists) {
        for (T t : lists) {
            insert(t, getAdapterItemCount());
        }
    }
}