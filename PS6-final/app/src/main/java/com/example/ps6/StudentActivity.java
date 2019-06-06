package com.example.ps6;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.InputQueue;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.StudentAdapter;
import model.user;

public class StudentActivity extends AppCompatActivity {

    private ArrayList<user> studentItem;
    private String URL;
    private Button bouton;
    private EditText numetu;
    private int num;
    private TextView text;
    private Boolean InQueue;
    private int time;
    final Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        numetu = findViewById(R.id.checknumetu)  ;
        bouton = findViewById(R.id.checkNumButton);
        text = findViewById(R.id.TextView_Check);
        InQueue = true;
        URL = "http://192.168.43.91:9428/api/students";
        studentItem = new ArrayList<>();

        getStudents();

      bouton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              handler.postDelayed(new Runnable(){
                  @Override
                  public void run(){
                      getStudents();
                      num = Integer.parseInt(numetu.getText().toString());

                      //if (studentItem.size()==0) {text.setText("Vous n'êtes pas inscrits");}
                      for (int i = 0; i < studentItem.size(); i++) {


                          if ((studentItem.get(i).getStudentNumber()) == (num)) {
                              time = 5*((studentItem.get(i).getQueueNumber()-1));
                              if (studentItem.get(i).getQueueNumber() != 1) {text.setText("Bonjour " +studentItem.get(i).getFirstName() +" "+studentItem.get(i).getName()+",\n" +"Votre position dans la liste d'attente "+studentItem.get(i).getEducationStream()+" est: " + studentItem.get(i).getQueueNumber()
                                      +"\nTemps d'attente estimé: "+ time+ " min");
                              } else {text.setText("Bonjour " +studentItem.get(i).getFirstName() +" "+studentItem.get(i).getName()+",\n"+"C'est votre tour, veuillez vous présenter au bureau.");
                                    }
                          }//else { text.setText("Vous n'êtes pas inscrits");}
                      }
                      handler.postDelayed(this, 1000);

                  }
              }, 1);
              //while (InQueue)  {
                  }


          //}
      });





    }
    private void getStudents() {

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
//                                studentItem.add(new user(student.getInt("queue"), student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
                                studentItem.add(new user(i+1, student.getInt("studentNumber"),student.getString("firstName"), student.getString("name"), student.getString("educationStream"), student.getLong("id")));
                            }
//


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





}
