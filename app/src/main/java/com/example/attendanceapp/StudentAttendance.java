package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class StudentAttendance extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentsAdapter adapter;
    private List<Student> studentList;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        studentList = new ArrayList<>();
        adapter = new StudentsAdapter(this, studentList);
        recyclerView.setAdapter(adapter);

        //1. SELECT * FROM Artists
        //dbArtists = FirebaseDatabase.getInstance().getReference("Attendance");
        //dbArtists.addListenerForSingleValueEvent(valueEventListener);

        Query query = FirebaseDatabase.getInstance().getReference("Attendance")
                .orderByChild("roll_No")
                .equalTo("52");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //studentList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                         Student student = snapshot.getValue(Student.class);
                         studentList.add(student);
                }
                adapter.notifyDataSetChanged();
            }
            adapter = new StudentsAdapter(StudentAttendance.this,studentList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(StudentAttendance.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
        }
    };
}
