package com.xpx.ardapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xpx.base.wrapper.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu_1:
                        break;
                    case R.id.navigation_menu_2:
                        break;
                    default:
                        break;
                }

                // Close the navigation drawer when an item is selected.
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.navigation_menu_1:
                showMenuDrop();
                break;
            case R.id.navigation_menu_2:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMenuDrop() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.navigation_menu_1));    //显示在menu1下面
        popup.getMenuInflater().inflate(R.menu.filter_list, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sortByTime:
                        Toast toast = Toast.makeText(getApplicationContext(), "通过时间排序", Toast.LENGTH_SHORT);    //必须获取applicationContext，否则会偏移
                        toast.show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}
