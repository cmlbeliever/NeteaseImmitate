package com.cml.imitate.netease.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.db.contract.SongContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmlBeliever on 2016/5/12.
 */
public class SongDbClient extends DataBaseClient {
    public SongDbClient(Context context) {
        super(context);
    }

    public Song query(int id) {
        Song song = null;
        Cursor cursor = helper.getWritableDatabase().query(SongContract.TABLE, null, SongContract.Columns._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                song = loadFromCursor(cursor);
            }
            cursor.close();
        }
        return song;
    }

    public static Song loadFromCursor(Cursor cursor) {
        Song song = new Song();
        song.name = cursor.getString(cursor.getColumnIndex(SongContract.Columns.NAME));
        song.url = cursor.getString(cursor.getColumnIndex(SongContract.Columns.URL));
        song.id = cursor.getInt(cursor.getColumnIndex(SongContract.Columns._ID));
        song.album = cursor.getString(cursor.getColumnIndex(SongContract.Columns.ALBUM));
        song.artist = cursor.getString(cursor.getColumnIndex(SongContract.Columns.ARTIST));
        song.duration = cursor.getInt(cursor.getColumnIndex(SongContract.Columns.DURATION));
        song.album = cursor.getString(cursor.getColumnIndex(SongContract.Columns.ALBUM));
        song.tilte = cursor.getString(cursor.getColumnIndex(SongContract.Columns.TILTE));
        return song;
    }

    public List<Song> query() {
        List<Song> songs = new ArrayList<Song>();
        Cursor cursor = helper.getWritableDatabase().query(SongContract.TABLE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                songs.add(loadFromCursor(cursor));
            }
            cursor.close();
        }
        return songs;
    }

    public long replaceSong(Song song) {

        ContentValues values = new ContentValues();
        values.put(SongContract.Columns._ID, song.id);
        values.put(SongContract.Columns.ALBUM, song.album);
        values.put(SongContract.Columns.ARTIST, song.artist);
        values.put(SongContract.Columns.DURATION, song.duration);
        values.put(SongContract.Columns.TILTE, song.tilte);
        values.put(SongContract.Columns.NAME, song.name);
        values.put(SongContract.Columns.URL, song.url);

        return helper.getWritableDatabase().replace(SongContract.TABLE, null, values);
    }
}
