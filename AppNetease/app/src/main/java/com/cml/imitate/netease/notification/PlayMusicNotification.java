package com.cml.imitate.netease.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.cml.imitate.netease.MainActivity;
import com.cml.imitate.netease.R;
import com.cml.imitate.netease.receiver.bean.PlayMusicBean;

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
        Intent resultIntent = Intent.makeMainActivity(new ComponentName(context, MainActivity.class));
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_play);
        remoteViews.setImageViewResource(R.id.notify_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notify_song_name, bean.name);
        remoteViews.setTextViewText(R.id.notify_song_author, bean.author);
        //播放暂停键
        remoteViews.setImageViewResource(R.id.notify_btn_play_ctrl, bean.isPlay ? R.drawable.play_btn_play : R.drawable.play_btn_pause);
        remoteViews.setImageViewResource(R.id.notify_btn_lrc, bean.showLrc ? R.drawable.actionbar_menu_icn_lrc : R.drawable.actionbar_menu_icn_lrc_dis);
//        remoteViews.setBoolean(R.id.notify_btn_play_ctrl, "setSelected", bean.isPlay);
//        remoteViews.setBoolean(R.id.notify_btn_lrc, "setSelected", bean.showLrc);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContent(remoteViews).setContentIntent(resultPendingIntent);
        builder.setSmallIcon(R.drawable.ic_menu_camera);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
//        builder.setColor(Color.RED);
        notification = builder.build();
//        notification.contentIntent = resultPendingIntent;

//        notification=new Notification();
//        notification.contentView=remoteViews;
//        notification.contentIntent=resultPendingIntent;
//        notification.tickerText="sdf";
    }

    public void show() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    public Notification getNotification() {
        return notification;
    }
}