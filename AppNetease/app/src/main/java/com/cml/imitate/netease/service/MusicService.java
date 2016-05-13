package com.cml.imitate.netease.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.cml.imitate.netease.notification.MusicNotification;
import com.cml.imitate.netease.notification.NeteaseNotification;
import com.cml.imitate.netease.receiver.MusicServiceReceiver;
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

    private NeteaseNotification<PlayMusicBean> notification;
    private MusicServiceReceiver musicServiceReceiver;
    private PlayMusicBean playMusicBean;
    private MusicPlayerClient musicPlayerClient;//音乐播放控制器

    @Override
    public void onCreate() {
        super.onCreate();

        //播放音乐notification
        notification = new MusicNotification(this, NOTIFICATION_ID);
        playMusicBean = new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true);

        //
        musicPlayerClient = new MusicPlayerClient(this);

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
//            Intent intent = NotificationReceiver.getPlayMusicIntent(new PlayMusicBean(NOTIFICATION_ID, "url", "name", "author", false, true));
//            sendBroadcast(intent);
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
            musicPlayerClient.play((Uri) msg.obj);
            try {
                target.send(Message.obtain());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            KLog.d(TAG, "===handleMessage>>" + msg);
        }
    }

}
