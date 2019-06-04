package com.example.ps6;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import adapter.StudentAdapter;
import model.user;

public class WaitingListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String URLGET;
    private String URL;
    private String URLDELETE;
    private ArrayList<user> studentItem;
    private ListView mylistView;
    ArrayList<user> studentItemFiltered;
    TextView mTextViewStudent;

    private Spinner spinner;
    private String filiere;

    private int[] studentNumbers;
    private String[] studentFirstNames;
    private String[] studentNames;
    private long IDtoDelete;
    private int test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        studentItem = new ArrayList<>();
        setContentView(R.layout.activity_waitinglist);
        Button nextbutton = findViewById(R.id.nextButton);
        mylistView = findViewById(R.id.waiting_list);
        mTextViewStudent = findViewById(R.id.tv_student);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filiereBRI, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        URL = "http://10.212.118.37:9428/api/students";
        URLGET = "http://10.212.118.37:9428/api/students";
        URLDELETE = URLGET + "/" + Long.toString(IDtoDelete);
        //getStudents();



        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getStudents();
                uploadQueue();
            }
        });

    }

    private void uploadQueue() {
        //getStudents();
        Log.e("test", "" + IDtoDelete);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                URLGET +"/"+ Long.toString(IDtoDelete),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      //  Toast.makeText(WaitingListActivity.this, "étudiant supprimé", Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(arrayRequest);
        mTextViewStudent.setText("");
        getStudents();


    }

    private void getStudents() {
        studentItem.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URLGET,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject student = response.getJSONObject(i);

//                                studentNumbers[i] = student.getInt("studentNumber");
//                                studentFirstNames[i] = student.getString("firstName");
//                                studentNames[i] = student.getString("name");

                                //add a new student to list
//                                studentItem.add(new user(studentNumbers[i], studentFirstNames[i], studentNames[i]));
                                studentItem.add(new user(student.getInt("queue"), student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
//                                Collections.reverse(studentItem);
//                                test= studentItem.get(0).getName();
                            }


                            //filter

//                            test = studentItem.get(0).getQueueNumber();


                            //set the adapter
                            mTextViewStudent.append("Etudiant en cours de traitement: \n"+studentItem.get(0).getFirstName()+" "+studentItem.get(0).getName());
                            StudentAdapter myadapter = new StudentAdapter(WaitingListActivity.this, R.layout.student, filterStudent(filiere));
                            mylistView.setAdapter(myadapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response.toString());
                        Toast.makeText(WaitingListActivity.this, "Liste d'attente actualisée", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(arrayRequest);


    }

    private ArrayList<user> filterStudent(String filiere) {
        ArrayList<user> studentItemFiltered = new ArrayList<>();
        if(filiere.equals("ALL")){
            studentItemFiltered = studentItem;

            IDtoDelete = studentItemFiltered.get(0).getId();
            return studentItem;}
        for (int i = 0; i < studentItem.size(); i++) {
            if (studentItem.get(i).getEducationStream().equals(filiere)) {
                studentItemFiltered.add(studentItem.get(i));
            }

        }

        IDtoDelete = studentItemFiltered.get(0).getId();
        return studentItemFiltered;



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        filiere = text;
        getStudents();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
