package com.example.yoant.travelassistant.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.adapters.view_pager.ViewPagerAdapter;
import com.example.yoant.travelassistant.database.AppDatabase;
import com.example.yoant.travelassistant.database.DatabaseInitializer;
import com.example.yoant.travelassistant.helper.constants.ViewConstant;
import com.example.yoant.travelassistant.models.CountriesReceiver;
import com.example.yoant.travelassistant.models.Country;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private DrawerLayout mDrawer;
    private TabLayout mTabLayout;
    private ArrayList<Country> mListCountries = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        DatabaseInitializer.deleteDataFromDatabase(AppDatabase.getInMemoryDatabase(getApplicationContext()), new CountriesReceiver("test_json"));

        mListCountries = getIntent().getParcelableArrayListExtra(ViewConstant.RESTORE_COUNTRIES_LIST);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_world) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.nav_africa) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_america) {
            mViewPager.setCurrentItem(2);
        } else if (id == R.id.nav_asia) {
            mViewPager.setCurrentItem(3);
        } else if (id == R.id.nav_eurpope) {
            mViewPager.setCurrentItem(4);
        } else if (id == R.id.nav_oceania) {
            mViewPager.setCurrentItem(5);
        } else if (id == R.id.nav_exit) {
            finish();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(ViewConstant.RESTORE_COUNTRIES_LIST, mListCountries);
        super.onSaveInstanceState(savedInstanceState);
    }

    public ArrayList<Country> sendData() {
        return mListCountries;
    }
}