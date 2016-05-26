package com.cml.imitate.netease.utils.songlist;

import android.content.Context;

import com.cml.imitate.netease.db.SongListDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.db.contract.SongListContract;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/5/25.
 */
public class SonglistHelper {
    private SongListDbClient songListDbClient;

    public SonglistHelper(Context context) {
        songListDbClient = new SongListDbClient(context);
    }

    public List<Song> getSongList() {
        return songListDbClient.getSongList();
    }

    public void generateSongList(boolean random) {
        songListDbClient.generateSongList(random);
    }


    public void generateDefaultPlayingSong() {
        songListDbClient.generateDefaultPlayingSong();
    }

    public Song getSong(int status) {
        return songListDbClient.getSong(status);
    }

    public int next() {
        Song current = songListDbClient.getSong(SongListContract.STATUS_PLAY);
        if (null == current) {
            current = songListDbClient.getSong(SongListContract.STATUS_DEFAULT);
            if (null == current) {
                return 0;
            }
            //设置为播放状态
            songListDbClient.setPlay(current.id);
            return current.id;
        }

        int songId = songListDbClient.next(current.id);

        //到最后了
        if (songId == 0) {
            Song song = songListDbClient.getSong(SongListContract.STATUS_DEFAULT);
            //设置为播放状态
            songListDbClient.setPlay(song.id);
        } else {
            songListDbClient.setPlay(songId);
        }
        return songId;
    }
}
