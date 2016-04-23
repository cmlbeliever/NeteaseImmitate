package com.cml.imitate.netease.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cml.imitate.netease.notification.PlayMusicNotification;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.socks.library.KLog;

/**
 * Created by cmlBeliever on 2016/4/22.
 * 通知栏广播接收器
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationReceiver.class.getSimpleName();

    public static final String EXTRA_KEY_TYPE = "notificationreceiver.key.type";
    public static final String EXTRA_KEY_DATA = "notificationreceiver.key.data";
    public static final String ACTION_PLAY_MUSIC = "com.cml.imitate.netease.receiver.notificationreceiver.playmusic";


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "receiver:" + intent.getAction(), Toast.LENGTH_LONG).show();

        KLog.d(TAG, "NotificationReceiver=====>" + intent.getAction());

        switch (intent.getAction()) {
            case ACTION_PLAY_MUSIC://显示音乐播放的通知
                PlayMusicBean bean = (PlayMusicBean) intent.getSerializableExtra(EXTRA_KEY_DATA);
                PlayMusicNotification notification = new PlayMusicNotification(bean.notifyId, context);
                //删除通知栏信息
                if (!bean.visible) {
                    notification.delete();
                    break;
                }

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
