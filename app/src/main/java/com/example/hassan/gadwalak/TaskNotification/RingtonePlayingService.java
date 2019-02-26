package com.example.hassan.gadwalak.TaskNotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.hassan.gadwalak.R;


public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer ;
    static int notificationId = 1 ;

    int NotificationButtonRequest = (int) System.currentTimeMillis();


    Vibrator vibrator ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        int currentCount = intent.getExtras().getInt("currentCount");
        int NotificationRequest = intent.getExtras().getInt("notificationId");
        int ViewId = intent.getExtras().getInt("viewId");
        String TableName = intent.getExtras().getString("tableName");
        String nTaskTitle = intent.getExtras().getString("nTaskName");
        String nTaskContent = intent.getExtras().getString("nDescription");

        // release the Ringtone
        releaseMediaPlayer();


        //when click notification , where it go !!
        Intent intentActivity = new Intent(this.getApplicationContext() , MainActivity.class);
        intentActivity.putExtra("currentTask" , currentCount);
        intentActivity.putExtra("ViewId" , ViewId);
        intentActivity.putExtra("TableName" , TableName);


        intentActivity.putExtra("whatTaskIsRing" , "taskRing");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this , NotificationRequest , intentActivity , 0);



        // when click done button notification
        Intent intentButton = new Intent(this.getApplicationContext(),MainActivity.class);
        intentButton.putExtra("PositionTask" , currentCount);
        intentButton.putExtra("pViewId" , ViewId);
        intentButton.putExtra("pTableName" , TableName);


        intentButton.putExtra("alarmTaskId" , NotificationRequest);
        intentButton.putExtra("notificationId" , notificationId);

        intentButton.putExtra("pwhatTaskIsRing" , "taskRingClickButton");

        intentButton.setAction("DoneTask");

        PendingIntent pendingIntentButton = PendingIntent.getActivity(this , 12345 , intentButton ,  PendingIntent.FLAG_UPDATE_CURRENT);


        ///////////////////// if exist a lot of buttons in notification //////////////////////

//        Intent intentButton = new Intent(this.getApplicationContext(),NotificationReceiver.class);
//        intentButton.putExtra("PositionTask" , currentCount);
//        intentButton.putExtra("pViewId" , ViewId);
//        intentButton.putExtra("pTableName" , TableName);
//        Log.i("pcurrentCount", "count is " + currentCount + ", T : "+TableName +
//                ", Id = " +ViewId +" , Notfi = "+NotificationRequest);
//
//        intentButton.putExtra("alarmTaskId" , NotificationRequest);
//
//        intentButton.putExtra("pwhatTaskIsRing" , "taskRingClickButton");
//
//        intentButton.setAction("DoneTask");
//
//        PendingIntent pendingIntentButton = PendingIntent.getBroadcast(this , 12345 , intentButton ,  PendingIntent.FLAG_UPDATE_CURRENT);

////////////////////////////////////////////////////////////////////////////////////////////////

        Notification mBuilder = new Notification.Builder(this)
                .setContentTitle(nTaskTitle)
                .setContentText(nTaskContent)
                .setSmallIcon(R.drawable.ic_stat_name)
//                .setLargeIcon(aBitmap)
//                .setStyle(new Notification.BigTextStyle()
//                        .bigText(nTaskContent))
                .setAutoCancel(true)
                .addAction(R.drawable.ic_done_black_24dp , "Done" , pendingIntentButton)
                .setContentIntent(pendingIntent)
                .build();

//        Log.i("notificationId", "notificationId =  "+notificationId);
        // set up the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder);
        notificationId ++;



        mediaPlayer = MediaPlayer.create(this , R.raw.alarm_2);

        mediaPlayer.start();

        customVibratePatternNoRepeat();

        return START_NOT_STICKY ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }



    private void releaseMediaPlayer() {
        // If the media player is not null
        if (mediaPlayer != null) {

            mediaPlayer.release();

            mediaPlayer = null;

        }
    }


    private void customVibratePatternNoRepeat() {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(2000,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            vibrator.vibrate(2000);
        }
    }

}
