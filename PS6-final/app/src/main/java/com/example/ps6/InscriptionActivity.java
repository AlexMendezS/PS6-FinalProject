package com.example.ps6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InscriptionActivity extends AppCompatActivity {
    private EditText name;
    private EditText firstname;
    private Button inscriptionButton;
    private RequestQueue requestQueue;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        inscriptionButton = findViewById(R.id.inscriptionButton);
        name = findViewById(R.id.textnewname);
        firstname = findViewById(R.id.textfirstname);
        URL = "http://192.168.1.69:9428/api/students";

        requestQueue = Volley.newRequestQueue(this);

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("firstName", firstname.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    postparams.put("name", name.getText().toString());
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
}
