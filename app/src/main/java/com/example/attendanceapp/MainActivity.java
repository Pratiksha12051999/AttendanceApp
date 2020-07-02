package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    EditText emailBox;
    EditText admissionNo;
    EditText passwordField;
    Button loginButton;
    TextView notRegistered;
    TextView teacherLogin;
    FirebaseAuth fAuth;
    CheckBox showPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.titleTextBox);
        admissionNo = findViewById(R.id.admissionNoRegister);
        passwordField = findViewById(R.id.passwordTeacherRegister);
        loginButton = findViewById(R.id.submitButton);
        notRegistered = findViewById(R.id.noRegistered);
        teacherLogin = findViewById(R.id.teacherLogin);
        showPassword = findViewById(R.id.showPassword2);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });

        teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TeacherIntent = new Intent(MainActivity.this, TeacherActivity.class);
                startActivity(TeacherIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailBox.getText().toString().trim();
                String admission = admissionNo.getText().toString().trim();
                String pwd = passwordField.getText().toString().trim();
                if(email.isEmpty()){
                    emailBox.setError("Please enter an Email");
                    emailBox.requestFocus();
                }
                else if(admission.isEmpty())
                {
                    admissionNo.setError("Please enter admission no");
                    admissionNo.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    passwordField.setError("Please enter a password");
                    passwordField.requestFocus();
                }
                else if(!(email.isEmpty()) && !(pwd.isEmpty())){
                    fAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
                                Intent goToAllTabs = new Intent(MainActivity.this, Tabs.class);
                                startActivity(goToAllTabs);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "User Log In failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
        finish();

    }
}
