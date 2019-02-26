package com.example.hassan.gadwalak.TaskNotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.hassan.gadwalak.R;

import java.text.DateFormat;
import java.util.Calendar;

public class Task_Details extends AppCompatActivity {

    private Button btn_Date;
    private TextView showDate;
    private EditText taskName;
    private EditText description;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    Intent myintent;


    DateFormat formatDate = DateFormat.getDateInstance();
    DateFormat formatTime = DateFormat.getTimeInstance();


    Calendar dateTime = Calendar.getInstance();

    String hour_string = "";
    String minute_string = "";
    String night = "" ;

    int _id = (int) System.currentTimeMillis();
    int count = 0 ;
    int viewId = 0 ;
    String tableName = "" ;


    boolean timeShowone = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__details);

        btn_Date = (Button) findViewById(R.id.date);

        showDate =  findViewById(R.id.show_Datetime);

        taskName =  findViewById(R.id.taskName);

        description =  findViewById(R.id.description);
taskName.setText(Task.getSubname());
        // for update or create new task
        Intent DataItem = getIntent();
        String UpadteItemORno = DataItem.getStringExtra("UpdateTask");
        if ("yesUpdate".equals(UpadteItemORno)) {

            taskName.setText(DataItem.getStringExtra("TaskName"));
            description.setText(DataItem.getStringExtra("Description"));
            showDate.setText(DataItem.getStringExtra("TaskDate") + "  " + DataItem.getStringExtra("TaskTime"));

            _id = DataItem.getIntExtra("alarmTaskID", 0);
///////////////////////////////////////////
            count = DataItem.getIntExtra("currentCount" , 0) ;
            viewId = DataItem.getIntExtra("viewId",0);
            tableName = DataItem.getStringExtra("tableName");
////////////////////////////////////////////////
        }else{

            count = DataItem.getIntExtra("currentCount" , 0) ;
            viewId = DataItem.getIntExtra("viewId",0);
            tableName = DataItem.getStringExtra("tableName");
            Log.i("currentCount", "Details : is " + count);
        }

        btn_Date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                myintent = new Intent(Task_Details.this, AlarmService.class);

                updateDate();

                timeShowone = true ;

            }
        });


    }


    public void updateDate() {

        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();

    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            dateTime.set(Calendar.YEAR, i);

            dateTime.set(Calendar.MONTH, i1);

            dateTime.set(Calendar.DAY_OF_MONTH, i2);


            if(timeShowone) {

                updateTime();

                timeShowone = false ;
            }

        }
    };


    public void updateTime() {

        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();

    }


    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            dateTime.set(Calendar.HOUR_OF_DAY, i);

            dateTime.set(Calendar.MINUTE, i1);

            dateTime.set(Calendar.SECOND, 0);

            dateTime.set(Calendar.MILLISECOND, 0);
            night = ""+i ;

            // send data to notification
            notificationData();

            pendingIntent = PendingIntent.getBroadcast(Task_Details.this, 0
                    , myintent, 0);


            updateDateTimeLabel();

        }
    };

    private void notificationData() {

        Log.i("currentCount", "notificationData: is " + count);
        myintent.putExtra("currentCount" , count);
        myintent.putExtra("notificationId" , _id);
        myintent.putExtra("viewId" , viewId);
        myintent.putExtra("tableName" , tableName);
        myintent.putExtra("nTaskName", taskName.getText().toString());
        myintent.putExtra("nDescription", description.getText().toString());

    }


    public void updateDateTimeLabel() {

        showDate.setText("" + formatDate.format(dateTime.getTime()) + "  " + formatTime.format(dateTime.getTime()));

        long currentTimeMilli = dateTime.getTimeInMillis() - System.currentTimeMillis();

        int hours = (int) currentTimeMilli / (1000 * 60 * 60);
        int minutes = (int) (currentTimeMilli / (1000 * 60)) % 60;


        Toast.makeText(this, R.string.AlarmTasksetfor + hours +  R.string.hours
                + minutes + R.string.minutes, Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void returnTaskDetails(View view) {

        if (alarmManager != null) {

            if (Build.VERSION.SDK_INT >= 19) {

                // high Api level 20
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,dateTime.getTimeInMillis(),pendingIntent);

            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, dateTime.getTimeInMillis(), pendingIntent);
                // low Api level 20 or equal
//                alarmManager.setWindow(AlarmManager.RTC_WAKEUP,
//                        dateTime.getTimeInMillis(),
//                        1,
//                        pendingIntent);
            }


//            alarmManager.set(AlarmManager.RTC_WAKEUP, dateTime.getTimeInMillis(), pendingIntent);


            Intent returnIntent = new Intent();
            returnIntent.putExtra("taskname", taskName.getText().toString());
            returnIntent.putExtra("description", description.getText().toString());
            returnIntent.putExtra("date", "" + formatDate.format(dateTime.getTime()));
            returnIntent.putExtra("time", "" + formatTime.format(dateTime.getTime()));
            returnIntent.putExtra("night" , night);
            returnIntent.putExtra("alarmTaskId", _id);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        }else{

            Toast.makeText(this, R.string.selectDandT, Toast.LENGTH_SHORT).show();
        }



    }


    public void returnBack(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
