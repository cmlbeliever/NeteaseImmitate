package com.cml.imitate.netease.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cml.imitate.netease.notification.MusicNotification;
import com.cml.imitate.netease.notification.NeteaseNotification;
import com.cml.imitate.netease.receiver.MusicServiceReceiver;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.cml.imitate.netease.utils.pref.PrefUtil;

/**
 * Created by cmlBeliever on 2016/4/22.
 * 播放音乐服务
 */
public class MusicService extends Service {

    private static final String TAG = MusicService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 1001;

    private NeteaseNotification<PlayMusicBean> notification;
    private MusicServiceReceiver musicServiceReceiver;
    private PlayMusicBean playMusicBean;

    private MusicBinder binder;


    @Override
    public void onCreate() {
        super.onCreate();

        binder = new MusicBinder(getApplicationContext());

        //播放音乐notification
        notification = new MusicNotification(this, NOTIFICATION_ID);
        playMusicBean = new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true);

        //注册广播监听
        musicServiceReceiver = new MusicServiceReceiver(notification);
        registerReceiver(musicServiceReceiver, new IntentFilter(com.cml.imitate.netease.receiver.MusicServiceReceiver.ACTION));
    }

    @Override
    public void onDestroy() {
        //通知栏信息监听去除
        if (null != musicServiceReceiver) {
            unregisterReceiver(musicServiceReceiver);
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //TODO 1 显示notification
        //如果允许显示通知栏，添加通知栏
        if (PrefUtil.getIsFrontService()) {
            notification.initOrUpdate(playMusicBean);
            notification.show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public interface ControlCode {
        int OK = 1000;
        int FAIL = 1001;
        int STOP = 1;
        int PAUSE = 2;
        int PLAY = 3;
        int PREPARED = 30;
        int PLAY_INDEX = 20;//根据音乐id播放
        int LOOP = 4;
        int EXIT = 5;
        int NEXT = 6;
        int LIST = 7;
        int COMPLETED = 8;
        int ERROR = 9;
    }

}
