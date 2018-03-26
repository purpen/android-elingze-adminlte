package com.thn.erp.sale.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.SwipeableUltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.swipe.SwipeLayout;
import com.stephen.taihuoniaolibrary.utils.THNToastUtil;
import com.thn.erp.R;
import com.thn.erp.sale.SelectAddressActivity;
import com.thn.erp.sale.bean.AddressData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressListAdapter extends SwipeableUltimateViewAdapter<AddressData.DataBean> {

    @Override
    protected void withBindHolder(UltimateRecyclerviewViewHolder holder, AddressData.DataBean data, int position) {
        super.withBindHolder(holder, data, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        final AddressData.DataBean item = list.get(position);
        ((ViewHolder) holder).itemAddressNametv.setText(item.full_name);

        if (item.is_default) {
            viewHolder.itemAddressIsdefaulttv.setVisibility(View.VISIBLE);
        } else {
            viewHolder.itemAddressIsdefaulttv.setVisibility(View.GONE);
        }

        String builder = item.province +
                " " +
                item.city +
                " " +
                item.town+
                " " +
                item.area;
        viewHolder.itemAddressAddresstv.setText(builder);
        viewHolder.itemAddressDetailsAddresstv.setText(item.street_address);
        viewHolder.itemAddressPhonetv.setText(item.phone);

        if (item.isSelected || TextUtils.equals(item.rid, addressId)) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                THNToastUtil.showInfo("编辑");
//                Intent intent = new Intent(activity, AddAddressActivity.class);
//                intent.putExtra("addressBean", item);
//                activity.startActivityForResult(intent, DataConstants.REQUESTCODE_EDITADDRESS);
            }
        });
    }


    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    @Override
    protected int getNormalLayoutResId() {
        return R.layout.layout_address_item;
    }

    /**
     * this is the Normal View Holder initiation
     *
     * @param view view
     * @return holder
     */
    @Override
    protected UltimateRecyclerviewViewHolder newViewHolder(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URLogs.d("click");
            }
        });
        final ViewHolder viewHolder = new ViewHolder(view, true);
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(view.getContext(), "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

//        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeAt(viewHolder.getPosition());
//                Toast.makeText(view.getContext(), "Deleted " + viewHolder.getPosition(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return viewHolder;
    }

    @Override
    public ViewHolder newFooterHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public ViewHolder newHeaderHolder(View view) {
        return new ViewHolder(view, false);
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    protected void removeNotifyExternal(int pos) {
        closeItem(pos);
    }


//    public static class SVHolder extends UltimateRecyclerviewViewHolder {
//        public static final int layout = R.layout.layout_address_item;
//        public TextView textView;
//        public Button deleteButton;
//        public SwipeLayout swipeLayout;
//
//        public SVHolder(View itemView, boolean bind) {
//            super(itemView);
//            if (bind) {
//                textView = (TextView) itemView.findViewById(R.id.position);
//                deleteButton = (Button) itemView.findViewById(R.id.delete);
//                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.recyclerview_swipe);
//                swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
//                swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//            }
//        }
//    }


    private SelectAddressActivity activity;
    private List<AddressData.DataBean> list;
    private String addressId;

    public AddressListAdapter(SelectAddressActivity activity, List<AddressData.DataBean> list, String addressId) {
        super(list);
        this.activity = activity;
        this.list = list;
        this.addressId = addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    static class ViewHolder extends UltimateRecyclerviewViewHolder{
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.tvEdit)
        TextView tvEdit;
        @BindView(R.id.item_address_nametv)
        TextView itemAddressNametv;
        @BindView(R.id.item_address_isdefaulttv)
        TextView itemAddressIsdefaulttv;
        @BindView(R.id.item_address_addresstv)
        TextView itemAddressAddresstv;
        @BindView(R.id.item_address_details_addresstv)
        TextView itemAddressDetailsAddresstv;
        @BindView(R.id.item_address_phonetv)
        TextView itemAddressPhonetv;
        @BindView(R.id.item_address_linear)
        LinearLayout itemAddressLinear;
        @BindView(R.id.item_address_relative)
        RelativeLayout itemAddressRelative;

        ViewHolder(View view,boolean b) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
