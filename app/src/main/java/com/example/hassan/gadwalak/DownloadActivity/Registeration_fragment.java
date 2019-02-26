package com.example.hassan.gadwalak.DownloadActivity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hassan.gadwalak.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Registeration_fragment extends Fragment {


    View student_view;
    LinearLayout student_layout, new_table_layout;

    public Registeration_fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_registeration_fragment, container, false);

        new_table_layout = (LinearLayout) view.findViewById(R.id.new_table_linear);
        student_layout = (LinearLayout) view.findViewById(R.id.studentlinear);

        new_table_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tablesView = new Intent(getActivity(), tables_names.class);
                startActivity(tablesView);
            }
        });


        student_view = LayoutInflater.from(getActivity()).inflate(R.layout.student, null);


        student_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SpineerActivity.class);
                startActivity(i);


            }
        });

        return view;
    }


}
