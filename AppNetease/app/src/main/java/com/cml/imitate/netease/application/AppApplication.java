package com.cml.imitate.netease.application;

import android.app.Application;
import android.content.Context;

import com.cml.second.app.common.crash.CrashHandler;

/**
 * Created by cmlBeliever on 2016/2/24.
 */
public class AppApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        new CrashHandler().init(this);
        context = getApplicationContext();
    }
}
