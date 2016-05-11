package com.cml.imitate.netease.modules.local;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;

import com.socks.library.KLog;

/**
 * Created by cmlBeliever on 2016/4/26.
 */
public class MusicScannerPresent implements MusicScannerContract.Present {

    private static final String TAG = MusicScannerPresent.class.getSimpleName();

    private MusicScannerContract.View view;
    private boolean isRunning;

    public MusicScannerPresent(MusicScannerContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void scan(Context context) {
        view.startScanAnim();
        Toast.makeText(context, "scann", Toast.LENGTH_LONG).show();

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        KLog.d(TAG, "==============>scann===>" + cursor);

        if (null == cursor) {
            view.updateScannerResult("共扫描到0首歌曲");
            return;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            view.updateScannerResult("共扫描到0首歌曲");
            return;
        }
        //启动扫描
        isRunning = true;
        //TODO 扫描歌曲
        while (isRunning && cursor.moveToNext()) {
            //TODO
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

            KLog.d(TAG, "id:" + id + ",title:" + tilte + ",album:" + album + ",artist:" + artist + ",url:" + url + ",duration:" + duration);
        }
        cursor.close();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
