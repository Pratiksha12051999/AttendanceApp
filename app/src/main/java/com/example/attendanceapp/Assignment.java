package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Assignment extends AppCompatActivity {

    //Firebase variables
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Assignment");;
    FirebaseDatabase mFirebaseDatabase;

    //Android layout
    EditText assno, startdate, enddate, question;
    Button AddBtn;
    RecyclerView mList;


    //Array list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        assno = findViewById(R.id.Assno);
        startdate = findViewById(R.id.Startdate);
        enddate = findViewById(R.id.Enddate);
        AddBtn = findViewById(R.id.AddBtn);
        question = findViewById(R.id.Question);
        mList = findViewById(R.id.RecyclerView);

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String Startdate = startdate.getText().toString().trim();
                String Enddate = enddate.getText().toString().trim();
                String Question = question.getText().toString().trim();
                String Assno = assno.getText().toString().trim();
           //     AssignmentHelper assignment = new AssignmentHelper();
//                mDatabase.push().setValue(assignment);
                mDatabase.child(Assno).child("Startdate").setValue(Startdate);
                mDatabase.child(Assno).child("Enddate").setValue(Enddate);
                mDatabase.child(Assno).child("Question").setValue(Question);
                mDatabase.child(Assno).child("Assno").setValue(Assno);

                Intent Assignment = new Intent(Assignment.this,ShowAssignment.class);
                startActivity(Assignment);
            }

        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        firebaseAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Assignment");



    }


}
