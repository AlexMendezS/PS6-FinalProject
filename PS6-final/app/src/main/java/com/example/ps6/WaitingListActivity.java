package com.example.ps6;

import android.os.Handler;
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
import adapter.StudentAdapter;
import model.user;

public class WaitingListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String URLGET;
    private String URLDELETE;
    private ArrayList<user> studentItem;
    private ListView mylistView;
    ArrayList<user> studentItemFiltered;
    TextView mTextViewStudent;
    final Handler handler = new Handler();


    private Spinner spinner;
    private String filiere;
    private long IDtoDelete;

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


        URLGET = "http://192.168.43.91:9428/api/students";
        URLDELETE = URLGET + "/" + Long.toString(IDtoDelete);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadQueue();
            }
        });

        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                getStudents();
                handler.postDelayed(this, 5000);

            }
        }, 2000);

    }

    private void uploadQueue() {
//        Log.e("test", "" + IDtoDelete);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                URLGET + "/" + Long.toString(IDtoDelete),
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

        ArrayList<user> studentsToUpdate = filterStudent(filiere);
        for(int i=0; i< studentsToUpdate.size(); i++) {
            user studentToUpdate = studentsToUpdate.get(i);
            studentToUpdate.setQueueNumber(studentToUpdate.getQueueNumber()-1);
            updateStudent(studentToUpdate);
        }
        getStudents();
    }

    private void getStudents() {
        mTextViewStudent.setText("");
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

                                //add a new student to list
//                                studentItem.add(new user(student.getInt("queue"), student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
                                studentItem.add(new user(i+1, student.getInt("studentNumber"),student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
                            }
//                            user finalStudent = studentItem.get(studentItem.size()-1);
//                            finalStudent.setQueueNumber(finalStudent.getQueueNumber()-1);
//                            updateStudent(finalStudent);

                            //set the adapter
                            StudentAdapter myadapter = new StudentAdapter(WaitingListActivity.this, R.layout.student, filterStudent(filiere));
                            mylistView.setAdapter(myadapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response.toString());
                        //Toast.makeText(WaitingListActivity.this, "Liste d'attente actualisée", Toast.LENGTH_SHORT).show();
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


    private void updateStudent(user student){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject putparams = new JSONObject();
        try {
            putparams.put("firstName", student.getFirstName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            putparams.put("educationStream", student.getEducationStream());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            putparams.put("name", student.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            putparams.put("studentNumber",  student.getStudentNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }try {
            putparams.put("queue", student.getQueueNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                URLGET + "/" + Long.toString(student.getId()), putparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
//                        Toast.makeText(WaitingListActivity.this, "Position dans la file d'attente : "+position, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjReq);
    }


    private ArrayList<user> filterStudent(String filiere) {
        ArrayList<user> studentItemFiltered = new ArrayList<>();
        if (filiere.equals("ALL")) {
            studentItemFiltered = studentItem;
            if (studentItemFiltered.size() != 0) {
                mTextViewStudent.append("Etudiant en cours de traitement: \n" + studentItemFiltered.get(0).getFirstName() + " " + studentItemFiltered.get(0).getName());
                IDtoDelete = studentItemFiltered.get(0).getId();
            }
            else{IDtoDelete = -1;

                mTextViewStudent.append("Aucun étudiant en cours de traiement ");}

            return studentItem;
        }
        for (int i = 0; i < studentItem.size(); i++) {
            if (studentItem.get(i).getEducationStream().equals(filiere)) {
                studentItemFiltered.add(studentItem.get(i));
            }

        }
        for (int i = 0; i < studentItemFiltered.size(); i++){
            studentItemFiltered.get(i).setQueueNumber(i+1);
        }


        if (studentItemFiltered.size() != 0) {
            IDtoDelete = studentItemFiltered.get(0).getId();

            mTextViewStudent.append("Etudiant en cours de traitement: \n" + studentItemFiltered.get(0).getFirstName() + " " + studentItemFiltered.get(0).getName());
        }
        else  {
            mTextViewStudent.append("Aucun étudiant en cours de traiement ");
        }

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