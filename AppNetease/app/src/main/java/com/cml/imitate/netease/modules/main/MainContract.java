package com.cml.imitate.netease.modules.main;

import com.cml.imitate.netease.modules.BasePresenter;
import com.cml.imitate.netease.modules.BaseView;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public interface MainContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
