package com.cml.imitate.netease.modules.local;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.cml.imitate.netease.db.SongDbClient;
import com.cml.imitate.netease.db.bean.Song;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by cmlBeliever on 2016/4/26.
 */
public class MusicScannerPresent implements MusicScannerContract.Present {

    private static final String TAG = MusicScannerPresent.class.getSimpleName();

    private MusicScannerContract.View view;
    private boolean isRunning;
    private BehaviorSubject<String> scanResultSubject = BehaviorSubject.create();
    private SongDbClient dbClient;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            view.updateScannerText(msg.obj.toString());
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };

    public MusicScannerPresent(final MusicScannerContract.View view) {
        this.view = view;
        dbClient = new SongDbClient(view.getContext());
        view.setPresenter(this);
        //压力太大丢弃后面的部分
        scanResultSubject.onBackpressureDrop().observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.updateScannerText("error:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.updateScannerText(s);
            }
        });
    }

    @Override
    public void scan(final Context context) {
        view.startScanAnim();
        //启动扫描
        isRunning = true;

        Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                    subscriber.onNext(cursor);
                    subscriber.onCompleted();
                }
            }
        }).map(new Func1<Cursor, List<Song>>() {
            @Override
            public List<Song> call(Cursor cursor) {
                List<Song> songs = new ArrayList<Song>();
                if (null != cursor && cursor.getCount() > 0) {
                    // 扫描歌曲
                    while (isRunning && cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                        String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                        String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                        String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                        Song song = new Song(id, tilte, album, artist, url, url, duration);
                        songs.add(song);

                        //插入db信息
                        dbClient.replaceSong(song);
                        //通知界面显示
                        scanResultSubject.onNext(song.toString());

                        KLog.d(TAG, song);
                    }
                    cursor.close();
                }
                return songs;
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Song>>() {
            @Override
            public void onCompleted() {
                view.stopScanAnim();
            }

            @Override
            public void onError(Throwable e) {
                view.stopScanAnim();
                KLog.e(TAG, e);
            }

            @Override
            public void onNext(List<Song> songs) {
                //通知界面显示
                scanResultSubject.onNext("共扫描到：" + Thread.currentThread().getId() + "," + songs.size());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
