package com.thn.erp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.thn.erp.R;
import com.thn.erp.goods.GoodsDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自己添加了stock
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> group;
    private HashMap<String, ArrayList<GoodsDetailsActivity.HomeType>> childMap;

    public MyExpandableListAdapter(ArrayList<String> group, HashMap<String, ArrayList<GoodsDetailsActivity.HomeType>> childMap) {
        this.group = group;
        this.childMap = childMap;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = group.get(groupPosition);
        return (childMap.get(key).get(childPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String key = group.get(groupPosition);
        GoodsDetailsActivity.HomeType subItem = childMap.get(key).get(childPosition);

        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_layout, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.tv_child.setText(subItem.name);
        holder.tvCount.setText(subItem.value);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = group.get(groupPosition);
        int size = childMap.get(key).size();
        return size;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String item = group.get(groupPosition);
        GroupViewHolder holder = null;
        if (convertView == null) {
            AbsListView.LayoutParams abs = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,parent.getContext().getResources().getDimensionPixelSize(R.dimen.dp45));
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_layout, null);
            convertView.setLayoutParams(abs);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.tv.setText(item);
        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.tv)
        TextView tv_child;
        @BindView(R.id.tvCount)
        TextView tvCount;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
