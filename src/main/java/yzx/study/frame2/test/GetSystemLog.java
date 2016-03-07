package yzx.study.frame2.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import yzx.study.frame2.callback.GenericCallback;

public class GetSystemLog {

    private static boolean running = true;

    public static void getLog(final GenericCallback<String> callback) {
        running = true;
        new Thread() {
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String[] clearArray = {"logcat", "-c"};
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        if (!running)
                            break;
                        Runtime.getRuntime().exec(clearArray);
                        callback.callback(str);
                    }
                    bufferedReader.close();
                    process.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static void stop() {
        running = false;
    }

}