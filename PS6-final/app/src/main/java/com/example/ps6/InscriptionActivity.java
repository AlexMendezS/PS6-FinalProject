package com.example.ps6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import model.user;

public class InscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name;
    private EditText firstname;
    private EditText studentNumber;
    private String filiere;
    private int position;
    private Spinner spinner;
    private Button inscriptionButton;
    private RequestQueue requestQueue;
    private String URL;

    private ArrayList<user> studentItem;
    ArrayList<user> studentItemFiltered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        inscriptionButton = findViewById(R.id.inscriptionButton);
        studentItem = new ArrayList<>();
        name = findViewById(R.id.textnewname);
        studentNumber = findViewById(R.id.textnumetu);
        firstname = findViewById(R.id.textfirstname);
        URL = "http://10.212.118.135:9428/api/students";

        requestQueue = Volley.newRequestQueue(this);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filiere, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getStudents();

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterStudent(filiere);
                position = studentItemFiltered.size();

                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("firstName", firstname.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    postparams.put("educationStream", filiere);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    postparams.put("name", name.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    int num = Integer.parseInt(studentNumber.getText().toString());

                    postparams.put("studentNumber",  num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }try {
                    position = position + 1;

                    postparams.put("queue",  position);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        URL, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                Toast.makeText(InscriptionActivity.this, "Position dans la file d'attente : "+position, Toast.LENGTH_LONG).show();
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

                Intent newIntent = new Intent(InscriptionActivity.this, WelcomeActivity.class);
                startActivity(newIntent);
            }
        });
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

                                //add a new student to list
                                studentItem.add(new user(student.getInt("queue"), student.getInt("studentNumber"), student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Response", response.toString());
                        Toast.makeText(InscriptionActivity.this, "Liste d'attente actualisée", Toast.LENGTH_SHORT).show();
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
        studentItemFiltered = new ArrayList<>();
        if(filiere.equals("ALL")){
            studentItemFiltered = studentItem;

            return studentItem;}
        for (int i = 0; i < studentItem.size(); i++) {
            if (studentItem.get(i).getEducationStream().equals(filiere)) {
                studentItemFiltered.add(studentItem.get(i));
            }
        }
        return studentItemFiltered;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filiere = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
