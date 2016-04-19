package com.cml.second.app.common.img;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片压缩工具类
 *
 * @author Administrator
 */
public class ImageCompress {

    private static final String TAG = ImageCompress.class.getSimpleName();

    public static final int REQUIRE_SIZE = 1280;
    public static final int QUALITY = 60;

    public static final String CONTENT = "content";
    public static final String FILE = "file";

    /**
     * 1.客户端发送图片时处理规则
     * <p>
     * 1.1.除非用户主动选择发送原图外，所有的图片均需要经过本地先处理才能上传服务器端，处理规则如下
     * <p>
     * a，图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片
     * <p>
     * b，宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280
     * <p>
     * c，宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至1280
     * <p>
     * d，宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片
     *
     * @param imgFile
     * @return
     */
    public static Bitmap compressFromFile(File imgFile) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);

        float outWidth = options.outWidth;
        float outHeight = options.outHeight;

        float outputWidth = 0;
        float outputHeight = 0;

        float sampleRatio = 0f;

        // a，图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片
        if (outHeight <= REQUIRE_SIZE && outHeight <= REQUIRE_SIZE) {
            sampleRatio = 1;
        } else {
            float ratio;
            if (outWidth > outHeight) {
                ratio = outWidth / outHeight;
            } else {
                ratio = outHeight / outWidth;
            }
            //b，宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280
            if (ratio <= 2) {
                sampleRatio = Math.max(outWidth, outHeight) / REQUIRE_SIZE;
            } else if (outWidth > REQUIRE_SIZE && outHeight > REQUIRE_SIZE) {
                // c，宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至1280
                sampleRatio = Math.min(outWidth, outHeight) / REQUIRE_SIZE;
            } else {
                //   d，宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片
            }
        }

        //得到最终输出的高度
        outputWidth = outWidth / sampleRatio;
        outputHeight = outHeight / sampleRatio;

        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) sampleRatio;


        Bitmap originBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);

//        Bitmap targetBitmap = Bitmap.createScaledBitmap(originBitmap, (int) outputWidth, (int) outputHeight, true);
//
//        originBitmap.recycle();

        Log.d(TAG, "输入尺寸：" + outWidth + "," + outHeight + " 转换后尺寸：" + outputWidth + "," + outputHeight + ",缩小比例：" + options.inSampleSize + ",bitmap:" + originBitmap.getWidth() + "," + originBitmap.getHeight());
        return originBitmap;
    }

    /**
     * 图片压缩参数
     *
     * @author Administrator
     */
    public static class CompressOptions {
        public static final int DEFAULT_WIDTH = 400;
        public static final int DEFAULT_HEIGHT = 800;

        public int maxWidth = DEFAULT_WIDTH;
        public int maxHeight = DEFAULT_HEIGHT;
        /**
         * 压缩后图片保存的文件
         */
        public File destFile;
        /**
         * 图片压缩格式,默认为jpg格式
         */
        public CompressFormat imgFormat = CompressFormat.JPEG;

        /**
         * 图片压缩比例 默认为30
         */
        public int quality = 30;

        public Uri uri;
    }

    public Bitmap compressFromUri(Context context,
                                  CompressOptions compressOptions) {

        // uri指向的文件路径
        String filePath = getFilePath(context, compressOptions.uri);

        if (null == filePath) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap temp = BitmapFactory.decodeFile(filePath, options);

        int actualWidth = options.outWidth;
        int actualHeight = options.outHeight;

        int desiredWidth = getResizedDimension(compressOptions.maxWidth,
                compressOptions.maxHeight, actualWidth, actualHeight);
        int desiredHeight = getResizedDimension(compressOptions.maxHeight,
                compressOptions.maxWidth, actualHeight, actualWidth);

        options.inJustDecodeBounds = false;
        options.inSampleSize = findBestSampleSize(actualWidth, actualHeight,
                desiredWidth, desiredHeight);

        Bitmap bitmap = null;

        Bitmap destBitmap = BitmapFactory.decodeFile(filePath, options);

        // If necessary, scale down to the maximal acceptable size.
        if (destBitmap.getWidth() > desiredWidth
                || destBitmap.getHeight() > desiredHeight) {
            bitmap = Bitmap.createScaledBitmap(destBitmap, desiredWidth,
                    desiredHeight, true);
            destBitmap.recycle();
        } else {
            bitmap = destBitmap;
        }

        // compress file if need
        if (null != compressOptions.destFile) {
            compressFile(compressOptions, bitmap);
        }

        return bitmap;
    }

    /**
     * compress file from bitmap with compressOptions
     *
     * @param compressOptions
     * @param bitmap
     */
    private void compressFile(CompressOptions compressOptions, Bitmap bitmap) {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(compressOptions.destFile);
        } catch (FileNotFoundException e) {
            Log.e("ImageCompress", e.getMessage());
        }

        bitmap.compress(compressOptions.imgFormat, compressOptions.quality,
                stream);
    }

    private static int findBestSampleSize(int actualWidth, int actualHeight,
                                          int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary,
                                           int actualPrimary, int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling
        // ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * 获取文件的路径
     *
     * @return
     */
    private String getFilePath(Context context, Uri uri) {

        String filePath = null;

        if (CONTENT.equalsIgnoreCase(uri.getScheme())) {

            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{Images.Media.DATA}, null, null, null);

            if (null == cursor) {
                return null;
            }

            try {
                if (cursor.moveToNext()) {
                    filePath = cursor.getString(cursor
                            .getColumnIndex(Images.Media.DATA));
                }
            } finally {
                cursor.close();
            }
        }

        // 从文件中选择
        if (FILE.equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }

        return filePath;
    }
}
