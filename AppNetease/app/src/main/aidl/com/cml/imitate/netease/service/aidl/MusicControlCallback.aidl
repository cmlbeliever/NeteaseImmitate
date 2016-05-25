// MusicControlCallback.aidl
package com.cml.imitate.netease.service.aidl;

// Declare any non-default types here with import statements

interface MusicControlCallback {
    void onControlResult(int type,int result,int songId);
}
