package com.thn.erp.goods.add;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stephen.taihuoniaolibrary.utils.THNGlideUtil;
import com.thn.erp.R;
import com.thn.erp.common.interfaces.OnRecyclerViewItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Stephen on 2018/3/27 17:18
 * Email: 895745843@qq.com
 */

public class GoodsAddRecyclerViewAdapter extends RecyclerView.Adapter<GoodsAddRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<String> stringList;
    private int pictureSize = 3;//最多三张图片
    private int pixelSize;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public GoodsAddRecyclerViewAdapter(Context context, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mContext = context;
        pixelSize = context.getResources().getDimensionPixelSize(R.dimen.dp69);
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
        if (stringList == null) {
            stringList = new ArrayList<>();
        }
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_goods_add_image, parent, false);
        return new ViewHolder(imteView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        if (stringList.size() == 0 || position == stringList.size()) {
//            THNGlideUtil.displayImage(R.mipmap.icon_goods_add_img, holder.imageView);
//        } else {
        String uri = stringList.get(position);
        THNGlideUtil.displayImageFadeinWithDimen(uri, holder.imageView, pixelSize, pixelSize);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    class ImageBean {
        String imageResource;
        String imamgeIndex;
        String imageId;
    }

    public static class MessageHideAddButton {
        public boolean hidden= true;
    }

    public void addList(String uri) {
        stringList.add(uri);
        notifyDataSetChanged();
        if (stringList.size() == pictureSize) {
            EventBus.getDefault().post(new MessageHideAddButton());
        }

    }
}
