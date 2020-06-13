package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class AddAnnouncement extends AppCompatActivity {
    String Subject;
    String Content;
    EditText subject;
    EditText content;
    Button Add;
    FirebaseDatabase root;
    DatabaseReference mDatabase;
    String Timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addannouncement);
        subject = findViewById(R.id.AddSubject);
        content = findViewById(R.id.AddContent);
        root = FirebaseDatabase.getInstance();
        mDatabase = root.getReference().child("Announcements");
        Timestamp New = new Timestamp(System.currentTimeMillis());
        Timestamp =New.toString();


        Add = findViewById(R.id.AddAnnouncement);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject = subject.toString();
                Content = content.toString();
                Ann ann = new Ann(Subject, Content, Timestamp);
                mDatabase.child(Timestamp).setValue(ann);
                /*
                Intent goToAnnouncement;
                goToAnnouncement = new Intent(AddAnnouncement.this, Announcements.class);
                startActivity(goToAnnouncement);
                */
            }
        });
    }
}
