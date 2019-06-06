package com.example.ps6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CheckActivity extends AppCompatActivity {


    private EditText notif;

    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        checkButton = findViewById(R.id.checkNumButton);
    }
}
