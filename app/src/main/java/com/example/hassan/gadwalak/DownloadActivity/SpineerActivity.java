package com.example.hassan.gadwalak.DownloadActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassan.gadwalak.R;
import com.example.hassan.gadwalak.ShowTables.DB;
import com.example.hassan.gadwalak.ShowTables.TablesNamesProvider;
import com.example.hassan.gadwalak.ShowTables.fixedtable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dmax.dialog.SpotsDialog;


public class SpineerActivity extends AppCompatActivity {
    Button go;
    String url, univ_name, univ_id, years_count, faculty_id, faculty_name, department_name, section_count;
    Spinner spineer_university, spinner_faculty, spinner_department, spinner_sction, spinner_years;
    ArrayList<String> univeristy_list = new ArrayList<>();
    ArrayList<String> univeristy_id = new ArrayList<>();
    ArrayList<String> faculty_list = new ArrayList<>();
    ArrayList<String> check_list = new ArrayList<>();
    ArrayList<Integer> count_years = new ArrayList<>();
    ArrayList<String> years_list = new ArrayList<>();
    ArrayList<String> facultynames = new ArrayList<>();
    ArrayList<String> departments_list = new ArrayList<>();
    ArrayList<String> departments_letter = new ArrayList<>();
    ArrayList<String> section_list = new ArrayList<>();
    ArrayList<Integer> count_section = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;
    private AlertDialog Dialog;
private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spineer);
        spineer_university = (Spinner) findViewById(R.id.univ_spinner);
        spinner_faculty = (Spinner) findViewById(R.id.faculty_spinner);
        spinner_years = (Spinner) findViewById(R.id.years_spinner);
        spinner_department = (Spinner) findViewById(R.id.department_spinner);
        spinner_sction = (Spinner) findViewById(R.id.section_spinner);
        handleSSLHandshake();
        go = findViewById(R.id.go);
        refreshLayout = findViewById(R.id.refresh);

        Dialog = new SpotsDialog(this, R.style.Custom);


        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        url = "https://softizone.000webhostapp.com/university.php";
        univeristy_list.add("select university");
        check_list.add("university");
        univeristy_id.add("univ_id");
        new JsonUtils().execute();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                url = "https://softizone.000webhostapp.com/university.php";
                univeristy_list.clear();
                check_list.clear();
                univeristy_list.add(String.valueOf(R.string.selectuniversity));
                check_list.add(String.valueOf(R.string.university));
                univeristy_id.add("univ_id");
                new JsonUtils().execute();

            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spineer_university.getSelectedItemPosition() == 0) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_s_u), Toast.LENGTH_SHORT).show();
                } else if (spinner_faculty.getSelectedItemPosition() == 0) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_s_f), Toast.LENGTH_SHORT).show();

                } else if (spinner_years.getSelectedItemPosition() == 0) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_s_y), Toast.LENGTH_SHORT).show();

                } else if (spinner_department.getSelectedItemPosition() == 0) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_s_d), Toast.LENGTH_SHORT).show();

                } else if (spinner_sction.getSelectedItemPosition() == 0) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_s_s), Toast.LENGTH_SHORT).show();

                } else if (spineer_university.getSelectedItem() == null) {
                    Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_c_i), Toast.LENGTH_LONG).show();
                } else {
                    getData();
                }


            }
        });
        /* mobAd codes to initialize and preview the ads */
        MobileAds.initialize(this, "ca-app-pub-5128215728925284~1360258605");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    void SpinnerUniversityNames() {


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(SpineerActivity.this, android.R.layout.simple_list_item_1, univeristy_list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#6A1B9A"));
                return view;

            }
        };
        spineer_university.setAdapter(faculty_adapter);

        spineer_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {

                    spinner_faculty.setVisibility(View.GONE);
                    spinner_department.setVisibility(View.GONE);
                    spinner_years.setVisibility(View.GONE);
                    spinner_sction.setVisibility(View.GONE);
                } else {


                    check_list.clear();
                    check_list.add("faculty");
                    facultynames.add("faculty");
                    faculty_list.clear();
                    faculty_list.add("select faculty");
                    count_years.add(0);

                    url = "https://softizone.000webhostapp.com/faculty.php?faculty_name=" + univeristy_id.get(spineer_university.getSelectedItemPosition());
                    new JsonUtils().execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    void SpinnerFacultyNames() {


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(SpineerActivity.this, android.R.layout.simple_list_item_1, faculty_list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#6A1B9A"));
                return view;

            }
        };
        spinner_faculty.setAdapter(faculty_adapter);

        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {


                    spinner_department.setVisibility(View.GONE);
                    spinner_years.setVisibility(View.GONE);
                    spinner_sction.setVisibility(View.GONE);
                } else {
                    years_list.clear();
                    loadyears();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    void SpinnerFacultyYears() {


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(SpineerActivity.this, android.R.layout.simple_list_item_1, years_list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#6A1B9A"));
                return view;

            }
        };
        spinner_years.setAdapter(faculty_adapter);

        spinner_years.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {


                    spinner_department.setVisibility(View.GONE);
                    spinner_sction.setVisibility(View.GONE);
                } else {

                    url = "https://softizone.000webhostapp.com/facultyDepartments.php?faculty_name=" + facultynames.get(spinner_faculty.getSelectedItemPosition()) + "&year_id=" + facultynames.get(spinner_faculty.getSelectedItemPosition()) + "year_" + spinner_years.getSelectedItemPosition();
                    check_list.clear();
                    check_list.add("deparment");
                    departments_letter.clear();
                    departments_letter.add("letters");
                    count_section.clear();
                    count_section.add(0);
                    departments_list.clear();
                    departments_list.add("select department");

                    Log.d("massage", "faculty_name=" + facultynames.get(spinner_faculty.getSelectedItemPosition()) + "&year_id=" + facultynames.get(spinner_faculty.getSelectedItemPosition()) + "year_" + spinner_years.getSelectedItemPosition());
                    new JsonUtils().execute();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    void SpinnerFacultyDepartments() {


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(SpineerActivity.this, android.R.layout.simple_list_item_1, departments_list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#6A1B9A"));
                return view;

            }
        };
        spinner_department.setAdapter(faculty_adapter);

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {


                    spinner_sction.setVisibility(View.GONE);
                } else {
                    section_list.clear();
                    loadsections();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    void SpinnerDepartmentsSections() {


        ArrayAdapter<String> faculty_adapter = new ArrayAdapter<String>(SpineerActivity.this, android.R.layout.simple_list_item_1, section_list) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#6A1B9A"));
                return view;

            }
        };
        spinner_sction.setAdapter(faculty_adapter);

        spinner_sction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {


                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    class JsonUtils extends AsyncTask<Void, Void, String> {

        String JsonUrl = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            JsonUrl = url;
            if (spinner_sction.getSelectedItem() == null) {
                progressDialog.show();

            } else {
                Dialog.show();
            }


        }

        @Override
        protected String doInBackground(Void... voids) {
            try {


                URL url = new URL(JsonUrl);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line + "\n");

                }
                is.close();
                bufferedReader.close();
                con.disconnect();
                return sb.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                progressDialog.dismiss();
                refreshLayout.setRefreshing(false);

                No_Connection();

                Toast.makeText(SpineerActivity.this, String.valueOf(R.string.p_c_i), Toast.LENGTH_LONG).show();


            } else {
                try {
                    if (check_list.contains("university")) {
                        loadUniveristyNames(s);
                        refreshLayout.setRefreshing(false);
                    } else if (check_list.contains("faculty")) {
                        loadFacultyNames(s);
                    } else if (check_list.contains("deparment")) {

                        loadDepartmentsNames(s);
                    } else if (check_list.contains("sectiondata")) {

                        String table_name ="Year "+spinner_years.getSelectedItemPosition()+" "+ spinner_sction.getSelectedItem() + " " + departments_letter.get(spinner_department.getSelectedItemPosition());

                        DB dbobj = new DB(getBaseContext(), table_name);
                        if (dbobj.testTableExistance()) {
                            Dialog.dismiss();
                            Toast.makeText(getBaseContext(), String.valueOf(R.string.TableExist), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(SpineerActivity.this, fixedtable.class);
                            intent.putExtra("table_name", table_name);
                            intent.putExtra("come_from_web", true);
                            intent.putExtra("json", s);
                            Dialog.dismiss();
                            startActivity(intent);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    private void loadUniveristyNames(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            univ_name = obj.getString("univ_name");
            univ_id = obj.getString("univ_id");
            univeristy_list.add(univ_name);
            univeristy_id.add(univ_id);


        }
        SpinnerUniversityNames();
        progressDialog.dismiss();
        Dialog.dismiss();


    }

    private void loadFacultyNames(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            faculty_name = obj.getString("faculty_name");
            faculty_id = obj.getString("faculty_id");
            years_count = obj.getString("years_count");
            faculty_list.add(faculty_name);
            facultynames.add(faculty_id);
            int x = Integer.parseInt(years_count);
            count_years.add(x);

        }
        spinner_faculty.setVisibility(View.VISIBLE);
        SpinnerFacultyNames();
        progressDialog.dismiss();
        Dialog.dismiss();


    }

    void loadyears() {
        years_list.add("select year");
        for (int countYears = 1; countYears <= count_years.get(spinner_faculty.getSelectedItemPosition()); countYears++) {

            years_list.add("year " + countYears);
        }
        SpinnerFacultyYears();
        spinner_years.setVisibility(View.VISIBLE);
        Dialog.dismiss();

    }

    private void loadDepartmentsNames(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("data");


        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            department_name = obj.getString("Departments");
            String letters = obj.getString("Departments_Letter");
            section_count = obj.getString("Section_Count");
            departments_list.add(department_name);
            departments_letter.add(letters);
            int z = Integer.parseInt(section_count);
            count_section.add(z);


        }
        spinner_department.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
        SpinnerFacultyDepartments();
        Dialog.dismiss();


    }

    void loadsections() {
        section_list.add("select section");
        for (int countSection = 1; countSection <= count_section.get(spinner_department.getSelectedItemPosition()); countSection++) {

            section_list.add("Section " + countSection);
        }
        SpinnerDepartmentsSections();
        spinner_sction.setVisibility(View.VISIBLE);
        Dialog.dismiss();

    }


    void getData() {
        check_list.clear();
        check_list.add("sectiondata");
        url = "https://softizone.000webhostapp.com/sections.php?section_name=" + facultynames.get(spinner_faculty.getSelectedItemPosition()) + "Year_" + spinner_years.getSelectedItemPosition() + departments_letter.get(spinner_department.getSelectedItemPosition()) + "Section_" + spinner_sction.getSelectedItemPosition();
        new JsonUtils().execute();
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {

                    if (arg0.equalsIgnoreCase("softizone.000webhostapp.com")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void No_Connection() {

        final AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setIcon(R.drawable.no_internet);
        alertdialog.setMessage("No Internet Connection,Refresh and try again");
        alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertdialog.show();

    }



}
