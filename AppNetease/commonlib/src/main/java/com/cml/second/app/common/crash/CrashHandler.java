package com.cml.second.app.common.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException handler,when UncaughtException occur,save log and send
 * error.<br>
 * APP崩溃捕获机制，崩溃后会在SD卡上生成异常文件夹crash，文件名格式crash-%s.txt <br>
 * 使用方式：new CrashHandler().init(context);
 *
 * @author user
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = null;

    // system UncaughtException handler
    private UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;
    // to save device info and error info
    private Map<String, String> mInfos = new HashMap<String, String>();

    public CrashHandler() {
    }

    /**
     * 初期化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * when the UncaughtException occur deal with it
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            // exit the process
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * Custom error handling, collect error information to send error reports
     * and other operations are completed in this.
     *
     * @param ex
     * @return true:if handle　the exception;else return　false.
     */

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // use Toast to view error info
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "Sorry, app encountered an error.",
                        Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // collect device parameter information
        collectDeviceInfo(mContext);
        // saved log
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * collect device info
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        // collect app version data
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfos.put("versionName", versionName);
                mInfos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "", e);
            }
        }
    }

    /**
     * save log to file
     *
     * @param ex
     * @return return filename
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        // write the crash log
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);

        return writeLogIntoFile(sb);
    }

    /**
     * save file as "com-crash-{time}-{timestamp}.log" into /sdcard/crash/
     *
     * @param sb log StringBuffer
     * @return return filename　saved failed
     */
    private String writeLogIntoFile(StringBuffer sb) {

        CharSequence time = DateFormat.format("yyyy-MM-dd-kk-mm-ss", new Date());

        String fileName = String.format("crash-%s.txt", time);
        try {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // write
                String path = Environment.getExternalStorageDirectory().getPath() + "/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (FileNotFoundException exception) {
            // file save sdcard failed
            try {
                // try save file into phone
                FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_APPEND);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                // failed to save into phone, give up
                Log.e(TAG, "", e);
                return null;
            } catch (IOException e) {
                // failed to save into phone, give up
                Log.e(TAG, "", e);
                return null;
            }
        } catch (IOException exception) {
            // failed to save into phone， give up
            Log.e(TAG, "", exception);
            return null;
        }
    }
}
