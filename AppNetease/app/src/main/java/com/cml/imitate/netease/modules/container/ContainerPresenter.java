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
import com.cml.imitate.netease.db.SongListDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.cml.imitate.netease.service.MusicService;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by cmlBeliever on 2016/4/20.
 */
public class ContainerPresenter implements ContainerContract.Presenter {

    private ContainerContract.View homeView;
    private Context context;
    private SongListDbClient songListDbClient;
    private Messenger serviceMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //TODO
            Toast.makeText(context, "回调了", Toast.LENGTH_LONG).show();
            super.handleMessage(msg);
        }
    });
    private Messenger sendMessenger;

    public ContainerPresenter(ContainerContract.View homeView, Context context) {
        this.homeView = homeView;
        this.context = context;
        homeView.setPresenter(this);
        songListDbClient = new SongListDbClient(context);
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessenger = new Messenger(service);

            List<Song> songList = songListDbClient.getSongList();
            KLog.d(AppApplication.TAG, "songlist==>" + songList);
            if (songList.size() == 0) {
                songListDbClient.generateSongList(false);
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
