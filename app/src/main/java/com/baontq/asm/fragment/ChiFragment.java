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
import com.baontq.asm.adapter.ChiViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiFragment extends Fragment {
    private TabLayout tabLayoutChi;
    private ViewPager2 vpChi;
    private ChiViewPagerAdapter chiViewPagerAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayoutChi = view.findViewById(R.id.tab_layout_chi);
        vpChi = view.findViewById(R.id.vp_chi);
        chiViewPagerAdapter = new ChiViewPagerAdapter(getActivity());
        vpChi.setAdapter(chiViewPagerAdapter);
        new TabLayoutMediator(tabLayoutChi, vpChi, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Khoản chi");
                    break;
                case 1:
                    tab.setText("Loại chi");
                    break;
            }
        })).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chi, container, false);
    }
}