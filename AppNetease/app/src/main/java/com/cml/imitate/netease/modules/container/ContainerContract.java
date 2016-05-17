package com.cml.imitate.netease.modules.container;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.modules.BasePresenter;
import com.cml.imitate.netease.modules.BaseView;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public interface ContainerContract {
    interface View extends BaseView<Presenter> {
        /**
         * 设置显示fragment
         *
         * @param target
         */
        void setContainer(Fragment target);

        void setPlaybar(Song song);

        /**
         * 显示或关闭菜单
         */
        void toggleMenu();
    }

    interface Presenter extends BasePresenter {
        void playMusic(Context context, Uri uri);

        void bindService();

        void play();

        void next();

        void pause();
    }
}
