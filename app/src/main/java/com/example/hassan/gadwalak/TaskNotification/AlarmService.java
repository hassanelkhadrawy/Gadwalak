package com.example.hassan.gadwalak.TaskNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.hassan.gadwalak.R;

public class AlarmService extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

            Toast.makeText(context, R.string.Receivealarm, Toast.LENGTH_SHORT).show();

            Intent service_Ring = new Intent(context, RingtonePlayingService.class);

            Log.i("currentCount", "onReceive: is " + intent.getExtras().getInt("currentCount"));
            service_Ring.putExtra("currentCount", intent.getExtras().getInt("currentCount"));
            service_Ring.putExtra("notificationId", intent.getExtras().getInt("notificationId"));
            service_Ring.putExtra("viewId", intent.getExtras().getInt("viewId"));
            service_Ring.putExtra("tableName", intent.getExtras().getString("tableName"));
            service_Ring.putExtra("nTaskName", intent.getExtras().getString("nTaskName"));
            service_Ring.putExtra("nDescription", intent.getExtras().getString("nDescription"));

            context.startService(service_Ring);

    }
}
