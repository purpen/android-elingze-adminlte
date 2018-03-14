/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * Github:https://github.com/hejunlin2013/TVSample
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thn.erp.goods;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thn.erp.R;


/**
 * Created by hejunlin on 2015/10/16.
 * blog: http://blog.csdn.net/hejjunlin
 */
public class TitleRecyclerViewAdapter extends RecyclerView.Adapter<TitleRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = TitleRecyclerViewAdapter.class.getSimpleName();

    // 数据集
    private String[] mDataList;
    private Context mContext;
    private OnBindListener onBindListener;


    private View selectedViewIndicate;
    private View focusedView;
    private int lastSelectedPosition = -1;

    public interface OnBindListener {
        void onBind(View view, int i);
    }

    public TitleRecyclerViewAdapter(Context context) {
        super();
        mContext = context;
    }

    public TitleRecyclerViewAdapter(Context context, String[] dataList) {
        super();
        mContext = context;
        this.mDataList = dataList;
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int resId = R.layout.item_recyclerview_goods_title;
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if (mDataList == null || mDataList.length == 0) {
            Log.d(TAG, "mDataset has no data!");
            return;
        }
        viewHolder.mTextView.setText(mDataList[i]);
        viewHolder.itemView.setTag(i);
        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if ( v == focusedView) {
                    return;
                }
//                setFocusedViewStatus(v, hasFocus);
                if (hasFocus) {
                    if (onBindListener != null) {
                        onBindListener.onBind(null, viewHolder.getAdapterPosition());
                    }
                    setIndicatorSelectedPosition(v);
                    focusedView = v;
                    v.requestFocus();
                    lastSelectedPosition = viewHolder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            mView =  itemView.findViewById(R.id.imageView);
        }
    }

    /**
     * 修改下面指示标状态
     * @param v
     */
    public void setIndicatorSelectedPosition(View v) {
//        if (v == null) {
//            return;
//        }
//        View view = v.findViewById(R.id.view_menu_indicate);
//        if (view != null) {
//            if (view == selectedViewIndicate) {
//                // do nothing
//            } else if (selectedViewIndicate == null) {
//                view.setBackgroundColor(Color.parseColor("#FFBE28"));
//            } else {
//                selectedViewIndicate.findViewById(R.id.view_menu_indicate).setBackgroundColor(Color.parseColor("#00FFFFFF"));
//                view.setBackgroundColor(Color.parseColor("#FFBE28"));
//            }
//            selectedViewIndicate = v;
//        }
    }

    public int getLastSelectedPosition(){
        return lastSelectedPosition;
    }

    public void setFocusedViewStatus(View v, boolean hasfocus) {
//        if (v == null) {
//            return;
//        }
//        View view = v.findViewById(R.id.tv_menu_title);
//        if (view != null) {
//            if (hasfocus) {
//                view.setBackgroundResource(R.mipmap.bg_button);
//            } else {
//                view.setBackgroundColor(Color.parseColor("#00000000"));
//            }
//        }
    }
}
