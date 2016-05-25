package com.cml.imitate.netease.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.cml.imitate.netease.db.SongDbClient;
import com.cml.imitate.netease.db.SongListDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.service.aidl.MusicControlCallback;
import com.cml.imitate.netease.service.aidl.MusicControlService;
import com.socks.library.KLog;

public class MusicBinder extends MusicControlService.Stub implements MusicPlayerClient.MusicCallback {

    private RemoteCallbackList<MusicControlCallback> callbackList = new RemoteCallbackList<>();
    private SongListDbClient songListDbClient;
    private SongDbClient songDbClient;
    private MusicPlayerClient musicPlayerClient;//音乐播放控制器


    public MusicBinder(Context context) {
        super();
        songDbClient = new SongDbClient(context);
        songListDbClient = new SongListDbClient(context);
        musicPlayerClient = new MusicPlayerClient(context, this);
    }

    @Override
    public void play(int songId) throws RemoteException {

        Song song = songDbClient.query(songId);

        if (null != song) {
            musicPlayerClient.play(Uri.parse(song.url), true);
        } else {
            //播放失败，回调信息
            callback(MusicService.ControlCode.PLAY, MusicService.ControlCode.FAIL, songId);
        }
    }

    @Override
    public void pause() throws RemoteException {
        //TODO
//            case ControlCode.PAUSE://音乐暂停
//            playMessenger = msg.replyTo;
//            musicPlayerClient.pause();
//            break;
    }

    @Override
    public void start(int songId) throws RemoteException {
        //TODO
    }

    private void callback(int type, int result, int songId) {
        try {
            int N = callbackList.beginBroadcast();
            for (int i = 0; i < N; i++) {
                callbackList.getBroadcastItem(i).onControlResult(type, result, songId);
            }
        } catch (RemoteException e) {
            KLog.e(e);
        }
        callbackList.finishBroadcast();
    }

    @Override
    public void register(MusicControlCallback callback) throws RemoteException {
        callbackList.register(callback);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        callback(MusicService.ControlCode.COMPLETED, MusicService.ControlCode.OK, 0);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        callback(MusicService.ControlCode.ERROR, MusicService.ControlCode.FAIL, 0);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        callback(MusicService.ControlCode.PREPARED, MusicService.ControlCode.OK, 0);
    }
}