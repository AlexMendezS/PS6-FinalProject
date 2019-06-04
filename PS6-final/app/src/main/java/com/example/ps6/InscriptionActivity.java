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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name;
    private EditText firstname;
    private EditText studentNumber;
    private String filiere;
    private Spinner spinner;
    private Button inscriptionButton;
    private RequestQueue requestQueue;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        inscriptionButton = findViewById(R.id.inscriptionButton);
        name = findViewById(R.id.textnewname);
        studentNumber = findViewById(R.id.textnumetu);
        firstname = findViewById(R.id.textfirstname);
        URL = "http://10.212.115.202:9428/api/students";

        requestQueue = Volley.newRequestQueue(this);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filiere, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("firstName", firstname.getText().toString());
                    postparams.put("queue", -2);
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
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        URL, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                Toast.makeText(InscriptionActivity.this, "Etudiant ajouté à la liste d'attente", Toast.LENGTH_SHORT).show();
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


                Intent newIntent = new Intent(InscriptionActivity.this, StudentActivity.class);
                startActivity(newIntent);

            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        filiere = text;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
