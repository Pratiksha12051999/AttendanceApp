package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Assignment_yearbutton extends AppCompatActivity implements View.OnClickListener{
    Button sem1, sem2, sem3,sem4,sem5,sem6,sem7,sem8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        sem1 = findViewById(R.id.sem1);
        sem2 = findViewById(R.id.sem2);
        sem3 = findViewById(R.id.sem3);
        sem4 = findViewById(R.id.sem4);
        sem5 = findViewById(R.id.sem5);
        sem6 = findViewById(R.id.sem6);
        sem7 = findViewById(R.id.sem7);
        sem8 = findViewById(R.id.sem8);

        sem1.setOnClickListener(this);
        sem2.setOnClickListener(this);
        sem3.setOnClickListener(this);
        sem4.setOnClickListener(this);
        sem5.setOnClickListener(this);
        sem6.setOnClickListener(this);
        sem7.setOnClickListener(this);
        sem8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sem1:
                Toast.makeText(this,"Semester 1",Toast.LENGTH_SHORT).show();
                openSem1pg();
                break;
            case R.id.sem2:
                Toast.makeText(this,"Semester 2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem3:
                Toast.makeText(this,"Semester 3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem4:
                Toast.makeText(this,"Semester 4",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem5:
                Toast.makeText(this,"Semester 5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem6:
                Toast.makeText(this,"Semester 6",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem7:
                Toast.makeText(this,"Semester 7",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sem8:
                Toast.makeText(this,"Semester 8",Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void openSem1pg(){
        Intent sem1 = new Intent(this, Assignment_subject_display_page.class);
        startActivity(sem1);
    }
}
