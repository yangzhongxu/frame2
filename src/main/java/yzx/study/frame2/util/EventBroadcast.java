package yzx.study.frame2.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;

public class EventBroadcast {

    private static final String Action_Header = "event.action._";
    private static Context context;


    public static void init(Context context) {
        EventBroadcast.context = context.getApplicationContext();
    }


    //
    //


    public static void register(int id, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action_Header + id);
        filter.setPriority(1000);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }


    public static void unRegister(BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }


    //
    //


    public static void publishEvent(int id, CharSequence data) {
        Intent intent = new Intent(Action_Header + id);
        intent.putExtra("data", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void publishEvent(int id, Serializable data) {
        Intent intent = new Intent(Action_Header + id);
        intent.putExtra("data", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void publishEvent(int id, int data) {
        Intent intent = new Intent(Action_Header + id);
        intent.putExtra("data", data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void publishEvent(int id) {
        Intent intent = new Intent(Action_Header + id);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
