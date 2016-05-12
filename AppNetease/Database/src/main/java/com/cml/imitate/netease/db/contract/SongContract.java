package com.cml.imitate.netease.db.contract;

import android.provider.BaseColumns;

/**
 * Created by cmlBeliever on 2016/5/12.
 */
public interface SongContract {
    String TABLE = "t_song";

    interface Columns extends BaseColumns {
        String TILTE = "tilte";
        String ALBUM = "album";
        String ARTIST = "artist";
        String URL = "url";
        String NAME = "name";
        String DURATION = "duration";
    }
}
