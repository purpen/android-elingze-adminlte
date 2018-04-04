package com.thn.erp.goods.add;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thn.erp.R;
import com.thn.erp.common.OnRecyclerViewItemClickListener;
import com.thn.erp.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Stephen on 2018/3/27 17:18
 * Email: 895745843@qq.com
 */

public class GoodsAddRecyclerViewAdapter extends RecyclerView.Adapter<GoodsAddRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Bitmap> stringList;
    private int pictureSize = 3;//最多三张图片
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public GoodsAddRecyclerViewAdapter(Context context, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mContext = context;
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
        if (stringList.size() == 0 || position == stringList.size()) {
            GlideUtil.displayImage(R.mipmap.icon_goods_add_img, holder.imageView);
        } else {
            Bitmap uri = stringList.get(position);
            holder.imageView.setImageBitmap(uri);
//            GlideUtil.displayImage(uri, holder.imageView);
        }
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
        if (stringList == null) {
            return 1;
        } else if (stringList.size() == pictureSize) {
            return pictureSize;
        } else {
            return stringList.size() + 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    class ImageBean{
        String imageResource;
        String imamgeIndex;
        String imageId;
    }

    public void addList(Bitmap uri) {
        stringList.add(uri);
        notifyDataSetChanged();
    }
}
