package com.example.hassan.gadwalak.UserInterface;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hassan.gadwalak.R;


/**
 * Created by computer market on 7/11/2018.
 */

public class onlinecorseFragment extends Fragment {


    public onlinecorseFragment() {
        // Required empty public constructor
    }


    ArrayAdapter<String> adapter;
    String[] courses = {"android", "php", "Java", "web design", "computer science",
            "Automotive Technology Course",
            "Network course", "photoshop course", "Excel course", "matlab course",
            "3D Max course", "كورس موارد بشريه ", "English", "Business and leadership", "statistic for Business",
            "Operating System", "Adope After Effects", "German Course", "HTML in Arabic", "ICT Course", "Auto CAD",
            "Ubuntu Linx", "AI Course", "مبادء المحاسبه الماليه", "كورس لغه فرنسيه"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onlinecorse, container, false);


        ListView listView = view.findViewById(R.id.listview1);
        adapter = new
                ArrayAdapter<String>(getActivity(), R.layout.custom_layout, R.id.textViewc, courses);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = (int) parent.getItemIdAtPosition(position);
                if (x == 0) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=VoES7xpuEME&list=PLF8OvnCBlEY3e0Yg990aAXreEru72_xWN"));
                    startActivity(i);
                } else if (x == 1) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=iCUV3iv9xOs&list=PL442FA2C127377F07"));
                    startActivity(i);
                } else if (x == 2) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Hl-zzrqQoSE&list=PLFE2CE09D83EE3E28"));
                    startActivity(i);
                } else if (x == 3) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Jcnsk3yNbuA&list=PLhPa7T8XPi0KkPj8ffG8xt-Ga9ueqvHph"));
                    startActivity(i);
                } else if (x == 4) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=tpIctyqH29Q&list=PL8dPuuaLjXtNlUrzyH5r6jN9ulIgZBpdo"));
                    startActivity(i);
                } else if (x == 6) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=99yGdRRNe0M&list=PLuBig59nkpxxQFW4j3ofXknuhqbgUh7ZX"));
                    startActivity(i);
                } else if (x == 7) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fEvyipju8bc&list=PLg9ps5Gu0MiBJt0okQBHazj6Zl6016BLa"));
                    startActivity(i);
                } else if (x == 8) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ti3cd7iBhPk&list=PLd5gfb34n8F58ZoQpK9eNp3G9rTu7B1Ee"));
                    startActivity(i);
                } else if (x == 9) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=7iQOfKCRyiM&list=PLAI6JViu7Xmdcak1DAnXe2BX3tQh_cSvo"));
                    startActivity(i);
                } else if (x == 10) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=udwVLoHAuGw&list=PLHpslpwBDGL597b9bC6jZ6e3NpJxImES7"));
                    startActivity(i);
                } else if (x == 11) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=t-SZJhP7XIM&list=PLqGEgt_8f5gRpHXDN_5qxKyCoI5gRqnbK"));
                    startActivity(i);
                } else if (x == 12) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=IUgA--KgtMc&list=PL5isa5XjlZ5qKX61YuOdq-aoFy3ZHF_hb"));
                    startActivity(i);
                } else if (x == 13) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=5_Q_qmvt8RM&list=PL2fCZiDqOYYX2-diD1Kd1iNkWPIHc4HFI"));
                    startActivity(i);
                } else if (x == 14) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FIBn7wxyjA0&list=PLtcsEN2flco0CcQse4PzUKTU_7G1r2spD"));
                    startActivity(i);
                } else if (x == 15) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=aRSo7wkV8Ks&list=PLHKTPL-jkzUqgHIBdC2I16QqZCSDN_6oS"));
                    startActivity(i);
                } else if (x == 16) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=uwAu5X_-7kQ&list=PLZ5zEGbaMXXWaWA35YyocBd5djZPLaTsX"));
                    startActivity(i);
                } else if (x == 17) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=39IZVX4cVmc&list=PL5QyCnFPRx0GxaFjdAVkx7K9TfEklY4sg"));
                    startActivity(i);
                } else if (x == 18) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PYjyg0LNTfE&list=PLDoPjvoNmBAwClZ1PDcjWilxp9YERUbNt"));
                    startActivity(i);
                } else if (x == 19) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=70jwHKiKywc&list=PLK3QoWSTqhuKoJu4pvk9vINCpVU91GujM"));
                    startActivity(i);
                } else if (x == 20) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=63WFLqeqtWU&list=PLsJ-t44YipGu5qo1np4id7asHSLdvdLgD"));
                    startActivity(i);
                } else if (x == 21) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=snB40gNSeM8&list=PL5H3smBFNEEjZGdevQqxoYWda3F47znyw"));
                    startActivity(i);
                } else if (x == 22) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=TjZBTDzGeGg&list=PLUl4u3cNGP63gFHB6xb-kVBiQHYe_4hSi"));
                    startActivity(i);
                } else if (x == 23) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=_kpskjFFx3c&list=PL6E1E19EB259776DC"));
                    startActivity(i);
                } else if (x == 24) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bzQQK1TgSTs&list=PLln8E9ScL0bTtOn6MnynSfb64bAzREG9-"));
                    startActivity(i);
                }
            }
        });

        return view;

    }

}

