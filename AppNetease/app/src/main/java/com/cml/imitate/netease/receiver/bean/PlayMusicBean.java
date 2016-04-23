package com.cml.imitate.netease.receiver.bean;

import java.io.Serializable;

/**
 * Created by cmlBeliever on 2016/4/22.
 */
public class PlayMusicBean implements Serializable {
    public int notifyId;
    public String iconUrl;
    public String name;
    public String author;
    public boolean isPlay;
    public boolean showLrc;
    public boolean visible = true;//是否显示

    public PlayMusicBean(int notifyId, String iconUrl, String name, String author, boolean isPlay, boolean showLrc, boolean visible) {
        this.notifyId = notifyId;
        this.iconUrl = iconUrl;
        this.name = name;
        this.author = author;
        this.isPlay = isPlay;
        this.showLrc = showLrc;
        this.visible = visible;
    }

    public PlayMusicBean() {
    }

    public PlayMusicBean(int notifyId, String iconUrl, String name, String author, boolean isPlay, boolean showLrc) {
        this.notifyId = notifyId;
        this.iconUrl = iconUrl;
        this.name = name;
        this.author = author;
        this.isPlay = isPlay;
        this.showLrc = showLrc;
    }

    public static PlayMusicBean cloneBean(PlayMusicBean src) {
        return new PlayMusicBean(src.notifyId, src.iconUrl, src.name, src.author, src.isPlay, src.showLrc);
    }
}
