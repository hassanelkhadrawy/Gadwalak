package com.example.hassan.gadwalak.TaskNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
try {


    if ("DoneTask".equals(action)) {
        Log.i("shuffTest", "Pressed Done");

        Intent Notif_Done = new Intent();

        Notif_Done.setClassName("com.softizone.pro_abdo.studychart", "com.softizone.pro_abdo.studychart.abdoCode.MainActivityInterface");
        Notif_Done.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Notif_Done.putExtra("PositionTask", intent.getExtras().getInt("PositionTask"));
        Notif_Done.putExtra("alarmTaskId", intent.getExtras().getInt("alarmTaskId"));
        Notif_Done.putExtra("pViewId", intent.getExtras().getInt("pViewId"));
        Notif_Done.putExtra("pTableName", intent.getExtras().getString("pTableName"));
        Notif_Done.putExtra("pwhatTaskIsRing", intent.getExtras().getString("pwhatTaskIsRing"));

        Log.i("TableName", "Tname is " + intent.getExtras().getString("pTableName"));
        context.startActivity(Notif_Done);

    }
}catch (Exception e){
    e.getStackTrace();
}
    }
}
