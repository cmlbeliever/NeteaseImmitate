package com.cml.imitate.netease.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.container.ContainerActivity;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.cml.imitate.netease.service.MusicService;

/**
 * Created by cmlBeliever on 2016/4/25.
 */
public class MusicNotification extends NeteaseNotification<PlayMusicBean> {
    public MusicNotification(Context context, int notifyId) {
        super(context, notifyId);
    }

    @Override
    public void initOrUpdate(PlayMusicBean bean) {
        Intent resultIntent = new Intent(context, ContainerActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(bean.notifyId, PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews remoteViews = getRemoteViews();

        remoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notify_song_name, bean.name);
        remoteViews.setTextViewText(R.id.notify_song_author, bean.author);
        //播放暂停键
        remoteViews.setImageViewResource(R.id.notify_btn_play_ctrl, bean.isPlay ? R.drawable.play_btn_play : R.drawable.play_btn_pause);
        remoteViews.setImageViewResource(R.id.notify_btn_lrc, bean.showLrc ? R.drawable.actionbar_menu_icn_lrc : R.drawable.actionbar_menu_icn_lrc_dis);

        //TODO 播放暂停按键触发事件
        PlayMusicBean ctrlBean = PlayMusicBean.cloneBean(bean);
        ctrlBean.isPlay = !bean.isPlay;//点击按钮触发相反事件
        Intent playCtrlIntent = new Intent(MusicService.MusicServiceReceiver.ACTION);
        playCtrlIntent.putExtra(MusicService.MusicServiceReceiver.EXTRA_DATA, ctrlBean);
        remoteViews.setOnClickPendingIntent(R.id.notify_btn_play_ctrl, PendingIntent.getBroadcast(context, notifyId, playCtrlIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        // TODO 点击下一首触发事件

        //TODO 点击删除按钮事件
        PlayMusicBean delBean = PlayMusicBean.cloneBean(bean);
        delBean.visible = false;
        Intent delIntent = new Intent(MusicService.MusicServiceReceiver.ACTION);
        delIntent.putExtra(MusicService.MusicServiceReceiver.EXTRA_DATA, delIntent);
        remoteViews.setOnClickPendingIntent(R.id.notify_btn_del, PendingIntent.getBroadcast(context, notifyId, delIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        //第一次初始化notification
        if (!isInit()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContent(remoteViews).setContentIntent(resultPendingIntent);
            builder.setSmallIcon(R.drawable.ic_menu_camera);
            builder.setAutoCancel(false);
            builder.setOngoing(true);
            notification = builder.build();
        }
    }


    private RemoteViews getRemoteViews() {
        //init notification
        if (null == notification) {
            return new RemoteViews(context.getPackageName(), R.layout.notification_play);
        }
        return notification.contentView;
    }
}
