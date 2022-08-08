package com.baontq.asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.baontq.asm.fragment.KhoanChiFragment;
import com.baontq.asm.fragment.LoaiChiFragment;

public class ChiViewPagerAdapter extends FragmentStateAdapter {
    public ChiViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new KhoanChiFragment();
            case 1:
                return new LoaiChiFragment();
            default:
                return new KhoanChiFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
