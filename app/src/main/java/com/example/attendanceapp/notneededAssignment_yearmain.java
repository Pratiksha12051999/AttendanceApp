package com.example.attendanceapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class notneededAssignment_yearmain extends AppCompatActivity {

    private RecyclerView recyclerview_year;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<notneededAssignment_yearwise> yearList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_assignment);
        yearList = new ArrayList<>();
        yearList.add(new notneededAssignment_yearwise(R.drawable.vesitlogo, "1st YEAR","SEMESTER1", "SEMESTER2"));
        yearList.add(new notneededAssignment_yearwise(R.drawable.vesitlogo, "2nd YEAR","SEMESTER3", "SEMESTER4"));
        yearList.add(new notneededAssignment_yearwise(R.drawable.vesitlogo, "3rd YEAR","SEMESTER5", "SEMESTER6"));
        yearList.add(new notneededAssignment_yearwise(R.drawable.vesitlogo, "4th YEAR","SEMESTER7", "SEMESTER8"));
        adapter = new notneededAssignment_year_adapter(yearList);
        recyclerview_year = (RecyclerView) findViewById(R.id.recyclerview_year);
        //recyclerview_year.setHasFixedSize(true);
        recyclerview_year.setLayoutManager( new LinearLayoutManager(this));

        recyclerview_year.setAdapter(adapter);
    }
}

