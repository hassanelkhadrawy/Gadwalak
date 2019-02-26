package com.example.hassan.gadwalak.UserInterface;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.example.hassan.gadwalak.DownloadActivity.tables_names;
import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.ShowTables.DB;
import com.example.hassan.gadwalak.ShowTables.fixedtable;

import java.util.ArrayList;


/**
 * Implementation of App Widget functionality.
 */
public class TablesNamesWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {






        StringBuilder sb = new StringBuilder();
        ArrayList<String> tablesnames;
        DB db = new DB(context);
        tablesnames = db.getAllTablesNames();

        sb.append("            Tables Names \n");
        for (int i = 0; i < tablesnames.size(); i++) {
            sb.append((i+1)+"-"+tablesnames.get(i) + "\n");

        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tables_names_widget);
        views.setTextViewText(R.id.appwidget_text, sb.toString());


        Intent intentUpdate = new Intent(context, TablesNamesWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

//Update the current widget instance only, by creating an array that contains the widget’s unique ID//
//
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

//Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);


        views.setOnClickPendingIntent(R.id.appwidget_text, pendingUpdate);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Toast.makeText(context, R.string.widget_updated , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }



}

