package com.example.hassan.gadwalak.ShowTables;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

public class TablesNamesProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.hassan.gadwalak.TablesNamesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/Tables";
    static final Uri CONTENT_URI = Uri.parse(URL);

   public static final String _ID = "userInput";
   public static final String NAME = "name";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int Table = 1;
    static final int Table_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Tables", Table);
        uriMatcher.addURI(PROVIDER_NAME, "Tables/#", Table_ID);
    }


    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DB dbHelper = new DB(context);


        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("firstTable");

        switch (uriMatcher.match(uri)) {
            case Table:
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;

            case Table_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(0));
                break;

            default:
        }

        if (sortOrder == null || sortOrder == "") {

            sortOrder = NAME;
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        return count;
    }


    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {

        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {

            case Table:
                return "vnd.android.cursor.dir/vnd.example.Tables";

            case Table_ID:
                return "vnd.android.cursor.item/vnd.example.Tables";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}