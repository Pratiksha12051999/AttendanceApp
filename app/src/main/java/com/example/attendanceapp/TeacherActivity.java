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

public class TeacherActivity extends AppCompatActivity {
    EditText emailTeacher;
    EditText passwordTeacher;
    TextView notRegisteredTeacher;
    Button loginTeacherButton;
    FirebaseAuth fAuth;
    CheckBox showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        fAuth = FirebaseAuth.getInstance();
        emailTeacher = findViewById(R.id.emailTeacherRegister);
        passwordTeacher = findViewById(R.id.passwordTeacherRegister);
        notRegisteredTeacher = findViewById(R.id.alreadyRegisteredTeacher);
        loginTeacherButton = findViewById(R.id.registerTeacherButton);
        showPassword = findViewById(R.id.showPassword3);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordTeacher.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    passwordTeacher.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        notRegisteredTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TeacherRegisterIntent = new Intent(TeacherActivity.this, TeacherRegister.class);
                startActivity(TeacherRegisterIntent);
            }
        });

        loginTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTeacher.getText().toString().trim();
                String pwd = passwordTeacher.getText().toString().trim();
                if (email.isEmpty()) {
                    emailTeacher.setError("Please enter an Email");
                    emailTeacher.requestFocus();
                } else if (pwd.isEmpty()) {
                    passwordTeacher.setError("Please enter a password");
                    passwordTeacher.requestFocus();
                } else if (!(email.isEmpty()) && !(pwd.isEmpty())) {
                    fAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(TeacherActivity.this, "Teacher Logged In", Toast.LENGTH_SHORT).show();
                                Intent goToAllTabs = new Intent(TeacherActivity.this, Teacher_All_Tabs.class);
                                startActivity(goToAllTabs);
                            }
                            else{
                                Toast.makeText(TeacherActivity.this, "Teacher Log In failed!", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(TeacherActivity.this, MainActivity.class));
        finish();
    }

}
