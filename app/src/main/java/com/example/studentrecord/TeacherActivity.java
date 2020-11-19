package com.example.studentrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

public class TeacherActivity extends AppCompatActivity {

    private String selectedRole, selectedYear, selectedBatch;
    private String TAG = "selected";

    @Override
    protected void onStart() {
        super.onStart();
        selectedRole = getIntent().getStringExtra("Selected role");
        selectedYear = getIntent().getStringExtra("Selected year");
        selectedBatch = getIntent().getStringExtra("Selected batch");

        Log.d(TAG, "onStart: "+selectedBatch+selectedYear+selectedRole);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
    }


}