package com.example.hassan.gadwalak.TaskNotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.ShowTables.DB;
import com.example.hassan.gadwalak.ShowTables.fixedtable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int count = 1;
    int doneCount = 1;
    int orientationValue;
    private int viewID;
    int notificationId = 0;
    int alarmId_Del = -1;
    int positionItem_Del = -1;
    String[] alarmDataItem;
    boolean disableWhenBack = false;
    boolean checkisClicked = false;
    boolean whatListViewUsed = false;

    ArrayList<Task> Tasks;
    ArrayList<Task> TasksDone;

    ListView listView;
    ListView doneListview;
    TextView tasksView;
    TextView tasksDoneView;
    Button btnAddTask;

    Task updateDataTask;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor doneEditor;


    private MenuItem menuItemAdd;
    private MenuItem menuItemDelete;
    private MenuItem menuItemDone;
    private MenuItem menuItemUndo;

    private static String MY_PREFS_NAME = "MyPrefsFileItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle getdata = getIntent().getExtras();



        String[] splitter = getdata.getString("data","Tasks").split("\\n");


        getSupportActionBar().setTitle(splitter[0]);
        Task.setSubname(splitter[0]);

        if (getdata.getBoolean("back", true)) {

            if ("taskRing".equals(getdata.getString("whatTaskIsRing"))) {


                viewID = getdata.getInt("ViewId", 0);
                MY_PREFS_NAME = getdata.getString("TableName", "no table name");

                Log.i("checkNoti", "yes , you click on the Notification : " +
                        "Table Name is " + MY_PREFS_NAME + " , Id =  " + viewID);

//////////////////////////////////////////////////
            } else if ("taskRingClickButton".equals(getdata.getString("pwhatTaskIsRing"))) {

                viewID = getdata.getInt("pViewId", 0);
                MY_PREFS_NAME = getdata.getString("pTableName", "no table name");

                alarmId_Del = getdata.getInt("alarmTaskId", 0);
                positionItem_Del = getdata.getInt("PositionTask", 0);
                notificationId = getdata.getInt("notificationId", 0);

                Log.i("checkNotiButton", "yes , you click on the Notification : " +
                        "Table Name is " + MY_PREFS_NAME + " , Id =  " + viewID + " , count = " + positionItem_Del);

                checkisClicked = true;

/////////////////////////////////////////////////////////
            } else {

                viewID = getdata.getInt("CellID");
                MY_PREFS_NAME = getdata.getString("tableName");

                Log.i("else", "nothing");


            }

        }

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        doneEditor = getSharedPreferences(MY_PREFS_NAME + "done", MODE_PRIVATE).edit();

        listView = (ListView) findViewById(R.id.listview);
        doneListview = (ListView) findViewById(R.id.doneListview);
        tasksView = (TextView) findViewById(R.id.tasks_view);
        tasksDoneView = (TextView) findViewById(R.id.tasksdone_view);
        btnAddTask = (Button) findViewById(R.id.add_task);


        Tasks = new ArrayList<Task>();
        TasksDone = new ArrayList<Task>();

        alarmDataItem = new String[]
                {"count", "taskName", "Description", "taskDate", "taskTime", "alarmTask_Id", "isNotEmpty"};

        // read all tasks from shared to show it in listview
        readAllDataShared();
        // read all done tasks from shared to show it in listview
        readAllDoneDataShared();

        // i want first read shared to use Tasks.get
        if (checkisClicked) {

            updateDataTask = Tasks.get(positionItem_Del - 1);

            Log.i("doneButton", "id pos = " + (positionItem_Del - 1));

            ClickToDoneTask();

            checkisClicked = false;

            // Notification cancel when click done button
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(notificationId);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                updateDataTask = Tasks.get(i);

//                Log.i("itemid", "onItemClick: " + i);

                Intent dataItem = new Intent(MainActivity.this, Task_Details.class);
                dataItem.putExtra("currentCount", updateDataTask.getNumberOfTask());
                dataItem.putExtra("TaskName", updateDataTask.getTaskName());
                dataItem.putExtra("Description", updateDataTask.getTaskDetails());
                dataItem.putExtra("TaskDate", updateDataTask.getDate());
                dataItem.putExtra("TaskTime", updateDataTask.getTime());
                dataItem.putExtra("alarmTaskID", updateDataTask.getAlarmTask_Id());
                dataItem.putExtra("tableName", "" + MY_PREFS_NAME);
                dataItem.putExtra("viewId", viewID);
                dataItem.putExtra("UpdateTask", "yesUpdate");

                startActivityForResult(dataItem, 2);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                menuItemDelete.setVisible(true);
                menuItemDone.setVisible(true);
                menuItemUndo.setVisible(false);
                Log.i("deleteTask", "you want delete task !" + view.getId());

                //to delete or done Task
                updateDataTask = Tasks.get(i);

                PrepareVarToDELLorDONE();

                whatListViewUsed = false;

                return true;
            }
        });


        doneListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                menuItemDelete.setVisible(true);
                menuItemUndo.setVisible(true);
                menuItemDone.setVisible(false);
                Log.i("deleteTask", "you want delete task !");

                //to delete or done Task
                updateDataTask = TasksDone.get(i);

                PrepareVarToDELLorDONE();

                whatListViewUsed = true;

                return true;
            }
        });

        //check orientation to hidden add task
        orientationValue = getResources().getConfiguration().orientation;

        if (orientationValue == Configuration.ORIENTATION_PORTRAIT) {

            btnAddTask.setVisibility(View.VISIBLE);

        }

        if (orientationValue == Configuration.ORIENTATION_LANDSCAPE) {

            btnAddTask.setVisibility(View.GONE);

        }

