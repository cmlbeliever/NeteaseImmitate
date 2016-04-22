package com.cml.imitate.netease.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.cml.imitate.netease.receiver.NotificationReceiver;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.cml.imitate.netease.utils.pref.PrefUtil;
import com.socks.library.KLog;

/**
 * Created by cmlBeliever on 2016/4/22.
 * 播放音乐服务
 */
public class MusicService extends Service {

    private static final String TAG = MusicService.class.getSimpleName();

    private final Messenger messenger = new Messenger(new MusicHandler());
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 1 显示notification
        //如果允许显示通知栏，添加通知栏
        if (PrefUtil.getIsFrontService()) {
            Intent intent = NotificationReceiver.getPlayMusicIntent(new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true));
            sendBroadcast(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
