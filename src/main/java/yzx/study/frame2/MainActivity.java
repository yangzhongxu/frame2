package yzx.study.frame2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import yzx.study.frame2.callback.GenericCallback;
import yzx.study.frame2.test.GetSystemLog;
import yzx.study.frame2.test.LocalSocketServer;
import yzx.study.frame2.util.EventBroadcast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);

        LocalSocketServer.start(new GenericCallback<String>() {
            public Object callback(final String s) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                return "ok";
            }
        }, new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "error!!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                File f = new File(Environment.getExternalStorageDirectory(), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.txt");
//                try {
//                    FileOutputStream out = new FileOutputStream(f);
//                    out.write("aa".getBytes());
//                    out.flush();
//                    out.close();
//                } catch (IOException e) {
//                }
//            }
//        });

//        GetSystemLog.getLog(new GenericCallback<String>() {
//            public void callback(final String s) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        tv.setText(s);
//                    }
//                });
//            }
//        });

//        EventBroadcast.init(this);
//        EventBroadcast.register(1, receiver);
//        EventBroadcast.publishEvent(1);
//        EventBroadcast.unRegister(receiver);
    }

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(getApplicationContext(), "fuck", Toast.LENGTH_SHORT).show();
//        }
//    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetSystemLog.stop();
        System.exit(0);
        LocalSocketServer.stop();
    }

}
