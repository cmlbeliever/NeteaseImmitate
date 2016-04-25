package com.cml.imitate.netease.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by cmlBeliever on 2016/4/25.
 */
public abstract class NeteaseNotification<T> {

    private Context context;
    private int notifyId;
    protected Notification notification;

    public NeteaseNotification(Context context, int notifyId) {
        this.context = context;
        this.notifyId = notifyId;
    }

    /**
     * 更新或初始化notification
     *
     * @param bean
     */
    public abstract void initOrUpdate(T bean);

    protected NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void delete() {
        getNotificationManager().cancel(notifyId);
    }

    public void show() {
        getNotificationManager().notify(notifyId, notification);
    }
}
