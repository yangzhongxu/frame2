package yzx.study.frame2.img.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.io.File;

import yzx.study.frame2.R;

/**
 * 这个类不好用  这只是演示简单用法
 */
public class ImgTool {

    /**
     * Glide的生命周期随context , 不能用application
     */
    private static Context context;

    public static void init(Context context) {
        ImgTool.context = context.getApplicationContext();
    }


    public static void display(String url, ImageView iv, int placeHolder) {
        Glide.with(context).load(url).placeholder(placeHolder).crossFade().into(iv);

        //diskCacheStrategy(DiskCacheStrategy.ALL) 缓存全尺寸的图片
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
        //override(int ,int )
        Glide.with(context).load(url).override(100, 100).into(iv);
        //error 图片
        Glide.with(context).load(url).error(R.mipmap.ic_launcher).into(iv);

        RequestManager rm = Glide.with(context);

        Glide.get(context).clearMemory();

        Glide.get(context).trimMemory(100);
    }

    public static void display(int res, ImageView iv, int placeHolder) {
        Glide.with(context).load(res).placeholder(placeHolder).crossFade().into(iv);
    }

    public static void display(File file, final ImageView iv, int placeHolder) {
        Glide.with(context).load(file).placeholder(placeHolder).crossFade().into(new ImageViewTarget<GlideDrawable>(iv) {
            protected void setResource(GlideDrawable resource) {
            }
        });
    }

}
