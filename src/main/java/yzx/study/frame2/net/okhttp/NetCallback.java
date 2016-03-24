package yzx.study.frame2.net.okhttp;

public interface NetCallback {

    void onSuccess(String result);

    void onError(int httpCode);

}
