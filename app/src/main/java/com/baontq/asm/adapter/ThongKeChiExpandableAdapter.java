package com.baontq.asm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.baontq.asm.DTO.KhoanChi;
import com.baontq.asm.R;
import com.baontq.asm.ultil.GroupObject;

import java.util.List;
import java.util.Map;

public class ThongKeChiExpandableAdapter extends BaseExpandableListAdapter {
    private final List<GroupObject> groupObjectList;
    private final Map<GroupObject, List<KhoanChi>> khoanChiListItem;

    public ThongKeChiExpandableAdapter(List<GroupObject> groupObjectList, Map<GroupObject, List<KhoanChi>> khoanChiListItem) {
        this.groupObjectList = groupObjectList;
        this.khoanChiListItem = khoanChiListItem;
    }

    @Override
    public int getGroupCount() {
        return groupObjectList != null ? groupObjectList.size() : 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if (groupObjectList != null && khoanChiListItem != null) {
            return khoanChiListItem.get(groupObjectList.get(i)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return groupObjectList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return khoanChiListItem.get(groupObjectList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return groupObjectList.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return khoanChiListItem.get(groupObjectList.get(i)).get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_thongke_group, viewGroup, false);
        }
        TextView tvNameGroup = view.findViewById(R.id.tv_group_name);
        tvNameGroup.setText(groupObjectList.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_khoanchi_item, viewGroup, false);
        }
        TextView tvKhoanchiItemName, tvKhoanchiItemDate, tvKhoanchiItemCost;

        tvKhoanchiItemName = view.findViewById(R.id.tv_khoanchi_item_name);
        tvKhoanchiItemDate = view.findViewById(R.id.tv_khoanchi_item_date);
        tvKhoanchiItemCost = view.findViewById(R.id.tv_khoanchi_item_cost);
        KhoanChi khoanChi = khoanChiListItem.get(groupObjectList.get(i)).get(i1);
        tvKhoanchiItemName.setText(khoanChi.getName());
        tvKhoanchiItemDate.setText(khoanChi.getTime());
        tvKhoanchiItemCost.setText("+ " + String.format("%.0f", khoanChi.getCost()));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
