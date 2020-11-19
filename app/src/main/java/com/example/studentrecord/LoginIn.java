package com.example.studentrecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginIn extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText email,name,password;
    private Spinner spinner,yearSpinner,batchSpinner;
    private Button login;
    private String selectedRole, selectedYear, selectedBatch;
    private LinearLayout teacherRole;
    private String TAG ="Error";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        spinner = findViewById(R.id.roleSpinner);
        name = findViewById(R.id.name);
        yearSpinner = findViewById(R.id.yearSpinner);
        batchSpinner = findViewById(R.id.batchSpinner);
        teacherRole = findViewById(R.id.teacherRole);

        mAuth = FirebaseAuth.getInstance();
        roleSelector();
        yearSelector();
        batchSelector();

        spinner.setOnItemSelectedListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (selectedRole.isEmpty()) {
                        Toast.makeText(LoginIn.this,"Please select your role",Toast.LENGTH_LONG).show();
                    }else{
                        if(selectedRole.equals("Teacher")){
                            signUp();

                        }else{
                            Intent intent = new Intent(LoginIn.this,StudentActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }catch (Exception e){
                    Log.d(TAG, "onClick: "+e.getMessage());
                }
            }
        });

    }

    void navigateToTeacherActivity(){
        Intent intent = new Intent(LoginIn.this,TeacherActivity.class);
        intent.putExtra("Selected role",selectedRole);
        intent.putExtra("Selected year",selectedYear);
        intent.putExtra("Selected batch",selectedBatch);
        startActivity(intent);
        finish();
    }

    private void signUp(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    storeValues(user);

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString()).build();
                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                navigateToTeacherActivity();
                            }
                        }
                    });
                }
            }
        });
    }

    private void storeValues(FirebaseUser user){
        if(user!=null){

        }
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

    private void roleSelector(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.role));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.roleSpinner) {
            selectedRole = adapterView.getSelectedItem().toString();
            if(selectedRole.equals("Teacher")){
                teacherRole.setVisibility(View.VISIBLE);
            }else if(selectedRole.equals("Student")){
                teacherRole.setVisibility(View.INVISIBLE);
            }
        }
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
}