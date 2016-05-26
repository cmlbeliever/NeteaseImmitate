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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.modules.BaseActivity;
import com.cml.imitate.netease.modules.main.MainFragment;
import com.cml.imitate.netease.service.MusicService;
import com.cml.imitate.netease.utils.pref.PrefUtil;
import com.cml.second.app.common.widget.menu.NavigationMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContainerActivity extends BaseActivity implements ContainerContract.View {

    private DrawerLayout drawer;
    private NavigationMenuView menuView;
    private FloatingActionButton floatingActionButton;
    private MenuHelper menuHelper;

    @Bind(R.id.playbar_music_name)
    TextView playbarNameView;
    @Bind(R.id.playbar_author)
    TextView playbarAuthorView;
    @Bind(R.id.playbar_header_img)
    ImageView playbarHeaderImageView;
    @Bind(R.id.playbar_play_ctrl)
    View playbarControlView;
    @Bind(R.id.playbar_progress)
    ProgressBar playbarProgressView;

    private ContainerContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucenteBar();
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        //TODO
        PrefUtil.setIsFrontService(true);
        startService(new Intent(this, MusicService.class));

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
        new ContainerPresenter(this, this);
        setContainer(MainFragment.getInstance());

        //启动或绑定音乐播放器服务
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setContainer(Fragment target) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, target);
        transaction.commit();
    }

    @Override
    public void setPlaybar(Song song) {
        playbarNameView.setText(song.tilte);
        playbarAuthorView.setText(song.artist);
        playbarProgressView.setProgress(0);
        //TODO url
    }

    @Override
    public void setPlayStatus(boolean play) {
        playbarControlView.setSelected(play);
    }

    @Override
    public void setPlayProgress(int value) {
        playbarProgressView.setProgress(value);
    }

    @Override
    public void toggleMenu() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }

    }

    @OnClick({R.id.playbar_play_ctrl, R.id.playbar_next, R.id.playbar_song_list})
    public void onPlayBarItemClicked(View v) {
        switch (v.getId()) {
            case R.id.playbar_play_ctrl:
                if (!v.isSelected()) {//播放
                    presenter.play();
                } else {
                    presenter.pause();
                }
                v.setSelected(!v.isSelected());
                break;
            case R.id.playbar_next:
                presenter.next();
                break;
        }
    }


    @Override
    public void setPresenter(ContainerContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
