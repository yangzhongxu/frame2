package yzx.study.frame2.img.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImgTool {

    private static Context context;

    public static void init(Context context) {
        ImgTool.context = context.getApplicationContext();
    }


    public static void display(String url, ImageView iv, int placeHolder) {
        Glide.with(context).load(url).placeholder(placeHolder).crossFade().into(iv);
    }

    public static void display(int res, ImageView iv, int placeHolder) {
        Glide.with(context).load(res).placeholder(placeHolder).crossFade().into(iv);
    }

    public static void display(File file, ImageView iv, int placeHolder) {
        Glide.with(context).load(file).placeholder(placeHolder).crossFade().into(iv);
    }

}
