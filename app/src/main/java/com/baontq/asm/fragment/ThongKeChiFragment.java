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

import com.baontq.asm.DAO.KhoanChiDAO;
import com.baontq.asm.DTO.KhoanChi;
import com.baontq.asm.R;
import com.baontq.asm.adapter.ThongKeChiExpandableAdapter;
import com.baontq.asm.ultil.GroupObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

public class ThongKeChiFragment extends Fragment {
    private List<GroupObject> listGroup;
    private ExpandableListView elvChi;
    private LinkedHashMap<GroupObject, List<KhoanChi>> listItem;
    private ThongKeChiExpandableAdapter adapter;
    private TextView tvTotal;
    private KhoanChiDAO dao;
    private final List<KhoanChi> listKhoanChiT1 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT2 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT3 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT4 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT5 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT6 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT7 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT8 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT9 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT10 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT11 = new ArrayList<>();
    private final List<KhoanChi> listKhoanChiT12 = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        elvChi = view.findViewById(R.id.elv_chi);
        tvTotal = tvTotal = view.findViewById(R.id.tv_total_value);
        dao = new KhoanChiDAO(getContext());
        listItem = getListItem();
        listGroup = new ArrayList<>(listItem.keySet());
        adapter = new ThongKeChiExpandableAdapter(listGroup, listItem);
        elvChi.setAdapter(adapter);
        for (int i = 0; i < 10; i++) elvChi.expandGroup(i);
    }

    private LinkedHashMap<GroupObject, List<KhoanChi>> getListItem() {
        double total = 0;
        LinkedHashMap<GroupObject, List<KhoanChi>> listMap = new LinkedHashMap<>();
        List<KhoanChi> list = dao.getAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (KhoanChi kc : list) {
            total += kc.getCost();
            try {
                cal.setTime(dateFormat.parse(kc.getTime()));
                switch (cal.get(Calendar.MONTH) + 1) {
                    case 1:
                        listKhoanChiT1.add(kc);
                        break;
                    case 2:
                        listKhoanChiT2.add(kc);
                        break;
                    case 3:
                        listKhoanChiT3.add(kc);
                        break;
                    case 4:
                        listKhoanChiT4.add(kc);
                        break;
                    case 5:
                        listKhoanChiT5.add(kc);
                        break;
                    case 6:
                        listKhoanChiT6.add(kc);
                        break;
                    case 7:
                        listKhoanChiT7.add(kc);
                        break;
                    case 8:
                        listKhoanChiT8.add(kc);
                        break;
                    case 9:
                        listKhoanChiT9.add(kc);
                        break;
                    case 10:
                        listKhoanChiT10.add(kc);
                        break;
                    case 11:
                        listKhoanChiT11.add(kc);
                        break;
                    case 12:
                        listKhoanChiT12.add(kc);
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        tvTotal.setText(String.format("%.0f VND", total));
        listMap.put(new GroupObject(1, "Tháng 1"), listKhoanChiT1);
        listMap.put(new GroupObject(2, "Tháng 2"), listKhoanChiT2);
        listMap.put(new GroupObject(3, "Tháng 3"), listKhoanChiT3);
        listMap.put(new GroupObject(4, "Tháng 4"), listKhoanChiT4);
        listMap.put(new GroupObject(5, "Tháng 5"), listKhoanChiT5);
        listMap.put(new GroupObject(6, "Tháng 6"), listKhoanChiT6);
        listMap.put(new GroupObject(7, "Tháng 7"), listKhoanChiT7);
        listMap.put(new GroupObject(8, "Tháng 8"), listKhoanChiT8);
        listMap.put(new GroupObject(9, "Tháng 9"), listKhoanChiT9);
        listMap.put(new GroupObject(10, "Tháng 10"), listKhoanChiT10);
        listMap.put(new GroupObject(11, "Tháng 11"), listKhoanChiT11);
        listMap.put(new GroupObject(12, "Tháng 12"), listKhoanChiT12);
        return listMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_chi, container, false);
    }
}