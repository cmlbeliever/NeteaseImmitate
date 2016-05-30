// MusicControlService.aidl
package com.cml.imitate.netease.service.aidl;
import com.cml.imitate.netease.service.aidl.MusicControlCallback;

interface MusicControlService {

    void play(int songId);

    void pause();

    void start(int songId);

    void register(MusicControlCallback callback);

    boolean isPlaying();
}
