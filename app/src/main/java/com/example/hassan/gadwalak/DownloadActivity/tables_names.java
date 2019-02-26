package com.example.hassan.gadwalak.DownloadActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.ShowTables.DB;
import com.example.hassan.gadwalak.ShowTables.TablesNamesProvider;
import com.example.hassan.gadwalak.ShowTables.fixedtable;
import com.example.hassan.gadwalak.UserInterface.MainActivityInterface;
import com.example.hassan.gadwalak.UserInterface.TablesNamesWidget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;


public class tables_names extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private MenuItem menuItemAdd;
    private MenuItem menuItemDelete;
    private MenuItem menuItemDone;
    private MenuItem menuItemUndo;
    ArrayList<String> tablesnames = new ArrayList<>();
    String fromEditText;
    TextView empetyText;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables_names);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        empetyText=findViewById(R.id.empty_view);
        stringArrayList.clear();
        DB db = new DB(this);
        db.testTableExistance();
        tablesnames = db.getAllTablesNames();
        try {
            if (tablesnames.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                empetyText.setVisibility(View.VISIBLE);
            }
            else {
                recyclerView.setVisibility(View.VISIBLE);
                empetyText.setVisibility(View.GONE);
                adabter a = new adabter(this, tablesnames);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setAdapter(a);
            }



        } catch (Exception e) {
            e.getStackTrace();
        }
        MobileAds.initialize(this, "ca-app-pub-5128215728925284~1360258605");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.TableName);
                final EditText editText = new EditText(this);
                builder.setView(editText);
                builder.setPositiveButton(R.string.Build, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(tables_names.this, fixedtable.class);

                        fromEditText = editText.getText().toString();
                        intent.putExtra("table_name", fromEditText);
                        DB db = new DB(getBaseContext(), fromEditText);

                        if (db.testTableExistance()) {
                            Toast.makeText(getBaseContext(), R.string.TableExist, Toast.LENGTH_SHORT).show();
                        } else {

                            db.createUserTables();
                            startActivity(intent);
                        }

                    }
                });
                builder.setNegativeButton(R.string.Cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            } catch (Exception e) {
                e.getStackTrace();
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent i = new Intent(this, MainActivityInterface.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }




}
