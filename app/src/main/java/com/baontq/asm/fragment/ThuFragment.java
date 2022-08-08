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
import com.baontq.asm.adapter.ThuViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ThuFragment extends Fragment {
    private TabLayout tabLayoutThu;
    private ViewPager2 vpThu;
    private ThuViewPagerAdapter thuViewPagerAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayoutThu = view.findViewById(R.id.tab_layout_thu);
        vpThu = view.findViewById(R.id.vp_thu);
        thuViewPagerAdapter = new ThuViewPagerAdapter(getActivity());
        vpThu.setAdapter(thuViewPagerAdapter);
                new TabLayoutMediator(tabLayoutThu, vpThu, (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Khoản thu");
                            break;
                        case 1:
                            tab.setText("Loại thu");
                            break;
                    }
                }).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu, container, false);
    }
}