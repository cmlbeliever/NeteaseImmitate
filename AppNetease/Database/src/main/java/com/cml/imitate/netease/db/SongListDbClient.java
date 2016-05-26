package com.cml.imitate.netease.db;

import android.content.ContentValues;
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
        //清空播放列表
        helper.getWritableDatabase().delete(SongListContract.TABLE, null, null);
        //生成播放列表
        helper.getWritableDatabase().execSQL("INSERT INTO " + SongListContract.TABLE + "(" + SongListContract._ID + ") SELECT " + SongContract.Columns._ID + "  FROM " + SongContract.TABLE + (random ? " ORDER BY RANDOM()" : ""));

        generateDefaultPlayingSong();
    }

    public void generateDefaultPlayingSong() {
        //设置第一个默认播放
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT ts.* FROM " + SongListContract.TABLE + " tsl INNER JOIN " + SongContract.TABLE + " ts ON ts." + SongContract.Columns._ID + "=tsl." + SongListContract._ID + " limit 1",
                null);

        if (null != cursor) {
            if (cursor.moveToNext()) {
                Song song = SongDbClient.loadFromCursor(cursor);
                setPlay(song.id);
            }
            cursor.close();
        }
    }

    public List<Song> getSongList() {
        List<Song> songList = new ArrayList<>();
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT ts.* FROM " + SongListContract.TABLE + " tsl INNER JOIN " + SongContract.TABLE + " ts ON ts." + SongContract.Columns._ID + "=tsl." + SongListContract._ID,
                null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                songList.add(SongDbClient.loadFromCursor(cursor));
            }
            cursor.close();
        }
        return songList;
    }


    public Song getSong(int status) {
        Cursor cursor = helper.getWritableDatabase().rawQuery("SELECT ts.* FROM " + SongListContract.TABLE + " tsl INNER JOIN " +
                        SongContract.TABLE + " ts ON ts." + SongContract.Columns._ID + "=tsl." + SongListContract._ID + " and tsl." + SongListContract.STATUS + "=" + status,
                null);
        Song song = null;
        if (null != cursor) {
            if (cursor.moveToNext()) {
                song = SongDbClient.loadFromCursor(cursor);
            }
            cursor.close();
        }
        return song;
    }

    public void setPlay(int songId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SongListContract.STATUS, SongListContract.STATUS_DEFAULT);
        //还原设置信息
        helper.getWritableDatabase().update(SongListContract.TABLE, contentValues, null, new String[]{});

        contentValues.clear();
        contentValues.put(SongListContract.STATUS, SongListContract.STATUS_PLAY);

        //设置指定id为状态
        helper.getWritableDatabase().update(SongListContract.TABLE, contentValues, SongListContract._ID + "=" + songId, null);
    }

    public int next(int songId) {

        Cursor cursor = helper.getWritableDatabase().query(SongListContract.TABLE, new String[]{SongListContract.LIST_ID}, SongListContract._ID + "=?", new String[]{
                String.valueOf(songId)
        }, null, null, null);

        if (null != cursor) {
            if (cursor.moveToNext()) {
                int listId = cursor.getInt(0);
                cursor.close();
                //获取下一首音乐id
                cursor = helper.getWritableDatabase().query(SongListContract.TABLE, new String[]{SongListContract._ID}, SongListContract.LIST_ID + ">?"
                        , new String[]{String.valueOf(listId)}, null, null, SongListContract.LIST_ID + " limit 1");
                if (cursor.moveToNext()) {
                    int nextSongId = cursor.getInt(0);
                    cursor.close();
                    return nextSongId;
                }
            }
        }
        return 0;
    }
}
