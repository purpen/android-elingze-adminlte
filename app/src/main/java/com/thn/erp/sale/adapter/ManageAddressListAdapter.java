package com.thn.erp.sale.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.stephen.taihuoniaolibrary.utils.THNWaittingDialog;
import com.thn.erp.R;
import com.thn.erp.net.ClientParamsAPI;
import com.thn.erp.net.HttpRequest;
import com.thn.erp.net.HttpRequestCallback;
import com.thn.erp.net.URL;
import com.thn.erp.sale.AddNewAddressActivity;
import com.thn.erp.sale.bean.AddressData;
import com.thn.erp.sale.bean.DeleteAddressData;
import com.thn.erp.utils.JsonUtil;
import com.thn.erp.utils.ToastUtils;
import com.thn.erp.view.dialog.DefaultDialog;
import com.thn.erp.view.dialog.IDialogListenerConfirmBack;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


public class ManageAddressListAdapter extends UltimateViewAdapter {
    private THNWaittingDialog dialog;
    private Activity activity;
    private List<AddressData.DataBean> list;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ManageAddressListAdapter(Activity activity,List<AddressData.DataBean> list) {
        dialog = new THNWaittingDialog(activity);
        this.activity = activity;
        this.list = list;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= list.size() : position < list.size()) && (customHeaderView != null ? position > 0 : true)) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final int pos = customHeaderView != null ? position - 1 : position;
            final AddressData.DataBean item = list.get(position);
            viewHolder.itemAddressNametv.setText(item.full_name);

            if (item.is_default) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(item.province);
            sb.append(item.city);
            sb.append(item.town);
            sb.append(item.area);
            sb.append(item.street_address);

            viewHolder.tvAddress.setText(sb.toString());
            viewHolder.itemAddressPhonetv.setText(item.mobile);

            viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AddNewAddressActivity.class);
                    intent.putExtra(AddNewAddressActivity.class.getSimpleName(), item);
                    activity.startActivity(intent);
                }
            });

            viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Resources resources = activity.getResources();

                    new DefaultDialog(activity, resources.getString(R.string.hint_dialog_delete_address_title), resources.getStringArray(R.array.text_dialog_button), new IDialogListenerConfirmBack() {

                        @Override
                        public void clickRight() {
                            HashMap<String, String> params = ClientParamsAPI.deleteAddressParams();
                            Call httpHandler = HttpRequest.sendRequest(HttpRequest.DELETE, URL.DELETE_ADDRESS+item.rid, params,new HttpRequestCallback() {
                                @Override
                                public void onStart() {
                                    if (dialog != null && !activity.isFinishing()) dialog.show();
                                }

                                @Override
                                public void onSuccess(String json) {
                                    DeleteAddressData deleteAddressData = JsonUtil.fromJson(json, DeleteAddressData.class);
                                    if (deleteAddressData.success) {
                                        ToastUtils.showSuccess("删除成功");
                                        activity.finish();
                                    } else {
                                        ToastUtils.showError(deleteAddressData.status.message);
                                    }
                                }

                                @Override
                                public void onFailure(IOException e) {
                                    dialog.dismiss();
                                    ToastUtils.showError(R.string.network_err);
                                }
                            });
                        }
                    });
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(view, pos);
                    }
                }
            });


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
                .inflate(R.layout.layout_manage_address_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(AddressData.DataBean dataBean, int position) {
        insertInternal(list, dataBean, position);
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


    public AddressData.DataBean getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < list.size())
            return list.get(position);
        else return null;
    }


    static class ViewHolder extends UltimateRecyclerviewViewHolder {
        @BindView(R.id.item_address_nametv)
        TextView itemAddressNametv;
        @BindView(R.id.item_address_phonetv)
        TextView itemAddressPhonetv;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.tvDelete)
        TextView tvDelete;
        @BindView(R.id.tvEdit)
        TextView tvEdit;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
