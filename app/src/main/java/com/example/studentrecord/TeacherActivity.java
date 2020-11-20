package com.example.studentrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AddSubjectDialog.addDialogListener {

    private String selectedRole, selectedYear, selectedBatch;
    private String TAG = "selected";
    private Spinner yearSpinner,batchSpinner;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        setTitle("Teacher's Record");

        yearSpinner = findViewById(R.id.yearSpinner);
        batchSpinner = findViewById(R.id.batchSpinner);
        final AddSubjectDialog addSubjectDialog = new AddSubjectDialog();

        yearSelector();
        batchSelector();

        yearSpinner.setOnItemSelectedListener(this);

        FloatingActionButton addbtn = findViewById(R.id.addButton);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "ADD", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                addSubjectDialog.show(getSupportFragmentManager(),"Add Dialog");
            }
        });
    }


    private void batchSelector() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.batch));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchSpinner.setAdapter(adapter);
    }

    private void yearSelector() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.year));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.yearSpinner){
            selectedYear = adapterView.getSelectedItem().toString();
        }
        if(adapterView.getId() == R.id.batchSpinner){
            selectedBatch = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void valesFromEditText(String subject, String time) {

        Map<String, Object> city = new HashMap<>();
        city.put("name", "Los Angeles");
        city.put("state", "CA");
        city.put("country", "USA");

        db.collection("subject").document(user.getUid()).set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TeacherActivity.this, "Succes",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}