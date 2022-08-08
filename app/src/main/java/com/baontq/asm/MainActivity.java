package com.baontq.asm;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.baontq.asm.fragment.AboutFragment;
import com.baontq.asm.fragment.ChiFragment;
import com.baontq.asm.fragment.ThongKeFragment;
import com.baontq.asm.fragment.ThuFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mainDrawerLayout;
    private MaterialToolbar mainToolbar;
    //private FrameLayout mainFrameLayout;
    private NavigationView mainNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        mainToolbar = findViewById(R.id.main_toolbar);
        //mainFrameLayout = findViewById(R.id.main_frame_layout);
        mainNavigationView = findViewById(R.id.main_navigation_view);

        //Toolbar
        mainToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(mainToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawerLayout, mainToolbar, R.string.navigation_open, R.string.navigation_close);
        toggle.syncState();
        //Navigation
        mainNavigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ThongKeFragment()).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int selectItem = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (selectItem) {
            case R.id.menu_item_khoanchi:
                mainToolbar.setTitle("Khoản chi");
                transaction.replace(R.id.main_frame_layout, new ChiFragment());
                transaction.commit();
                break;
            case R.id.menu_item_khoanthu:
                mainToolbar.setTitle("Khoản thu");
                transaction.replace(R.id.main_frame_layout, new ThuFragment());
                transaction.commit();
                break;
            case R.id.menu_item_thongke:
                mainToolbar.setTitle(R.string.navigation_statistic);
                transaction.replace(R.id.main_frame_layout, new ThongKeFragment());
                transaction.commit();
                break;
            case R.id.menu_item_about:
                item.setCheckable(true);
                mainToolbar.setTitle(R.string.navigation_info);
                transaction.replace(R.id.main_frame_layout, new AboutFragment());
                transaction.commit();
                break;
            case R.id.menu_item_exit:
                System.exit(0);
                break;

        }
        mainDrawerLayout.closeDrawer(mainNavigationView);
        return true;
    }
}