package yzx.study.frame2.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class BitmapTool {

    public static Bitmap getBitmapBelowSize(File file, long maxSize) {
        int simpleSize = 1;
        while (getJustBitmapSize(file, simpleSize) > maxSize)
            simpleSize++;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = simpleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }


    public static long getJustBitmapSize(File file, int inSimpleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = inSimpleSize;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return options.outWidth * options.outHeight * 4;
    }


    public static Bitmap getBitmapBelowSize(Context context, int resId, long maxSize) {
        int simpleSize = 1;
        while (getJustBitmapSize(context, resId, simpleSize) > maxSize)
            simpleSize++;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = simpleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public static long getJustBitmapSize(Context context, int resId, int inSimpleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = inSimpleSize;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        return options.outWidth * options.outHeight * 4;
    }

}
