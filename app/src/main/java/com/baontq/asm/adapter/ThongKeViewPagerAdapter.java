package com.baontq.asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.baontq.asm.fragment.ThongKeChiFragment;
import com.baontq.asm.fragment.ThongKeThuFragment;

public class ThongKeViewPagerAdapter extends FragmentStateAdapter {
    public ThongKeViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new ThongKeChiFragment();
            default: return new ThongKeThuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
