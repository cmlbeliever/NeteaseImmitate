package com.cml.imitate.netease.modules.main;

import com.cml.imitate.netease.modules.container.ContainerContract;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View homeView;
    private ContainerContract.View mainView;

    public MainPresenter(MainContract.View homeView) {
        this.homeView = homeView;
        homeView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setContainerView(ContainerContract.View containerView) {
        this.mainView = containerView;
    }

}
