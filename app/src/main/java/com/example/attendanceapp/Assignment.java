package com.example.attendanceapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
    EditText Assno, Startdate, Enddate, Question;
    Button AddBtn;
    RecyclerView Recyclerview;

    //Array list
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        Assno = findViewById(R.id.Assno);
        Startdate = findViewById(R.id.Startdate);
        Enddate = findViewById(R.id.Enddate);
        AddBtn = findViewById(R.id.AddBtn);
        Question = findViewById(R.id.Question);
        Recyclerview =findViewById(R.id.Recyclerview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        Recyclerview.setAdapter(adapter);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mDatabase.push().child(Assno.getText().toString());
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        firebaseAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Assignment");


        mDatabase.addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded( DataSnapshot dataSnapshot,  String s) {
                                                String string =dataSnapshot.getValue(String.class);
                                                arrayList.add(string);
                                                adapter.notifyDataSetChanged();

                                            }

                                            @Override
                                            public void onChildChanged( DataSnapshot dataSnapshot,  String s) {

                                            }

                                            @Override
                                            public void onChildRemoved( DataSnapshot dataSnapshot) {

                                            }

                                            @Override
                                            public void onChildMoved( DataSnapshot dataSnapshot,  String s) {

                                            }

                                            @Override
                                            public void onCancelled( DatabaseError databaseError) {

                                            }
                                        }
        );

    }


}
