package com.cml.imitate.netease.modules.container;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class ContainerPresenter implements ContainerContract.Presenter {

    private ContainerContract.View homeView;

    public ContainerPresenter(ContainerContract.View homeView) {
        this.homeView = homeView;
        homeView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

}
