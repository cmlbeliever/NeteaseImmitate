package com.cml.imitate.netease.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.socks.library.KLog;

import java.io.IOException;

/**
 * Created by cmlBeliever on 2016/5/13.
 */
public class MusicPlayerClient {
    private Context context;
    private MediaPlayer player;
    private MusicCallback callback;

    public MusicPlayerClient(Context context, MusicCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean isPause() {
        return null != player && !player.isPlaying();
    }

    public void play(Uri uri, boolean isOrigin) {
        if (null != player) {
            player.release();
        }
        //重新播放
        player = new MediaPlayer();
        try {
            player.setDataSource(context, uri);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    KLog.d("====>onPrepared:" + Thread.currentThread().getId());
                    if (null != callback) {
                        callback.onPrepared(mp);
                    }
                }
            });
            player.prepareAsync();
            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (null != callback) {
                        callback.onError(mp, what, extra);
                    }
                    return false;
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    player = null;
                    if (null != callback) {
                        callback.onCompletion(mp);
                    }
                }
            });
        } catch (IOException e) {
            KLog.e(e);
        }
    }

    public void pause() {
        if (null != player && player.isPlaying()) {
            player.pause();
        }
    }

    public void start() {
        if (null != player && !player.isPlaying()) {
            player.start();
        }
    }

    public static interface MusicCallback {
        void onCompletion(MediaPlayer mp);

        boolean onError(MediaPlayer mp, int what, int extra);

        void onPrepared(MediaPlayer mp);
    }
}
