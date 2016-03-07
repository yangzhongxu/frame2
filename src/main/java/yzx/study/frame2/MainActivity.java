package yzx.study.frame2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import yzx.study.frame2.util.EventBroadcast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
