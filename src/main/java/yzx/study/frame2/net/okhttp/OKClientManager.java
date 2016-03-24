package yzx.study.frame2.net.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OKClientManager {

    private static final OkHttpClient client = new OkHttpClient();


    static {
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(8, TimeUnit.SECONDS);
        client.setWriteTimeout(8, TimeUnit.SECONDS);
    }


    /****************************************************************************************************************
     * 停止请求
     *
     * @param url 网址
     */
    public static void cancelRequest(String url) {
        client.cancel(url);
    }


    /****************************************************************************************************************
     * get请求
     *
     * @param url      网址
     * @param callback 回调
     */
    public static void get(String url, final NetCallback callback) {
        Request request = new Request.Builder().url(url).tag(url).build();
        startRequest(request, callback);
    }


    /****************************************************************************************************************
     * post请求
     *
     * @param url      网址
     * @param params   参数
     * @param callback 回调
     */
    public static void post(String url, @Nullable Map<String, String> params, final NetCallback callback) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null)
            for (Map.Entry<String, String> en : params.entrySet())
                builder.add(en.getKey(), en.getValue());
        Request request = new Request.Builder().url(url).tag(url).post(builder.build()).build();
        startRequest(request, callback);
    }


    /****************************************************************************************************************
     * post请求 , 包含上传文件
     *
     * @param url      网址
     * @param files    文件集合
     * @param params   参数
     * @param callback 回调
     */
    public static void post(String url, @NonNull List<File> files, Map<String, String> params, final NetCallback callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (params != null && !params.isEmpty())
            for (Map.Entry<String, String> en : params.entrySet())
                builder.addFormDataPart(en.getKey(), en.getValue());
        for (File file : files)
            builder.addFormDataPart("file", null, RequestBody.create(MediaType.parse("image/png"), file));
        startRequest(new Request.Builder().url(url).tag(url).post(builder.build()).build(), callback);
    }


    //
    //


    private static void startRequest(Request request, final NetCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callbackOnUIThread_error(callback, response.code());
                } else
                    callbackOnUIThread_success(callback, response.body().string());
            }

            public void onFailure(Request request, IOException e) {
                callbackOnUIThread_error(callback, 0);
            }
        });
    }


    private static void callbackOnUIThread_success(final NetCallback callback, final String result) {
        if (callback == null)
            return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                callback.onSuccess(result);
            }
        });
    }


    private static void callbackOnUIThread_error(final NetCallback callback, final int code) {
        if (callback == null)
            return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                callback.onError(code);
            }
        });
    }

}
