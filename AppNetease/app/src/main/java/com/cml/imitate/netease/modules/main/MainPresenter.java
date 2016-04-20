package com.cml.imitate.netease.modules.main;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View homeView;

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


}
