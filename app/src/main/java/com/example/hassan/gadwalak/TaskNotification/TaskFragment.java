package com.example.hassan.gadwalak.TaskNotification;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hassan.gadwalak.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    private ListView listView ;
    private ListView done_listView ;
    private TextView tasks_View ,textEmpety;
    private TextView tasksDone_View ;
Task task;
    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_task, container, false);


        listView = (ListView)view.findViewById(R.id.listview);
        done_listView = (ListView)view.findViewById(R.id.doneListview);
        tasks_View = (TextView)view.findViewById(R.id.tasks_view);
        tasksDone_View = (TextView)view.findViewById(R.id.tasksdone_view);
        textEmpety=view.findViewById(R.id.empty_view2);
              listView.setEmptyView(textEmpety);
        return view;
    }




}
