package com.example.ps6;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.StudentAdapter;
import model.user;

public class WaitingListActivity extends AppCompatActivity {

    //private RequestQueue requestQueue;
    private String URL;
    private ArrayList<user> studentItem;
    private ListView mylistView;
    private int[] studentNumbers;
    private String[] studentFirstNames;
    private String[] studentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        studentItem = new ArrayList<>();
        setContentView(R.layout.activity_waitinglist);
        Button nextbutton = findViewById(R.id.nextButton);
        Button uploadbutton = findViewById(R.id.uploadButton);
        TextView mTextViewStudent = findViewById(R.id.tv_waitingliste);
        mylistView = findViewById(R.id.waiting_list);
        URL = "http://10.212.118.135:9428/api/students";

        //    requestQueue = Volley.newRequestQueue(this);
        getStudents();

        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStudents();
            }
        });

//        nextbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadStudents();
//            }
//        });

    }

    private void getStudents() {
        studentItem.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
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
                                studentItem.add(new user(student.getInt("queue"), student.getString("firstName"), student.getString("name"), student.getString("educationStream")));
                            }


                            //filter

                            //set the adapter

                            StudentAdapter myadapter = new StudentAdapter(WaitingListActivity.this, R.layout.student, filterStudent("GE"));
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
        for (int i = 0; i < studentItem.size(); i++) {
            if (studentItem.get(i).getEducationStream().equals(filiere+"3") || studentItem.get(i).getEducationStream().equals(filiere+"4")) {
                studentItemFiltered.add(studentItem.get(i));
            }

        }
        return studentItemFiltered;

    }


}
