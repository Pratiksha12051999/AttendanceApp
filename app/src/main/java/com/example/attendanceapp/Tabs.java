package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class Tabs extends AppCompatActivity {
    Button announcementButton, examButton,assignButton;
    public void showAttributions(View view){
        Intent attris=new Intent(this, Attributions.class);
        startActivity(attris);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        GridLayout mainGrid=(GridLayout)findViewById(R.id.mainGrid);
        mainGrid.setTranslationY(10000);
        mainGrid.animate().translationYBy(-10000).setDuration(1000);

        announcementButton = findViewById(R.id.cell5_);;
        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAnnouncement;
                goToAnnouncement = new Intent(Tabs.this, Announcements.class);
                startActivity(goToAnnouncement);
            }
        });

        examButton = findViewById(R.id.cell6_);;
        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToExam;
                goToExam = new Intent(Tabs.this, Exam.class);
                startActivity(goToExam);
            }
        });

        assignButton = findViewById(R.id.cell2_);;
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAssign;
                goToAssign = new Intent(Tabs.this, ShowAssignment.class);
                startActivity(goToAssign);
            }
        });
    }

}
