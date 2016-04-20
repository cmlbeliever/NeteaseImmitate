package com.cml.imitate.netease.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;
import com.cml.imitate.netease.modules.container.ContainerContract;

import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class MainFragment extends BaseFragment implements MainContract.View {

    private MainContract.Presenter presenter;

    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        new MainPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

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
