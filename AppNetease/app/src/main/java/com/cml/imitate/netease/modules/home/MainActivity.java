package com.cml.imitate.netease.modules.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseActivity;
import com.cml.second.app.common.widget.menu.NavigationMenuView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawer;
    private NavigationMenuView menuView;
    private FloatingActionButton floatingActionButton;
    private MenuHelper menuHelper;
    private LinearLayout toolbarCustomLayout;//自定义toolbar背景界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置titlebar
        initToolbar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setCustomTitle(getTitle());

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        menuHelper = new MenuHelper(this, drawer);

        menuView = (NavigationMenuView) findViewById(R.id.nav_view);

        List<NavigationMenuView.MenuItem> menus = new ArrayList<>();
        menus.add(new NavigationMenuView.MenuItem(R.string.app_name, android.R.drawable.ic_menu_camera));
        menuView.setMenus(menus);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        menuView.setHeader(headerView);
        menuView.setMenuSelectedLisener(menuHelper);

//        TextView navigationView = (TextView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(menuHelper);

        //将默认的menu栏迁移到自定义的custom title容器上，以解决title居中问题
//        setupCustomNavigation();
    }
}
