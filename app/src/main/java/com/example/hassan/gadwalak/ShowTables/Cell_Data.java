package com.example.hassan.gadwalak.ShowTables;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.hassan.gadwalak.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DateFormat;
import java.util.Calendar;

public class Cell_Data extends AppCompatActivity implements View.OnClickListener {
    EditText Sub_name, Lecturer_name, Place, From_time, To_time, settime;
    Button save_btn, cancel_btn;
    DateFormat formatTime = DateFormat.getTimeInstance();
    DB dbObjet;
    Calendar dateTime = Calendar.getInstance();
    int editID;
    String tableNameIs;
    String tableNameAfterReplace;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell__data);
        Sub_name = (EditText) findViewById(R.id.S_name);
        Lecturer_name = (EditText) findViewById(R.id.l_name);
        Place = (EditText) findViewById(R.id.P_name);
        From_time = (EditText) findViewById(R.id.f_name);
        To_time = (EditText) findViewById(R.id.t_name);
        save_btn = (Button) findViewById(R.id.savebtn);
        cancel_btn = (Button) findViewById(R.id.cancelbtn);
        Bundle getdata = getIntent().getExtras();
        String data = getdata.getString("data", null);
        try {
            Log.i("data", "data is " + data);
            if (data != null) {
                String[] splitter = data.split("\\n");
                Sub_name.setText(splitter[0]);
                Lecturer_name.setText(splitter[1]);
                Place.setText(splitter[2]);
                String[] timesplit = splitter[3].split("-");
                From_time.setText(timesplit[0]);
                To_time.setText(timesplit[1]);

            }
        } catch (Exception e) {
            e.getStackTrace();
        }


        From_time.setOnClickListener(this);
        To_time.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        dbObjet = new DB(this, "myCollage");
        tableNameIs = getIntent().getStringExtra("tableName");
        tableNameAfterReplace = tableNameIs.replaceAll("[^\\p{L}\\p{N}]+", "");
        /* mobAd codes to initialize and preview the ads */
        MobileAds.initialize(this, "ca-app-pub-5128215728925284~1360258605");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void updateTime() {

        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();

    }


    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

            try {
                dateTime.set(Calendar.HOUR_OF_DAY, i);

                dateTime.set(Calendar.MINUTE, i1);

                dateTime.set(Calendar.SECOND, 0);

                dateTime.set(Calendar.MILLISECOND, 0);
                settime = (EditText) findViewById(editID);
                settime.setText("  " + formatTime.format(dateTime.getTime()));

            }catch (Exception e){
                 e.getStackTrace();
            }




        }
    };

    int cellID = 1;

    public void saveclick() {


        try {
            cellID = getIntent().getIntExtra("CellID", 0);
            Log.d("Abdo", "CAllid" + cellID);
            boolean fieldsOK = checkEmptyFields(new EditText[]{Sub_name, Lecturer_name, Place, From_time, To_time});

            if (!fieldsOK) {
                Toast.makeText(this, R.string.TextEmpety, Toast.LENGTH_SHORT).show();

            } else {
                boolean status = dbObjet.updateData(tableNameAfterReplace, cellID, Sub_name.getText().toString(), Lecturer_name.getText().toString(), Place.getText().toString(), From_time.getText().toString(), To_time.getText().toString());
                if (status == true) {

                    Toast.makeText(this, R.string.UpdateData, Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getBaseContext(), fixedtable.class);
                    in.putExtra("table_name", tableNameIs);
                    in.putExtra("existTable", true);
                    startActivity(in);


                } else {
                    Toast.makeText(this, R.string.UpdateDataFaild, Toast.LENGTH_SHORT).show();

                }
            }

        }catch (Exception e){
          e.getStackTrace();
        }


    }


    private boolean checkEmptyFields(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.f_name:
                editID = v.getId();
                updateTime();
                From_time.setText("  " + formatTime.format(dateTime.getTime()));
                break;
            case R.id.t_name:
                editID = v.getId();
                updateTime();
                To_time.setText("  " + formatTime.format(dateTime.getTime()));
                break;
            case R.id.savebtn:
                saveclick();
                break;
            case R.id.cancelbtn:
                Intent cancelintent = new Intent(getBaseContext(), fixedtable.class);
                cancelintent.putExtra("table_name", tableNameIs);
                cancelintent.putExtra("existTable", true);
                startActivity(cancelintent);
                break;
            default:
                Log.d("defaultincelldata", "Error");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, fixedtable.class);
        intent.putExtra("table_name", tableNameIs);
        intent.putExtra("existTable", true);
        startActivity(intent);
        super.onBackPressed();
        finish();

    }
}
