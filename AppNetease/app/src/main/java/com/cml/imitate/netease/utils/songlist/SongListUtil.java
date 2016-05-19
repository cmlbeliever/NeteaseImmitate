package com.cml.imitate.netease.utils.songlist;

import com.cml.imitate.netease.application.AppApplication;
import com.cml.imitate.netease.db.SongListDbClient;
import com.cml.imitate.netease.db.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmlBeliever on 2016/5/19.
 */
public class SongListUtil {
    private static SongListUtil songListUtil = new SongListUtil();

    public static SongListUtil getInstance() {
        return songListUtil;
    }

    private List<Song> songList = new ArrayList<>();
    private int currentIndex;
    private SongListDbClient songListDbClient;
    private boolean init;

    private SongListUtil() {
        songListDbClient = new SongListDbClient(AppApplication.getContext());
        songList = songListDbClient.getSongList();
    }


    public void randomList() {
        songListDbClient.generateSongList(true);
        songList = songListDbClient.getSongList();
    }

    public void generateList() {
        songListDbClient.generateSongList(false);
        songList = songListDbClient.getSongList();
    }


    public void clear() {
        songList.clear();
    }

    public Song getCurrent() {
        return songList.get(currentIndex);
    }

    public Song next() {
        currentIndex++;
        //列表已经是最后一个了
        if (currentIndex >= songList.size()) {
            return null;
        }
        return songList.get(currentIndex);
    }

    /**
     * 是否为最后一个
     *
     * @return
     */
    public boolean isLast() {
        return currentIndex == songList.size() - 1;
    }

    public List<Song> getSongList() {
        return songList;
    }
}
