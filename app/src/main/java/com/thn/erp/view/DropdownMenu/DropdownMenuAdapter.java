package com.thn.erp.view.DropdownMenu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thn.erp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DropdownMenuAdapter extends RecyclerView.Adapter<DropdownMenuAdapter.ViewHolder> {
    private List<String> list;

    public DropdownMenuAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_menu, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv.setText(list.get(position));
        int width = holder.tv.getContext().getResources().getDimensionPixelSize(R.dimen.dp200);
        int height = holder.tv.getContext().getResources().getDimensionPixelSize(R.dimen.dp44);
        holder.tv.setLayoutParams(new ViewGroup.LayoutParams(width,height));
        holder.tv.setGravity(Gravity.CENTER);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }
}
