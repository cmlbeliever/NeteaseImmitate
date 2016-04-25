package com.cml.imitate.netease.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cml.imitate.netease.notification.NeteaseNotification;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;

/**
 * Created by cmlBeliever on 2016/4/25.
 * 状态栏信息接收广播
 */
public class MusicServiceReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.cml.imitate.netease.service.musicservice.musicservicereceiver.event";
    public static final String EXTRA_DATA = "data";

    private NeteaseNotification<PlayMusicBean> notification;

    public MusicServiceReceiver(NeteaseNotification<PlayMusicBean> notification) {
        this.notification = notification;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PlayMusicBean bean = (PlayMusicBean) intent.getSerializableExtra(EXTRA_DATA);

        if (bean.visible) {
            notification.initOrUpdate(bean);
            notification.show();
        } else {
            notification.delete();
        }

    }
}
