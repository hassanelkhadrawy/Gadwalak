package com.example.hassan.gadwalak.ShowTables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.hassan.gadwalak.R;

import java.util.ArrayList;


public class DB extends SQLiteOpenHelper {
    private static final String Dbname = "Softitable.db";
    private static String Tablename = "newTable";
    private static String userTablename = "theTable";
    private static int version_code = 1;
    String Dbpath;
    /* version number of the database (starting at 1); if the database is older,
           onUpgrade will be used to upgrade the database; if the database is
           newer, onDowngrade will be used to downgrade the database
     */
    private Context context;


    //  /data/data/packagename/databases/Dbname.db
    public DB(Context context, String tablename ) {
        super(context, Dbname, null, version_code);   // create the database
        Tablename = tablename.replaceAll("[^\\p{L}\\p{N}]+", "");
        Log.d("Table Name",Tablename);
        userTablename = tablename;
        this.context = context;
        // Toast.makeText(context, "Constructor is Called Successfully .", Toast.LENGTH_SHORT).show();
        Dbpath = context.getDatabasePath(Dbname).getPath();
        Log.d("softitable", "DB Path : " + Dbpath);
        // Toast.makeText(context, " DB path is " + Dbpath, Toast.LENGTH_LONG).show();
        //null >> create your custom cursor object


    }

    public DB(Context context) {
        super(context, Dbname, null, version_code);
        Dbpath = context.getDatabasePath(Dbname).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table firstTable(userInput,realName);");
        Log.v("oncreatemethod","created once");
    }

    public void createUserTables() {
        SQLiteDatabase db = getWritableDatabase();
        if (!testTableExistance()) {
            try {
                ContentValues contentValues=new ContentValues();
                contentValues.put("userInput",userTablename);
                contentValues.put("realName",Tablename);
                db.execSQL("create table " + Tablename + "( id Integer , lectureName , lecturerName , place , fromTime , toTime);");

                db.insert("firstTable",null,contentValues);
                Toast.makeText(context, R.string.DataCreateSucess, Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Log.d("execption", e.getMessage());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //use this method to upgrade to a new database schema
        // Not mandatory to make drop to the table , you can make any query in the upgrade method
        try {
            db.execSQL("drop table if exists " + Tablename);
            onCreate(db); // to recreate the table again
        } catch (SQLException e) {
        }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table if exists " + Tablename);
            onCreate(db); // to recreate the table again
        } catch (SQLException e) {
        }
    }

    public boolean insertData(int id, String LectureName, String LecturerName, String Place, String FromTime, String ToTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("id", id);
        content.put("lectureName", LectureName);
        content.put("lecturerName", LecturerName);
        content.put("place", Place);
        content.put("fromTime", FromTime);
        content.put("toTime", ToTime);
        long result = db.insert(Tablename, null, content);
        // result >> the row ID of the newly inserted row, or -1 if an error occurred
        //Log.d("ssssssaaaaadddddddd", Tablename);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    String idUpdate;

    public boolean updateData(String table, int id, String LectureName, String LecturerName, String Place, String FromTime, String ToTime) {
        SQLiteDatabase db = getWritableDatabase();
        idUpdate = String.valueOf(id);
        Tablename = table;
        ContentValues content = new ContentValues();
        //update based the id column
        content.put("lectureName", LectureName);
        content.put("lecturerName", LecturerName);
        content.put("place", Place);
        content.put("fromTime", FromTime);
        content.put("toTime", ToTime);
        int result = db.update(Tablename, content, "id=?", new String[]{idUpdate});
        //result >> the number of rows affected or >> = 0 if no update was done !
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

//    public boolean removeData(String id) {
//// Firstly searching for the id
//        SQLiteDatabase db1 = getReadableDatabase();
//
//        Cursor cursor = db1.rawQuery("select * from " + Tablename + " where id=?", new String[]{id});
//        // cursor like result set in java
//        Boolean flag = false; // not founded as a default result
//        if (cursor.moveToNext()) {
//            flag = true;  // founded
//        }
//        if (flag == true) {
//// continue the delete process
//            SQLiteDatabase db = getWritableDatabase();
//            int result = db.delete(Tablename, "id=?", new String[]{id});
//            // result >>= the number of rows affected , >> = 0 if No Deletion occur .
//            // To remove all rows , pass "1" as the whereClause.
////            int result = db.delete(Tablename, "1",null); // all rows are deleted from table
//            return true;
//        } else {
//            return false;
//        }
//    }

    public ArrayList getAllData(String tableTitle) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Tablename = tableTitle;
        Cursor cursor = db.rawQuery("select * from " + Tablename, null);
        //Cursor object is positioned before the first entry.
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String id = cursor.getString(0);
            String lecName = cursor.getString(1);
            String lecerName = cursor.getString(2);
            String place = cursor.getString(3);
            String fromT = cursor.getString(4);
            String toT = cursor.getString(5);

            arrayList.add(id + "-" + lecName + "-" + lecerName + "-" + place + "-" + fromT + "-" + toT);

            cursor.moveToNext();
        }
        return arrayList;
    }

    public void ondeletetable() {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.execSQL("drop table if exists " + Tablename);
            db.delete("firstTable", "userInput=?", new String[]{userTablename});
            Toast.makeText(context, R.string.TableDeleted, Toast.LENGTH_SHORT).show();
        } catch (SQLException exception) {
            Toast.makeText(context, R.string.TableNotDeleted, Toast.LENGTH_SHORT).show();
        }

    }


    public boolean testTableExistance() {
        final ArrayList<String> dirArray = new ArrayList<String>();
        int co = 0;
        SQLiteDatabase DB = getWritableDatabase();
        Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                dirArray.add(c.getString(c.getColumnIndex("name")));
                Log.d("aykalam", "tables :   " + dirArray.get(co) + "           " + Tablename);
                if (Tablename.equals(dirArray.get(co)))
                    return true;
                c.moveToNext();
                co++;
            }
        }

        return false;
    }

    public ArrayList<String> getAllTablesNames() {
        final ArrayList<String> dirArray = new ArrayList<String>();
        int co = 0;
        SQLiteDatabase DB = getWritableDatabase();
        //Cursor c = DB.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        Cursor c = DB.rawQuery("select userInput from firstTable", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                dirArray.add(c.getString(c.getColumnIndex("userInput")));
                c.moveToNext();
                co++;
            }
        }

        return dirArray;
    }


}