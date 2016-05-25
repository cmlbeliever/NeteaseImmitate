package com.cml.imitate.netease.db.contract;

import android.provider.BaseColumns;

/**
 * Created by cmlBeliever on 2016/5/16.
 */
public interface SongListContract extends BaseColumns {
    String TABLE = "t_song_list";

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_PLAY = 2;
    public static final int STATUS_PAUSE = 3;
    /**
     * 1、默认值
     * 2、当前播放
     * 3、正在播放
     * 4、暂停
     * <p>type:int</p>
     */
    String STATUS = "status";
    /**
     * 播放的进度
     * <p>type:int</p>
     */
    String POSITION = "play_position";
}
