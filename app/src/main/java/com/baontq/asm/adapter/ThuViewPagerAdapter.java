package com.baontq.asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.baontq.asm.fragment.KhoanThuFragment;
import com.baontq.asm.fragment.LoaiThuFragment;

public class ThuViewPagerAdapter extends FragmentStateAdapter {
    public ThuViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new KhoanThuFragment();
            case 1:
                return new LoaiThuFragment();
            default:
                return new KhoanThuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
