package com.cml.imitate.netease.utils.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cml.imitate.netease.application.AppApplication;

public class PrefUtil {

    private static Context context = AppApplication.getContext();

    interface Keys {
        String KEY_IS_FRONT_SERVICE = "pref.key.isfrontservice";
        String KEY_PLAY_MUSIC_ID = "pref.key.music.play.id";
    }

    public static void setCurrentPlayId(String id) {
        setPref(Keys.KEY_PLAY_MUSIC_ID, id);
    }

    public static String getCurrentPlayId() {
        return getPref(Keys.KEY_PLAY_MUSIC_ID);
    }

    public static boolean getIsFrontService() {
        return getBooleanPref(Keys.KEY_IS_FRONT_SERVICE);
    }

    public static void setIsFrontService(boolean show) {
        setPref(Keys.KEY_IS_FRONT_SERVICE, show);
    }

    public static final void setPref(String prefName, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).commit();
    }

    public static final void setPref(String prefName, String key, Integer value) {
        SharedPreferences pref = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        pref.edit().putInt(key, value).commit();
    }

    public static final void setPref(String key, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putBoolean(key, value).commit();
    }

    public static final void setPref(String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putString(key, value).commit();
    }

    public static final void setPref(String prefName, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).commit();
    }

    public static final String getPref(String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, null);
    }

    public static final String getPref(String prefName, String key) {
        SharedPreferences pref = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        return pref.getString(key, null);
    }

    public static final Integer getIntPref(String prefName, String key) {
        SharedPreferences pref = context.getSharedPreferences(
                prefName, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public static final boolean getBooleanPref(String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, false);
    }


}
