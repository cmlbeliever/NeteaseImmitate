package com.cml.imitate.netease.modules.container;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseActivity;
import com.cml.imitate.netease.modules.BasePresenter;
import com.cml.imitate.netease.modules.main.MainFragment;
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

        //隐藏导航栏
        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//全屏隐藏虚拟按键
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            window.setAttributes(params);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

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
        if (target instanceof BasePresenter) {
            BasePresenter presenter = (BasePresenter) target;
            presenter.setContainerView(this);
        }
        transaction.commit();
    }

    @Override
    public void toggleMenu() {

    }

    @Override
    public void setPresenter(ContainerContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
