package com.baontq.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baontq.asm.DAO.KhoanThuDAO;
import com.baontq.asm.DTO.KhoanThu;
import com.baontq.asm.R;
import com.baontq.asm.adapter.ThongKeThuExpandableAdapter;
import com.baontq.asm.ultil.GroupObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;


public class ThongKeThuFragment extends Fragment {
    private ExpandableListView elvThu;
    private List<GroupObject> listGroup;
    private LinkedHashMap<GroupObject, List<KhoanThu>> listItem;
    private ThongKeThuExpandableAdapter adapter;
    private TextView tvTotal;
    private KhoanThuDAO dao;
    private final List<KhoanThu> listKhoanThuT1 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT2 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT3 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT4 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT5 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT6 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT7 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT8 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT9 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT10 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT11 = new ArrayList<>();
    private final List<KhoanThu> listKhoanThuT12 = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        elvThu = view.findViewById(R.id.elv_thu);
        tvTotal = view.findViewById(R.id.tv_total_value);
        dao = new KhoanThuDAO(getContext());
        listItem = getListItem();
        listGroup = new ArrayList<>(listItem.keySet());
        adapter = new ThongKeThuExpandableAdapter(listGroup, listItem);
        elvThu.setAdapter(adapter);
        for (int i = 0; i < 10; i++) elvThu.expandGroup(i);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_thu, container, false);
    }

    private LinkedHashMap<GroupObject, List<KhoanThu>> getListItem() {
        double total = 0;
        LinkedHashMap<GroupObject, List<KhoanThu>> listMap = new LinkedHashMap<>();
        List<KhoanThu> list = dao.getAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (KhoanThu kt : list) {
            total += kt.getCost();
            try {
                cal.setTime(dateFormat.parse(kt.getTime()));
                switch (cal.get(Calendar.MONTH) + 1) {
                    case 1:
                        listKhoanThuT1.add(kt);
                        break;
                    case 2:
                        listKhoanThuT2.add(kt);
                        break;
                    case 3:
                        listKhoanThuT3.add(kt);
                        break;
                    case 4:
                        listKhoanThuT4.add(kt);
                        break;
                    case 5:
                        listKhoanThuT5.add(kt);
                        break;
                    case 6:
                        listKhoanThuT6.add(kt);
                        break;
                    case 7:
                        listKhoanThuT7.add(kt);
                        break;
                    case 8:
                        listKhoanThuT8.add(kt);
                        break;
                    case 9:
                        listKhoanThuT9.add(kt);
                        break;
                    case 10:
                        listKhoanThuT10.add(kt);
                        break;
                    case 11:
                        listKhoanThuT11.add(kt);
                        break;
                    case 12:
                        listKhoanThuT12.add(kt);
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tvTotal.setText(String.format("%.0f VND", total));
        listMap.put(new GroupObject(1, "Tháng 1"), listKhoanThuT1);
        listMap.put(new GroupObject(2, "Tháng 2"), listKhoanThuT2);
        listMap.put(new GroupObject(3, "Tháng 3"), listKhoanThuT3);
        listMap.put(new GroupObject(4, "Tháng 4"), listKhoanThuT4);
        listMap.put(new GroupObject(5, "Tháng 5"), listKhoanThuT5);
        listMap.put(new GroupObject(6, "Tháng 6"), listKhoanThuT6);
        listMap.put(new GroupObject(7, "Tháng 7"), listKhoanThuT7);
        listMap.put(new GroupObject(8, "Tháng 8"), listKhoanThuT8);
        listMap.put(new GroupObject(9, "Tháng 9"), listKhoanThuT9);
        listMap.put(new GroupObject(10, "Tháng 10"), listKhoanThuT10);
        listMap.put(new GroupObject(11, "Tháng 11"), listKhoanThuT11);
        listMap.put(new GroupObject(12, "Tháng 12"), listKhoanThuT12);

        return listMap;
    }

}