package com.cml.imitate.netease.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;
import com.cml.imitate.netease.modules.container.ContainerContract;
import com.cml.imitate.netease.modules.main.adapter.MainFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class MainFragment extends BaseFragment implements MainContract.View {

    private MainContract.Presenter presenter;
    @Bind(R.id.home_viewpager)
     ViewPager homeViewPager;

//    @Bind(R.id.home_discover)
//    View discoverView;
//    @Bind(R.id.home_music)
//    View musicView;
//    @Bind(R.id.home_friend)
//    View friendView;

    @Bind({R.id.home_discover,R.id.home_music,R.id.home_friend})
    List<View> toolbarViews;

    private FragmentPagerAdapter pagerAdapter;

    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        new MainPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //设置viewpager
          initViewPager();
    }

    /**
     * 设置viewpager
     */
    private void initViewPager() {
        List<Fragment> fragments=new ArrayList<Fragment>(3);
        fragments.add(SuggestionFragment.getInstance());
        fragments.add(SetListFragment.getInstance());
        fragments.add(FriendFragment.getInstance());

        pagerAdapter=new MainFragmentAdapter(getFragmentManager(),fragments);
        homeViewPager.setAdapter(pagerAdapter);
        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //移动时
                ButterKnife.apply(toolbarViews, new ButterKnife.Action<View>() {
                    @Override
                    public void apply(View view, int index) {
                        view.setSelected(false);
                    }
                });
                toolbarViews.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //默认第一个选中
        toolbarViews.get(0).setSelected(true);
    }

    @OnClick(R.id.toolbar_menu)
    public void onMenuClick(View v) {
        ContainerContract.View containerView = (ContainerContract.View) getActivity();
        if (null != containerView) {
            containerView.toggleMenu();
        }
    }

    @Override
    protected int getContainerRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
