package com.cml.imitate.netease.modules.container;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.cml.imitate.netease.application.AppApplication;
import com.cml.imitate.netease.db.SongDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.db.contract.SongListContract;
import com.cml.imitate.netease.service.MusicService;
import com.cml.imitate.netease.service.aidl.MusicControlCallback;
import com.cml.imitate.netease.service.aidl.MusicControlService;
import com.cml.imitate.netease.utils.pref.PrefUtil;
import com.cml.imitate.netease.utils.songlist.SonglistHelper;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class ContainerPresenter implements ContainerContract.Presenter {

    private ContainerContract.View homeView;
    private Context context;
    private SonglistHelper songlistHelper;
    private SongDbClient songDbClient;


    //    private Messenger serviceMessenger = new Messenger(new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Toast.makeText(context, "回调了", Toast.LENGTH_LONG).show();
//            switch (msg.what) {
//                case MusicService.ControlCode.PLAY_INDEX:
//                    homeView.setPlayStatus(msg.arg1 == MusicService.ControlCode.OK);
//                    //播放成功
//                    if (msg.arg1 == MusicService.ControlCode.OK) {
//                        //TODO 设置进度条
////                        Song song = SongListUtil.getInstance().getCurrent();
////                        homeView.setPlaybar(song);
//                        //TODO 设置timeer
////                        new ProgressController(song.duration, 24, homeView).start();
//                    }
//                    break;
//                case MusicService.ControlCode.COMPLETED:
//                    homeView.setPlayStatus(false);
////                    Song song = SongListUtil.getInstance().next();
//                    //播放下一首
//                    if (null != song) {
//                        homeView.setPlaybar(song);
//                        play();
//                    }
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    });
    private MusicControlService controlService;

    public ContainerPresenter(final ContainerContract.View homeView, final Context context) {
        this.homeView = homeView;
        this.context = context;
        homeView.setPresenter(this);
        songlistHelper = new SonglistHelper(context);
        songDbClient = new SongDbClient(context);
        //设置上次播放界面
        Observable.create(new Observable.OnSubscribe<Song>() {
            @Override
            public void call(Subscriber<? super Song> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(songlistHelper.getSong(SongListContract.STATUS_PLAY));
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Song>() {
            @Override
            public void call(Song song) {
                if (null != song) {
                    Toast.makeText(context, "显示上次的音乐信息", Toast.LENGTH_LONG).show();
                    homeView.setPlaybar(song);
                }
            }
        });
    }

    private Handler callbackHandler = new Handler();

    private MusicControlCallback.Stub callback = new MusicControlCallback.Stub() {
        @Override
        public void onControlResult(final int type, int result, int songId) throws RemoteException {
            KLog.d("===========》onControlResult" + type + "," + result + "," + songId + ",threadid:" + Thread.currentThread().getId());
            callbackHandler.post(new Runnable() {
                @Override
                public void run() {
                    switch (type) {
                        case MusicService.ControlCode.PREPARED://播放准备完成
                            homeView.setPlaybar(songlistHelper.getSong(SongListContract.STATUS_PLAY));
                            break;
                    }
                }
            });
        }
    };


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            controlService = MusicControlService.Stub.asInterface(service);
            try {
                controlService.register(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            List<Song> songList = songlistHelper.getSongList();
            KLog.d(AppApplication.TAG, "songlist==>" + songList);
            if (songList.size() == 0) {
                songlistHelper.generateSongList(false);
            } else {
                PrefUtil.setCurrentPlayId(String.valueOf(songList.get(2).id));
//                //校验ui信息
//                //TODO 设置信息
//                String url = songList.get(2).url;
//                homeView.setPlaybar(songList.get(2));
//                Message msg = Message.obtain();
//                msg.obj = Uri.fromFile(new File(url));
//                msg.what = MusicService.ControlCode.PLAY;
//                try {
//                    msg.replyTo = serviceMessenger;
//                    sendMessenger.send(msg);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }
//            //TODO
//            SongDbClient client = new SongDbClient(context);
//            List<Song> songs = client.query();
//            String url = songs.get(0).url;
//
//            Message msg = Message.obtain();
//            msg.obj = Uri.fromFile(new File(url));
//            msg.what = MusicService.ControlCode.PLAY;
//            try {
//                msg.replyTo = serviceMessenger;
//                sendMessenger.send(msg);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //TODO
        }
    };

    @Override
    public void subscribe() {
        //TODO 设置以及启动服务
        Intent serviceIntent = new Intent(context, MusicService.class);
        context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unsubscribe() {
        //TODO 关闭服务
        context.unbindService(connection);
    }

    @Override
    public void playMusic(Context context, Uri uri) {
    }

    @Override
    public void bindService() {
    }

    @Override
    public void play() {

        try {
            if (controlService.isPause()) {
                controlService.start(1);
                return;
            }
        } catch (Exception e) {
            KLog.e(e);
        }

        Observable.create(new Observable.OnSubscribe<Song>() {
            @Override
            public void call(Subscriber<? super Song> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(songlistHelper.getSong(SongListContract.STATUS_PLAY));
                    subscriber.onCompleted();
                }
            }
        }).map(new Func1<Song, Song>() {
            @Override
            public Song call(Song song) {
                if (null != song) {
                    return song;
                }
                songlistHelper.generateDefaultPlayingSong();
                return songlistHelper.getSong(SongListContract.STATUS_PLAY);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Song>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e("=====>play error:", e);
            }

            @Override
            public void onNext(Song song) {
                if (null != song) {
                    try {
                        controlService.play(song.id);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void next() {
        int nextId = songlistHelper.next();
        //没有歌曲了
        if (0 == nextId) {
            return;
        }
        play();
    }

    @Override
    public void pause() {
        try {
            controlService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
