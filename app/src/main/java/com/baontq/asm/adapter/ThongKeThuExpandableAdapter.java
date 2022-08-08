package com.baontq.asm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.baontq.asm.DTO.KhoanThu;
import com.baontq.asm.R;
import com.baontq.asm.ultil.GroupObject;

import java.util.List;
import java.util.Map;

public class ThongKeThuExpandableAdapter extends BaseExpandableListAdapter {

    private final List<GroupObject> groupObjectList;
    private final Map<GroupObject, List<KhoanThu>> khoanThuListItem;

    public ThongKeThuExpandableAdapter(List<GroupObject> groupObjectList, Map<GroupObject, List<KhoanThu>> khoanThuListItem) {
        this.groupObjectList = groupObjectList;
        this.khoanThuListItem = khoanThuListItem;
    }

    @Override
    public int getGroupCount() {
        return groupObjectList != null ? groupObjectList.size() : 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if (groupObjectList != null && khoanThuListItem != null) {
            return khoanThuListItem.get(groupObjectList.get(i)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return groupObjectList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return khoanThuListItem.get(groupObjectList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return groupObjectList.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return khoanThuListItem.get(groupObjectList.get(i)).get(i1).getId();
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_khoanthu_item, viewGroup, false);
        }
        TextView tvKhoanThuItemName, tvKhoanThuItemDate, tvKhoanThuItemCost;

        tvKhoanThuItemName = view.findViewById(R.id.tv_khoanthu_item_name);
        tvKhoanThuItemDate = view.findViewById(R.id.tv_khoanthu_item_date);
        tvKhoanThuItemCost = view.findViewById(R.id.tv_khoanthu_item_cost);
        KhoanThu khoanThu = khoanThuListItem.get(groupObjectList.get(i)).get(i1);
        tvKhoanThuItemName.setText(khoanThu.getName());
        tvKhoanThuItemDate.setText(khoanThu.getTime());
        tvKhoanThuItemCost.setText("+ " + String.format("%.0f", khoanThu.getCost()));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
