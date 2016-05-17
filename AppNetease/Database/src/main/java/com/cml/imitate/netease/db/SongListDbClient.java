package com.cml.imitate.netease.db;

import android.content.Context;
import android.database.Cursor;

import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.db.contract.SongContract;
import com.cml.imitate.netease.db.contract.SongListContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmlBeliever on 2016/5/16.
 */
public class SongListDbClient extends DataBaseClient {
    public SongListDbClient(Context context) {
        super(context);
    }

    /**
     * 随机生成歌曲列表
     *
     * @param random
     */
    public void generateSongList(boolean random) {
        List<Song> songs = query();
        //清空播放列表
        helper.getWritableDatabase().delete(SongListContract.TABLE, null, null);
        //生成播放列表
        try {
            helper.getWritableDatabase().execSQL("INSERT INTO " + SongListContract.TABLE + "(" + SongListContract._ID + ") SELECT " + SongContract.Columns._ID + "  FROM " + SongContract.TABLE + (random ? " ORDER BY RANDOM()" : ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.getWritableDatabase().query(SongListContract.TABLE, null, null, null, null, null, null);
        getSongList();
    }

    public List<Song> query() {
        List<Song> songs = new ArrayList<Song>();
        Cursor cursor = helper.getWritableDatabase().query(SongContract.TABLE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.name = cursor.getString(cursor.getColumnIndex(SongContract.Columns.NAME));
                song.url = cursor.getString(cursor.getColumnIndex(SongContract.Columns.URL));
                songs.add(song);
            }
            cursor.close();
        }
        return songs;
    }

    public List<Song> getSongList() {
        List<Song> songList = new ArrayList<>();
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT ts.* FROM " + SongListContract.TABLE + " tsl INNER JOIN " + SongContract.TABLE + " ts ON ts." + SongContract.Columns._ID + "=tsl." + SongListContract._ID,
                null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                song.name = cursor.getString(cursor.getColumnIndex(SongContract.Columns.NAME));
                song.url = cursor.getString(cursor.getColumnIndex(SongContract.Columns.URL));
                songList.add(song);
            }
            cursor.close();
        }
        return songList;
    }

}
