package com.example.hassan.gadwalak.DownloadActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.ShowTables.DB;
import com.example.hassan.gadwalak.ShowTables.fixedtable;

import java.util.ArrayList;


/**
 * Created by 7aSSan on 23/6/2018.
 */

public class adabter extends RecyclerView.Adapter<adabter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);


        }
    }

    Context context;
    DB dbclass;
    ArrayList<String> listitem;

    public adabter(Context context, ArrayList<String> list) {
        this.context = context;
        this.listitem = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_item_name, null);

        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_name.setText(listitem.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                final AlertDialog.Builder deleteornot = new AlertDialog.Builder(context);
                deleteornot.setIcon(R.drawable.ic_delete_black_24dp);
                deleteornot.setTitle("Do you want to delete selected table!");

                deleteornot.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbclass = new DB(context, holder.txt_name.getText().toString());
                        dbclass.ondeletetable();
                        listitem.remove(position);
                        notifyDataSetChanged();
//============================
                    }
                });
                deleteornot.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                deleteornot.show();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, fixedtable.class);
                DB db = new DB(context, holder.txt_name.getText().toString());

                if (db.testTableExistance()) {
                    intent.putExtra("table_name", holder.txt_name.getText().toString());
                    intent.putExtra("existTable", true);
//                    db.createUserTables();
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, R.string.noTable, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {

        return listitem.size();
    }


}
