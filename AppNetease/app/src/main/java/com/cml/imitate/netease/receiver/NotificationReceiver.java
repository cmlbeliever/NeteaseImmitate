package com.cml.imitate.netease.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cml.imitate.netease.notification.PlayMusicNotification;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;

/**
 * Created by cmlBeliever on 2016/4/22.
 * 通知栏广播接收器
 */
public class NotificationReceiver extends BroadcastReceiver {
    public static final String EXTRA_KEY_TYPE = "notificationreceiver.key.type";
    public static final String EXTRA_KEY_DATA = "notificationreceiver.key.data";
    public static final String ACTION_PLAY_MUSIC = "com.cml.imitate.netease.receiver.notificationreceiver.playmusic";


    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PLAY_MUSIC://显示音乐播放的通知
                PlayMusicBean bean = (PlayMusicBean) intent.getSerializableExtra(EXTRA_KEY_DATA);
                PlayMusicNotification notification = new PlayMusicNotification(bean.notifyId, context);
                notification.initNotification(bean);
                notification.show();
                break;
        }
    }

    /**
     * 获取播放音乐intent
     *
     * @param bean
     * @return
     */
    public static Intent getPlayMusicIntent(PlayMusicBean bean) {
        Intent intent = new Intent(ACTION_PLAY_MUSIC);
        intent.putExtra(EXTRA_KEY_DATA, bean);
        return intent;
    }
}
