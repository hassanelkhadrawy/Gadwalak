package com.example.hassan.gadwalak.ShowTables;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassan.gadwalak.DownloadActivity.model;
import com.example.hassan.gadwalak.DownloadActivity.tables_names;
import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.TaskNotification.MainActivity;
import com.example.hassan.gadwalak.TaskNotification.Task;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by computer market on 6/22/2018.
 */

public class fixedtable extends AppCompatActivity {
    static int id2;
    String id;
    TextView ttt;
    TextView tableTitle;
    DB dbObject;
    ArrayList<Integer> arrayOfID = new ArrayList<Integer>();
    ArrayList<model> dayes_list = new ArrayList<>();
    String tablenameafterreplace;
    TableLayout yourRootLayout;
    Bundle b;
    Task task_cell_name;

    Cell_Data cd = new Cell_Data();
    String table_name;
    String[] separated;
    int theID;
    private InterstitialAd mInterstitialAd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixedtable);

        try {
            task_cell_name = new Task();
            b = getIntent().getExtras();
            table_name = b.getString("table_name");

            tablenameafterreplace = table_name.replaceAll("[^\\p{L}\\p{N}]+", "");
            tableTitle = (TextView) findViewById(R.id.text0);
            tableTitle.setText(table_name);
            dbObject = new DB(this, table_name);
            yourRootLayout = findViewById(R.id.tabla_cuerpo);
            if (b.getBoolean("existTable", false)) {
                viewInTable();
            } else if (b.getBoolean("come_from_web", false)) {
                getID(yourRootLayout);
                try {
                    createtablefromweb();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        viewInTable();

                    }
                });

            } else {
                getID(yourRootLayout);
                createdatabaseWithNullTable();
            }
            actioCells();
        } catch (Exception e) {
            e.getStackTrace();
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5128215728925284/8805657874");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    //================== Method to get cell id which user has clicked ================
    public void actioCells() {
        int count = yourRootLayout.getChildCount();
        for (int i = 2; i < count; i++) {
            View v = yourRootLayout.getChildAt(i);
            if (v instanceof TableRow) {
                TableRow row = (TableRow) v;
                int rowCount = row.getChildCount();
                for (int r = 0; r < rowCount; r++) {
                    View v2 = row.getChildAt(r);
                    if (v2 instanceof TextView) {
                        final TextView tv = (TextView) v2;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                id = v.getResources().getResourceName(v.getId());
                                id2 = v.getId();
                                Log.d("softitable", "ID: " + id2);
                                Log.d("softitable", "Tag: " + v.getTag());
                                registerForContextMenu(tv);
                                openContextMenu(tv);
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                } else {
                                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                                }
                            }
                        });
                    }
                }
            }


        }

    }

    String subName;

    @SuppressLint("ResourceType")
    public void viewInTable() {
        ArrayList result = dbObject.getAllData(tablenameafterreplace);
        Log.i("arraySize", "viewInTable: " + result.size());
        for (int f = 0; f < 48; f++) {
            String s = (String) result.get(f);
            Log.d("softitable", "DBssss : " + s);
            separated = s.split("-");
            theID = Integer.parseInt(separated[0]);
            subName = separated[1];
            String docName = separated[2];
            String place = separated[3];
            String fromTo = separated[4] + " - " + separated[5];

            Log.d("softitable", "DB ssss : " + theID + " " + subName);
            ttt = (TextView) findViewById(theID);
            if (!"null".equals(subName))
                ttt.setText(subName + "\n" + docName + "\n" + place + "\n" + fromTo);
            else
                ttt.setText("______________");

        }

    }

    public void createdatabaseWithNullTable() {
        boolean donesuceessfully = false;
        for (int i = 0; i < arrayOfID.size(); i++) {
            boolean status = dbObject.insertData(arrayOfID.get(i), null, null, null, null, null);
            if (status == true) {
                donesuceessfully = true;
            } else {
                donesuceessfully = false;
                break;
            }
        }
        if (donesuceessfully) {
            Toast.makeText(this, R.string.Datadonesucess, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.Datafailed, Toast.LENGTH_SHORT).show();
        }


    }


    public void getID(View v) {
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v1 = viewGroup.getChildAt(i);
            if (v1 instanceof ViewGroup) getID(v1);
            String s = getResources().getResourceEntryName(v1.getId());
            if (s.contains("textView")) {
                Log.d("name", "DatabaseIDs" + v1.getId() + ",," + s);
                arrayOfID.add(v1.getId());
            }
        }

        Log.i("arrayofID", "arraySize is (ID): " + arrayOfID.size());
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_action, menu);
        menu.setHeaderTitle(R.string.selectContextTitle);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.Edit_option) {
            Intent edit = new Intent(getBaseContext(), Cell_Data.class);
            ttt = (TextView) findViewById(id2);
            if (!"______________".equals(ttt.getText().toString()))
                edit.putExtra("data", ttt.getText().toString());
            edit.putExtra("CellID", id2);
            edit.putExtra("tableName", table_name);
            startActivity(edit);
        } else if (item.getItemId() == R.id.Tasks_option) {
            Intent task = new Intent(getBaseContext(), MainActivity.class);
            ttt = (TextView) findViewById(id2);
            task.putExtra("CellID", id2);
            if (!"______________".equals(ttt.getText().toString()))
                task.putExtra("data", ttt.getText().toString());
            task.putExtra("tableName", table_name);

            startActivity(task);
        } else if (item.getItemId() == R.id.Delete_option) {
            boolean status = dbObject.updateData(tablenameafterreplace, id2, null, null, null, null, null);
            if (status == true) {
                ttt = (TextView) findViewById(id2);
                ttt.setText("______________");
                Toast.makeText(this, R.string.Datadeleted, Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, R.string.Datanotdeleted, Toast.LENGTH_SHORT).show();

        } else {
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tables_names.class);
        startActivity(intent);
        super.onBackPressed();
        finish();

    }

    boolean status;

    public void createtablefromweb() throws JSONException {
        b = getIntent().getExtras();
        String json = b.getString("json");
        loadSectionData(json);
        dbObject.createUserTables();
        for (int i = 0; i < arrayOfID.size(); i++) {
            if (i < dayes_list.size()) {

                status = dbObject.insertData(arrayOfID.get(i), dayes_list.get(i).lectureName, dayes_list.get(i).lecturerName, dayes_list.get(i).place, dayes_list.get(i).fromTime, dayes_list.get(i).toTime);
                Log.d("ddd", "" + status);
            } else {
                status = dbObject.insertData(arrayOfID.get(i), null, null, null, null, null);

            }
        }
        if (status == true) {
            Toast.makeText(this, R.string.Tablecreated, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.Tablenotcreated, Toast.LENGTH_SHORT).show();

        }

    }


    private void loadSectionData(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            String lectureName = obj.getString("lectureName");
            String lecturerName = obj.getString("lecturerName");
            String place = obj.getString("place");
            String fromTime = obj.getString("fromTime");
            String toTime = obj.getString("toTime");
            dayes_list.add(new model(lectureName, lecturerName, place, fromTime, toTime));


        }


    }




}

