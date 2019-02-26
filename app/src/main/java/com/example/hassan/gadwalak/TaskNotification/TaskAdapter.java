package com.example.hassan.gadwalak.TaskNotification;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.hassan.gadwalak.R;

import java.util.ArrayList;

/**
 * Created by PRO_Abdo on 3/20/2018.
 */

public class  TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Activity context, ArrayList<Task> Tasks) {
        super(context,0, Tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_list_item, parent, false);
        }

        Task currentView = getItem(position);

        // Find the TextView with view ID magnitude
        TextView NumberOfTask = (TextView) listItemView.findViewById(R.id.taskNumber);
        // Format the magnitude to show 1 decimal place
        int formattedMagnitude = currentView.getNumberOfTask();
        // Display the magnitude of the current earthquake in that TextView
        NumberOfTask.setText(""+formattedMagnitude);

        //===============================================================

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) NumberOfTask.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //==========================================================================

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.taskName);

        nameTextView.setText(currentView.getTaskName());

        TextView descTextView = (TextView) listItemView.findViewById(R.id.taskDetails);

        descTextView.setText(currentView.getTaskDetails());

        TextView dateTextview = (TextView) listItemView.findViewById(R.id.dateShow);

        dateTextview.setText(currentView.getDate());

        TextView timeTextview = (TextView) listItemView.findViewById(R.id.timeShow);

        timeTextview.setText(currentView.getTime());

        return listItemView;
    }
}