//        // what task is ring in listview
//        selectItemAfterRing();

    }


    @Override
    public void onBackPressed() {

        if (disableWhenBack) {

            menuItemAdd.setVisible(true);
            menuItemDelete.setVisible(false);
            menuItemDone.setVisible(false);
            menuItemUndo.setVisible(false);

            disableWhenBack = false;

        } else {

            Intent i = new Intent(MainActivity.this, fixedtable.class);

            DB db = new DB(this, MY_PREFS_NAME);

            if (db.testTableExistance()) {
                i.putExtra("table_name", MY_PREFS_NAME);
                i.putExtra("tableIsFound", true);
                i.putExtra("existTable", true);
                db.createUserTables();
                startActivity(i);
            }

            super.onBackPressed();
            finish();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);

        menuItemAdd = menu.findItem(R.id.add);
        menuItemDelete = menu.findItem(R.id.delete);
        menuItemDone = menu.findItem(R.id.done);
        menuItemUndo = menu.findItem(R.id.undo);

        menuItemAdd.setVisible(true);
        menuItemDelete.setVisible(false);
        menuItemDone.setVisible(false);
        menuItemUndo.setVisible(false);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add:

                Intent intent = new Intent(this, Task_Details.class);

                intent.putExtra("currentCount", count);
                intent.putExtra("tableName", "" + MY_PREFS_NAME);
                intent.putExtra("viewId", viewID);
                Log.i("currentCount", "addTask: is " + count);

                startActivityForResult(intent, 1);

                break;

            case R.id.delete:

                // delete tasks to do it
                if (!whatListViewUsed) {

                    // delete pending intent
                    Toast.makeText(this, R.string.Delete, Toast.LENGTH_SHORT).show();
                    deleteAlarmTask(alarmId_Del);

                    // delete from Arraylist
                    Tasks.remove(positionItem_Del - 1);
//                addItem();

                    // delete from sharedPreference
                    RemoveValueFromShared("" + viewID + positionItem_Del, "");

                    // update sharedPreference
                    updateDataShared(positionItem_Del + 1, "");

                    // invisible menu item except add button
                    menuItemAdd.setVisible(true);
                    menuItemDelete.setVisible(false);
                    menuItemDone.setVisible(false);

                } else {
                    //delete done task

//                    Log.i("DelDone", "Yaaaah");

                    // delete from Arraylist
                    TasksDone.remove(positionItem_Del - 1);

                    // delete from sharedPreference
                    RemoveValueFromShared("" + viewID + positionItem_Del, "done");

                    // update sharedPreference
                    updateDataShared(positionItem_Del + 1, "done");

                    // invisible menu item except add button
                    menuItemAdd.setVisible(true);
                    menuItemDelete.setVisible(false);
                    menuItemUndo.setVisible(false);
                }

                break;

            case R.id.done:

                ClickToDoneTask();

                break;

            case R.id.undo:

                ClickToUndoTask();

                break;

            default:

        }


        return super.onOptionsItemSelected(item);
    }


    public void PrepareVarToDELLorDONE() {

        alarmId_Del = updateDataTask.getAlarmTask_Id();
        positionItem_Del = updateDataTask.getNumberOfTask();

        disableWhenBack = true;

    }


    public void addItem() {

        TaskAdapter adapter = new TaskAdapter(this, Tasks);

        listView.setAdapter(adapter);

        // to not show scroll listview tasks and show scrollview
        setListViewHeightBasedOnChildren(listView);

    }


    public void addDoneItem() {

        TaskDoneAdapter adapter = new TaskDoneAdapter(this, TasksDone);

        doneListview.setAdapter(adapter);

        // to not show scroll listview tasks done and show scrollview
        setListViewHeightBasedOnChildren(doneListview);

    }


    public void addTask(View view) {

        Intent intent = new Intent(this, Task_Details.class);

        intent.putExtra("currentCount", count);
        intent.putExtra("tableName", "" + MY_PREFS_NAME);
        intent.putExtra("viewId", viewID);
        Log.i("currentCount", "addTask: is " + count);

        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String taskName = "";
        String Description = "";
        String taskDate = "";
        String taskTime = "";
        int alarmTask_Id = 1;

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                taskName = data.getStringExtra("taskname");
                Description = data.getStringExtra("description");
                taskDate = data.getStringExtra("date");
                taskTime = ShowTime(data.getStringExtra("time"), Integer.parseInt(data.getStringExtra("night")));
                alarmTask_Id = data.getIntExtra("alarmTaskId", 1);


                Tasks.add(new Task(count, taskName, Description, taskDate, taskTime, alarmTask_Id));

                //Store data in sharedpreference
                storeItShared(count, taskName, Description, taskDate, taskTime, alarmTask_Id);

                count++;

                addItem();

            }

        } else if (requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {

                taskName = data.getStringExtra("taskname");
                Description = data.getStringExtra("description");
                taskDate = data.getStringExtra("date");
                taskTime = ShowTime(data.getStringExtra("time"), Integer.parseInt(data.getStringExtra("night")));
                alarmTask_Id = data.getIntExtra("alarmTaskId", 1);


                updateDataTask.setTaskName(taskName);
                updateDataTask.setTaskDetails(Description);
                updateDataTask.setDate(taskDate);
                updateDataTask.setTime(taskTime);

                storeItShared(updateDataTask.getNumberOfTask(), taskName, Description, taskDate, taskTime, alarmTask_Id);

                addItem();

            }
        }
    }


    public String ShowTime(String Time, int night) {


        String Night = "";
        String zero = "";

        String[] hourMin = Time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);

