package com.example.attendanceapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Event_click_event_button extends AppCompatActivity {
    TextView EventName, Date, Fromtime, Totime, Dept, Description, Link;
    Button previous;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_event_button);

        EventName = (TextView) findViewById(R.id.EventName);
        Date = (TextView) findViewById(R.id.Date);
        Fromtime = (TextView) findViewById(R.id.Fromtime);
        Totime = (TextView) findViewById(R.id.Totime);
        Dept = (TextView) findViewById(R.id.Dept);
        Description = (TextView) findViewById(R.id.Description);
        Link = (TextView) findViewById(R.id.Link);
        previous = (Button) findViewById(R.id.previous);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child("android");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String eventName= dataSnapshot.child("EventName").getValue().toString();
                String date= dataSnapshot.child("Date").getValue().toString();
                String fromtime= dataSnapshot.child("Fromtime").getValue().toString();
                String totime= dataSnapshot.child("Totime").getValue().toString();
                String dept= dataSnapshot.child("Dept").getValue().toString();
                String description= dataSnapshot.child("Description").getValue().toString();
                String link= dataSnapshot.child("Link").getValue().toString();
                EventName.setText(eventName);
                Date.setText(date);
                Fromtime.setText(fromtime);
                Totime.setText(totime);
                Dept.setText(dept);
                Description.setText(description);
                Link.setText(link);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
