package com.cml.imitate.netease.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by cmlBeliever on 2016/4/25.
 */
public abstract class NeteaseNotification<T> {

    protected Context context;
    protected int notifyId;
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

    /**
     * 判断是否已经初始化
     *
     * @return true 已经初始化 else false
     */
    public boolean isInit() {
        return null != notification;
    }


    public void delete() {
        getNotificationManager().cancel(notifyId);
    }

    public void show() {
        getNotificationManager().notify(notifyId, notification);
    }
}
