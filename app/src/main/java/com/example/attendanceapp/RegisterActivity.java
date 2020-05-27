package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText emailBoxRegister;
    EditText admissionNoRegister;
    EditText passwordFieldRegister;
    Button registerButton;
    TextView alreadyRegisteredTextView;
    FirebaseAuth mFirebaseAuth;
    CheckBox showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailBoxRegister = findViewById(R.id.emailTeacherRegister);
        admissionNoRegister = findViewById(R.id.admissionNoRegister);
        passwordFieldRegister = findViewById(R.id.passwordTeacherRegister);
        registerButton = findViewById(R.id.registerTeacherButton);
        alreadyRegisteredTextView = findViewById(R.id.alreadyRegisteredTextView);
        showPassword = findViewById(R.id.showPassword);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(isChecked){
                                                            passwordFieldRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                        }
                                                        else
                                                        {
                                                            passwordFieldRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                        }
                                                    }
                                                });

                alreadyRegisteredTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(LoginIntent);
                    }
                });

        if(mFirebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailBoxRegister.getText().toString().trim();
                final String admissionno = admissionNoRegister.getText().toString().trim();
                final String pwd = passwordFieldRegister.getText().toString().trim();
                if(email.isEmpty()){
                    emailBoxRegister.setError("Please enter an Email");
                    emailBoxRegister.requestFocus();        }
                else if(admissionno.isEmpty())
                {
                    admissionNoRegister.setError("Please enter admission no");
                    admissionNoRegister.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    passwordFieldRegister.setError("Please enter a password");
                    passwordFieldRegister.requestFocus();
                }
                else if(pwd.length() <=6 ){
                    passwordFieldRegister.setError("Password should be greater than 6 characters");
                    passwordFieldRegister.requestFocus();
                }
                else if(!(email.isEmpty()) && !(pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if(task.isSuccessful()){
                                User user = new User(
                                  email,
                                  admissionno,
                                  pwd
                                );
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "User Registeration unsuccessful", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Intent goToLogin = new Intent(RegisterActivity.this, AllTabsActivity.class);
                                startActivity(goToLogin);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Error during Registration", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}
