package ng.com.carekojo.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ng.com.carekojo.R;
import ng.com.carekojo.fragments.DashboardFragment;
import ng.com.carekojo.fragments.HomeFragment;
import ng.com.carekojo.fragments.LocateFragment;

public class GuestPage extends AppCompatActivity {

    private TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    LinearLayout drawerItemsLayout;
    private View overlayView;
    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //view pager and tab layout
        viewPager = findViewById(R.id.viewpager);
        addTabs(viewPager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#B72020")));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTintList(ColorStateList.valueOf(Color.parseColor("#707070")));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupTabIcons();

        overlayView = findViewById(R.id.overlayView);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerItemsLayout = findViewById(R.id.drawer_items_layout);
        createDrawerItem();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the drawer when the overlay is clicked
                drawerLayout.closeDrawers();
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                overlayView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                overlayView.setVisibility(View.GONE);
            }
        });

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void createDrawerItem() {
        // Inflate the drawer item layout
        View drawerItem = getLayoutInflater().inflate(R.layout.drawer_item, drawerItemsLayout, false);

        TextView close = drawerItem.findViewById(R.id.close);
        LinearLayout login = drawerItem.findViewById(R.id.linlogin);
        LinearLayout signup = drawerItem.findViewById(R.id.linsignup);
        LinearLayout aboutus = drawerItem.findViewById(R.id.linaboutus);
        LinearLayout blog = drawerItem.findViewById(R.id.linblog);
        LinearLayout support = drawerItem.findViewById(R.id.linsupport);
        LinearLayout faq = drawerItem.findViewById(R.id.linfaq);
        LinearLayout terms = drawerItem.findViewById(R.id.linterms);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuestPage.this, LoginPage.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuestPage.this, Registration.class));
            }
        });

        // Add the item to the drawer layout
        drawerItemsLayout.addView(drawerItem);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_locate);
    }

    private void addTabs(ViewPager viewPager) {
        GuestPage.ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new DashboardFragment(), "Dashboard");
        adapter.addFrag(new LocateFragment(), "Locate");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}