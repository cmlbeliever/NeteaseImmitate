package com.cml.imitate.netease.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.BaseFragment;

import butterknife.OnClick;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class MainFragment extends BaseFragment implements MainContract.View {

    private MainContract.Presenter presenter;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.toolbar_menu)
    public void onMenuClick(View v) {
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
