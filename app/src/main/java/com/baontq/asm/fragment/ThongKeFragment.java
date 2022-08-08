package com.baontq.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.baontq.asm.R;
import com.baontq.asm.adapter.ThongKeViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ThongKeFragment extends Fragment {
    private TabLayout tabLayoutThongke;
    private ViewPager2 vpThongke;
    private ThongKeViewPagerAdapter viewPagerAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPagerAdapter = new ThongKeViewPagerAdapter(this);
        tabLayoutThongke = view.findViewById(R.id.tab_layout_thongke);
        vpThongke = view.findViewById(R.id.vp_thongke);
        vpThongke.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayoutThongke, vpThongke, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Thu");
                    break;
                case 1:
                    tab.setText("Chi");
                    break;
            }
        }).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }
}