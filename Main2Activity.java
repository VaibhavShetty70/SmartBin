package com.example.priti.smartbin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private TextView mHeaderTitle;
    FragmentManager mFragmentManager ;
    Fragment fragment;
    private final String TAG="Main2Activity";

    public static Intent newIntent(Context context) {
        return new Intent(context, Main2Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mFragmentManager= getSupportFragmentManager();
        fragment = mFragmentManager.findFragmentById(R.id.fcontainer);
        if(fragment==null)
        {
            fragment = new HomeFragment();
            mFragmentManager.beginTransaction().add(R.id.fcontainer,fragment).commit();
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View mHeaderView = navigationView.getHeaderView(0);
        mHeaderTitle = (TextView) mHeaderView.findViewById(R.id.header_title_username);
        mHeaderTitle.setText(QueryPreferences.getUserName(this));

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                       switch (menuItem.getItemId()){
                           case R.id.db: {

                                   fragment = new HomeFragment();
                                   mFragmentManager.beginTransaction().replace(R.id.fcontainer,fragment).commit();

                               break;

                           }
                           case R.id.bm :{
                               //Fragment manager

                                   fragment = new BinManagerFragment();
                                   mFragmentManager.beginTransaction().replace(R.id.fcontainer,fragment).commit();

                               break;}

                           case R.id.settings :{

                               fragment = new SettingsFragment();
                               mFragmentManager.beginTransaction().replace(R.id.fcontainer,fragment).commit();

                               break;
                           }

                           case R.id.exit:{
                               finish();
                               System.exit(0);
                           }

                       }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
