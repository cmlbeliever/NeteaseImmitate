package com.cml.imitate.netease.modules.local;

import android.content.Context;

import com.cml.imitate.netease.modules.BasePresenter;
import com.cml.imitate.netease.modules.BaseView;

/**
 * Created by cmlBeliever on 2016/4/26.
 */
public interface MusicScannerContract {
    interface View extends BaseView<Present> {
        void updateScannerText(String path);

        void updateScannerResult(String text);

        void startScanAnim();

        void stopScanAnim();

        Context getContext();
    }

    interface Present extends BasePresenter {
        void scan(Context context);
    }
}
