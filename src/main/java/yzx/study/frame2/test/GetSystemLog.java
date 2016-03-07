package yzx.study.frame2.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import yzx.study.frame2.callback.GenericCallback;

public class GetSystemLog {

    public static void getLog(final GenericCallback<String> callback) {
        new Thread() {
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String[] clearArray = {"logcat", "-c"};
                    String str = null;
                    while ((str = bufferedReader.readLine()) != null) {
                        Runtime.getRuntime().exec(clearArray);
                        callback.callback(str);
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }

}