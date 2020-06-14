package com.example.attendanceapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Exam extends AppCompatActivity {
    public EditText FirstName, MiddleName, LastName, emailid, Address, Contact, DOB;
    Button Submit;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        FirstName = findViewById(R.id.FirstName);
        MiddleName = findViewById(R.id.MiddleName);
        LastName = findViewById(R.id.LastName);
        emailid = findViewById(R.id.emailid);
        Address = findViewById(R.id.Address);
        Contact = findViewById(R.id.Contact);
        DOB = findViewById(R.id.DOB);
        Submit = findViewById(R.id.Submit);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reference = mFirebaseDatabase.getReference();

//        Pushing data into firebase
        Submit.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view){
              firebaseAuth= FirebaseAuth.getInstance();
              reference = FirebaseDatabase.getInstance().getReference("Exam");
            //Get all the values
              reference.setValue("Form submitted");
              String firstName = FirstName.getText().toString().trim();
              String lastName = LastName.getText().toString().trim();
              String middleName = MiddleName.getText().toString().trim();
              String email = emailid.getText().toString().trim();
              String address = Address.getText().toString().trim();
              String contact = Contact.getText().toString().trim();
              String dob= DOB.getText().toString().trim();
              ExamHelper examhepler = new ExamHelper();
              reference.child(contact).child("First Name").setValue(firstName);
              reference.child(contact).child("Last Name").setValue(lastName);
              reference.child(contact).child("Middle Name").setValue(middleName);
              reference.child(contact).child("Address").setValue(address);
              reference.child(contact).child("Email").setValue(email);
              reference.child(contact).child("Date of Birth").setValue(dob);
          }
          });

    }
}
