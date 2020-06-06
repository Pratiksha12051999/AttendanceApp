package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Teacher_All_Tabs extends AppCompatActivity {
    Button attendanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__all__tabs);
        attendanceButton = findViewById(R.id.attendance);
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAttendancePage = new Intent(Teacher_All_Tabs.this, TeacherAttendance.class);
                startActivity(goToAttendancePage);
            }
        });
    }
}