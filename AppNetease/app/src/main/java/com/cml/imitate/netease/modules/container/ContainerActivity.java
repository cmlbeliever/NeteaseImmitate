package com.cml.imitate.netease.modules.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseActivity;
import com.cml.imitate.netease.modules.main.MainFragment;
import com.cml.imitate.netease.service.MusicService;
import com.cml.imitate.netease.utils.pref.PrefUtil;
import com.cml.second.app.common.widget.menu.NavigationMenuView;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends BaseActivity implements ContainerContract.View {

    private DrawerLayout drawer;
    private NavigationMenuView menuView;
    private FloatingActionButton floatingActionButton;
    private MenuHelper menuHelper;

    private ContainerContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucenteBar();
        setContentView(R.layout.activity_main);

        //TODO
        PrefUtil.setIsFrontService(true);
        startService(new Intent(this, MusicService.class));

//        //TODO 期待更好的方法，直接使用style就能解决
//        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(),
//                new android.support.v4.view.OnApplyWindowInsetsListener() {
//                    @Override
//                    public WindowInsetsCompat onApplyWindowInsets(View v,
//                                                                  WindowInsetsCompat insets) {
//                        KLog.d(TAG, "=====onApplyWindowInsets>>>" + getStatusBarHeight());
//                        if (toolbar.getTag() == null) {
//                            toolbar.setTag("true");
//                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
//                            params.topMargin = getStatusBarHeight();
//                            toolbar.requestLayout();
//                        }
//
//                        return insets.consumeSystemWindowInsets();
//                    }
//
//
//                });

        //设置titlebar
//        initToolbar();
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setCustomTitle(getTitle());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        menuHelper = new MenuHelper(this, drawer);

        menuView = (NavigationMenuView) findViewById(R.id.nav_view);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        menuView.setHeader(headerView);
        menuView.setMenuSelectedLisener(menuHelper);

        List<NavigationMenuView.MenuItem> menus = new ArrayList<>();
        menus.add(new NavigationMenuView.MenuItem(R.string.app_name, android.R.drawable.ic_menu_camera));
        menuView.setMenus(menus);


//        TextView navigationView = (TextView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(menuHelper);

        //将默认的menu栏迁移到自定义的custom title容器上，以解决title居中问题
//        setupCustomNavigation();

        //TODO
        new ContainerPresenter(this);
        setContainer(MainFragment.getInstance());
    }

    @Override
    public void setContainer(Fragment target) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, target);
        transaction.commit();
    }

    @Override
    public void toggleMenu() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }

    }

    @Override
    public void setPresenter(ContainerContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
