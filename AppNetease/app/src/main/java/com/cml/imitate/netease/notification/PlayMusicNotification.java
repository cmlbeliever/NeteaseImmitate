package com.cml.imitate.netease.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.cml.imitate.netease.R;
import com.cml.imitate.netease.modules.container.ContainerActivity;
import com.cml.imitate.netease.receiver.NotificationReceiver;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;
import com.cml.imitate.netease.service.MusicService;

/**
 * 通知栏信息
 */
public class PlayMusicNotification {

    private Notification notification;
    private Context context;
    private int id;

    public PlayMusicNotification(int id, Context context) {
        this.id = id;
        this.context = context;
    }

    public void initNotification(PlayMusicBean bean) {
        Intent resultIntent = new Intent(context, ContainerActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(bean.notifyId, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_play);
        remoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notify_song_name, bean.name);
        remoteViews.setTextViewText(R.id.notify_song_author, bean.author);
        //播放暂停键
        remoteViews.setImageViewResource(R.id.notify_btn_play_ctrl, bean.isPlay ? R.drawable.play_btn_play : R.drawable.play_btn_pause);
        remoteViews.setImageViewResource(R.id.notify_btn_lrc, bean.showLrc ? R.drawable.actionbar_menu_icn_lrc : R.drawable.actionbar_menu_icn_lrc_dis);

        //TODO 播放暂停按键触发事件
        PlayMusicBean ctrlBean = PlayMusicBean.cloneBean(bean);
        ctrlBean.isPlay = !bean.isPlay;//点击按钮触发相反事件
//        Intent playCtrlIntent = NotificationReceiver.getPlayMusicIntent(ctrlBean);
//        remoteViews.setOnClickPendingIntent(R.id.notify_btn_play_ctrl, PendingIntent.getBroadcast(context, id * 10, playCtrlIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Intent playCtrlIntent = new Intent(context, MusicService.class);
        remoteViews.setOnClickPendingIntent(R.id.notify_btn_play_ctrl, PendingIntent.getService(context, id * 10, playCtrlIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        // TODO 点击下一首触发事件

        //TODO 点击删除按钮事件
        PlayMusicBean delBean = PlayMusicBean.cloneBean(bean);
        delBean.visible = false;
        Intent delIntent = NotificationReceiver.getPlayMusicIntent(delBean);
        remoteViews.setOnClickPendingIntent(R.id.notify_btn_del, PendingIntent.getBroadcast(context, id * 100, delIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContent(remoteViews).setContentIntent(resultPendingIntent);
        builder.setSmallIcon(R.drawable.ic_menu_camera);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        notification = builder.build();
    }

    public void update(PlayMusicBean bean) {

        RemoteViews remoteViews = notification.contentView;
        remoteViews.setTextViewText(R.id.notify_song_name, "update==");
        remoteViews.setTextViewText(R.id.notify_song_author, bean.author);
        //播放暂停键
        remoteViews.setImageViewResource(R.id.notify_btn_play_ctrl, bean.isPlay ? R.drawable.play_btn_play : R.drawable.play_btn_pause);
        remoteViews.setImageViewResource(R.id.notify_btn_lrc, bean.showLrc ? R.drawable.actionbar_menu_icn_lrc : R.drawable.actionbar_menu_icn_lrc_dis);
    }

    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    public void delete() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    public Notification getNotification() {
        return notification;
    }
}