package yzx.study.frame2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import yzx.study.frame2.errorCollect.ECActivity;
import yzx.study.frame2.errorCollect.ECManager;
import yzx.study.frame2.test.GetSystemLog;
import yzx.study.frame2.test.LocalSocketServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ECManager.install(this);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainActivity.this, ECActivity.class));
            }
        }, 1500);


//        ImgTool.init(this);
//        ImageView iv = (ImageView) findViewById(R.id.iv);
//        ImgTool.display(R.mipmap.ic_launcher, iv, 0);


//  final TextView tv = (TextView) findViewById(R.id.tv);

// Toast.makeText(MainActivity.this, JNI.test(), Toast.LENGTH_SHORT).show();

//        LocalSocketServer.start(new GenericCallback<String>() {
//            public Object callback(final String s) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                return "ok";
//            }
//        }, new Runnable() {
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "error!!!!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });


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
        LocalSocketServer.stop();
        System.exit(0);
    }

}
