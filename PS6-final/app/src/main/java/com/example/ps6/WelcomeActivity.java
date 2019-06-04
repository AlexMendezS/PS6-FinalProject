package com.example.ps6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button student;
    private Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        student = (Button) findViewById(R.id.StudentButton);
        admin = (Button) findViewById(R.id.AdminButton);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewInscriptionActivity = new Intent(getApplicationContext(),InscriptionActivity.class);
                startActivity(NewInscriptionActivity);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewAdminActivity = new Intent(getApplicationContext(), WaitingListActivity.class);
                startActivity(NewAdminActivity);
            }
        });
    }
}
