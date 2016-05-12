package com.cml.imitate.netease.db;

import android.content.Context;

/**
 * Created by cmlBeliever on 2016/5/12.
 */
public class DataBaseClient {
    Context context;
    SQLiteConnectionHelper helper;

    public DataBaseClient(Context context) {
        this.context = context;
        helper = SQLiteConnectionHelper.getInstance(context);
    }

    public void destory() {
        helper.getWritableDatabase().close();
        helper.getReadableDatabase().close();

    }
}
