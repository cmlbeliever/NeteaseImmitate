package com.cml.imitate.netease.modules.container;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import com.cml.imitate.netease.application.AppApplication;
import com.cml.imitate.netease.db.SongDbClient;
import com.cml.imitate.netease.db.SongListDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.service.MusicService;
import com.cml.imitate.netease.utils.pref.PrefUtil;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class ContainerPresenter implements ContainerContract.Presenter {

    private ContainerContract.View homeView;
    private Context context;
    private SongListDbClient songListDbClient;
    private SongDbClient songDbClient;
    private Messenger serviceMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //TODO
            Toast.makeText(context, "回调了", Toast.LENGTH_LONG).show();
            super.handleMessage(msg);
        }
    });
    private Messenger sendMessenger;

    public ContainerPresenter(final ContainerContract.View homeView, final Context context) {
        this.homeView = homeView;
        this.context = context;
        homeView.setPresenter(this);
        songListDbClient = new SongListDbClient(context);
        songDbClient = new SongDbClient(context);
        //设置上次播放界面
        if (PrefUtil.getCurrentPlayId() != null) {
            Observable.create(new Observable.OnSubscribe<Song>() {
                @Override
                public void call(Subscriber<? super Song> subscriber) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(songDbClient.query(PrefUtil.getCurrentPlayId()));
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
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessenger = new Messenger(service);

            List<Song> songList = songListDbClient.getSongList();
            KLog.d(AppApplication.TAG, "songlist==>" + songList);
            if (songList.size() == 0) {
                songListDbClient.generateSongList(false);
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
    }

    @Override
    public void next() {

    }

    @Override
    public void pause() {
    }
}