//            String Night = hourMin[2].substring(2 , 5);

        if (mins < 10) {

            zero = "0";

        } else {
            zero = "";
        }


        if (night >= 12) {

            Night = " PM";

        } else {

            Night = " AM";
        }


        return hour + ":" + zero + mins + Night;

    }


    public void deleteAlarmTask(int alarmId) {


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), alarmId, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

    }


    public void storeItShared(int count, String taskName, String Description, String taskDate, String taskTime, int alarmTask_Id) {

        editor.putInt("count" + viewID + count, count);
        editor.putString("taskName" + viewID + count, taskName);
        editor.putString("Description" + viewID + count, Description);
        editor.putString("taskDate" + viewID + count, taskDate);
        editor.putString("taskTime" + viewID + count, taskTime);
        editor.putInt("alarmTask_Id" + viewID + count, alarmTask_Id);
        editor.putBoolean("isNotEmpty" + viewID + count, true);
        editor.apply();


    }


    public void storeItDoneShared(int count, String taskName, String Description, String taskDate, String taskTime, int alarmTask_Id) {

        doneEditor.putInt("count" + viewID + count, count);
        doneEditor.putString("taskName" + viewID + count, taskName);
        doneEditor.putString("Description" + viewID + count, Description);
        doneEditor.putString("taskDate" + viewID + count, taskDate);
        doneEditor.putString("taskTime" + viewID + count, taskTime);
        doneEditor.putInt("alarmTask_Id" + viewID + count, alarmTask_Id);
        doneEditor.putBoolean("isNotEmpty" + viewID + count, true);
        doneEditor.apply();


    }


    public void readAllDataShared() {

        int itemId_count = 1;

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        for (; ; ) {

            Boolean restoredText = prefs.getBoolean("isNotEmpty" + viewID + itemId_count, false);
            if (restoredText) {

                count = prefs.getInt("count" + viewID + itemId_count, 0);
                String taskName = prefs.getString("taskName" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String Description = prefs.getString("Description" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String taskDate = prefs.getString("taskDate" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String taskTime = prefs.getString("taskTime" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                int alarmTask_Id = prefs.getInt("alarmTask_Id" + viewID + itemId_count, 0);

                Log.i("taskProcess", "readAllDataShared: " + count + taskName + Description + taskDate + taskTime + alarmTask_Id);

                Tasks.add(new Task(count, taskName, Description, taskDate, taskTime, alarmTask_Id));

                itemId_count++;

            } else {

                break;
            }

        }

        count = itemId_count;

        addItem();

    }


    public void readAllDoneDataShared() {

        int itemId_count = 1;

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME + "done", MODE_PRIVATE);

        for (; ; ) {

            Boolean restoredText = prefs.getBoolean("isNotEmpty" + viewID + itemId_count, false);
            if (restoredText) {

                doneCount = prefs.getInt("count" + viewID + itemId_count, 0);
                String taskName = prefs.getString("taskName" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String Description = prefs.getString("Description" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String taskDate = prefs.getString("taskDate" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                String taskTime = prefs.getString("taskTime" + viewID + itemId_count, "No name defined");//"No name defined" is the default value.
                int alarmTask_Id = prefs.getInt("alarmTask_Id" + viewID + itemId_count, 0);


                TasksDone.add(new Task(doneCount, taskName, Description, taskDate, taskTime, alarmTask_Id));

                itemId_count++;

            } else {

                break;
            }

        }

        doneCount = itemId_count;

        addDoneItem();

    }


    public void RemoveValueFromShared(String valueKey, String doneORnot) {

        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME + doneORnot, 0);
        if (settings.contains("count" + valueKey)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("count" + valueKey);
            editor.remove("taskName" + valueKey);
            editor.remove("Description" + valueKey);
            editor.remove("taskDate" + valueKey);
            editor.remove("taskTime" + valueKey);
            editor.remove("alarmTask_Id" + valueKey);
            editor.remove("isNotEmpty" + valueKey);
            editor.apply();
        }

    }


    public void updateDataShared(int updateIndexFrom, String doneORnot) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME + doneORnot, MODE_PRIVATE);

        for (; ; ) {

            Boolean restoredText = prefs.getBoolean("isNotEmpty" + viewID + updateIndexFrom, false);
            if (restoredText) {

                if ("".equals(doneORnot)) {
                    count = prefs.getInt("count" + viewID + updateIndexFrom, 0);
                } else {
                    doneCount = prefs.getInt("count" + viewID + updateIndexFrom, 0);
                }
                String taskName = prefs.getString("taskName" + viewID + updateIndexFrom, "No name defined");//"No name defined" is the default value.
                String Description = prefs.getString("Description" + viewID + updateIndexFrom, "No name defined");//"No name defined" is the default value.
                String taskDate = prefs.getString("taskDate" + viewID + updateIndexFrom, "No name defined");//"No name defined" is the default value.
                String taskTime = prefs.getString("taskTime" + viewID + updateIndexFrom, "No name defined");//"No name defined" is the default value.
                int alarmTask_Id = prefs.getInt("alarmTask_Id" + viewID + updateIndexFrom, 0);

                // update key and value in shared
                RemoveValueFromShared("" + viewID + updateIndexFrom, doneORnot);

                if ("".equals(doneORnot)) {

                    storeItShared(count - 1, taskName, Description, taskDate, taskTime, alarmTask_Id);
                    Tasks.set(updateIndexFrom - 2, new Task(count - 1, taskName, Description, taskDate, taskTime, alarmTask_Id));

                } else {

                    storeItDoneShared(doneCount - 1, taskName, Description, taskDate, taskTime, alarmTask_Id);
                    TasksDone.set(updateIndexFrom - 2, new Task(doneCount - 1, taskName, Description, taskDate, taskTime, alarmTask_Id));
                }

                updateIndexFrom++;

            } else {

                break;
            }

        }

        if ("".equals(doneORnot)) {
            count = updateIndexFrom - 1;
        } else {
            doneCount = updateIndexFrom - 1;
        }

        // to refresh items in two listview
        addItem();
        addDoneItem();

    }


    public void ClickToDoneTask() {

        Toast.makeText(this, R.string.done_item, Toast.LENGTH_SHORT).show();

        // delete pending intent
        deleteAlarmTask(alarmId_Del);

        // delete from Arraylist
        Tasks.remove(positionItem_Del - 1);

        // delete from sharedPreference
        RemoveValueFromShared("" + viewID + positionItem_Del, "");

        // update sharedPreference
        updateDataShared(positionItem_Del + 1, "");

        // add to done Arraylist (new Arraylist)
        TasksDone.add(new Task(doneCount, updateDataTask.getTaskName(),
                updateDataTask.getTaskDetails(), updateDataTask.getDate(),
                updateDataTask.getTime(), updateDataTask.getAlarmTask_Id()));

        // add to done sharedPreference (new shared)
        storeItDoneShared(doneCount, updateDataTask.getTaskName(),
                updateDataTask.getTaskDetails(), updateDataTask.getDate(),
                updateDataTask.getTime(), updateDataTask.getAlarmTask_Id());

        doneCount++;

        addDoneItem();

        if (!checkisClicked) {
            // invisible menu item except add button
            menuItemAdd.setVisible(true);
            menuItemDelete.setVisible(false);
            menuItemDone.setVisible(false);

        }


    }


    public void ClickToUndoTask() {

        Toast.makeText(this, R.string.done_item, Toast.LENGTH_SHORT).show();

        // delete from Arraylist
        TasksDone.remove(positionItem_Del - 1);

        // delete from sharedPreference
        RemoveValueFromShared("" + viewID + positionItem_Del, "done");

        // update sharedPreference
        updateDataShared(positionItem_Del + 1, "done");

        // add to done Arraylist (new Arraylist)
        Tasks.add(new Task(count, updateDataTask.getTaskName(),
                updateDataTask.getTaskDetails(), updateDataTask.getDate(),
                updateDataTask.getTime(), updateDataTask.getAlarmTask_Id()));

        // add to done sharedPreference (new shared)
        storeItShared(count, updateDataTask.getTaskName(),
                updateDataTask.getTaskDetails(), updateDataTask.getDate(),
                updateDataTask.getTime(), updateDataTask.getAlarmTask_Id());

        count++;

        addItem();

        menuItemAdd.setVisible(true);
        menuItemDelete.setVisible(false);
        menuItemUndo.setVisible(false);

    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + 50;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
