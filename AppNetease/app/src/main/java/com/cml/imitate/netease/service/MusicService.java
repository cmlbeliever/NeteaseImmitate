package com.cml.imitate.netease.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cml.imitate.netease.notification.PlayMusicNotification;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.socks.library.KLog;

/**
 * Created by cmlBeliever on 2016/4/22.
 * 播放音乐服务
 */
public class MusicService extends Service {

    private static final String TAG = MusicService.class.getSimpleName();

    private final Messenger messenger = new Messenger(new MusicHandler());
    private static final int NOTIFICATION_ID = 1001;

    private PlayMusicNotification notification;

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO 1 显示notification
        //如果允许显示通知栏，添加通知栏
//        if (PrefUtil.getIsFrontService()) {
//            Intent intent = NotificationReceiver.getPlayMusicIntent(new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true));
//            sendBroadcast(intent);
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service" + notification, Toast.LENGTH_LONG).show();
        if (null == notification) {
            notification = new PlayMusicNotification(NOTIFICATION_ID, this);
            notification.initNotification(new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true));
            notification.show();
        } else {
            notification.update(new PlayMusicBean(NOTIFICATION_ID, "url", "update==", "update==>", false, true));
            notification.show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private class MusicHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Messenger target = msg.replyTo;
            KLog.d(TAG, "===handleMessage>>" + msg);
        }
    }
}
