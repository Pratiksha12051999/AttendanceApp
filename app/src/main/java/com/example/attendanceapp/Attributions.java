package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Attributions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributions);
        ArrayList<String> attri=new ArrayList<String>(Arrays.asList("Results, Events, Announcements, Grievance, Contact Us - Icon made by Freepik from www.flaticon.com",
                "Assignments - Icon made by Good Ware from www.flaticon.com",
                "Attendance - Icon made by Flat Icons from www.flaticon.com",
                "Fee Details - Icon made by Kiranshastry from www.flaticon.com",
                "Discussion Forum - Icon made by SmashIcons from www.flaticon.com"));
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, attri);
        ListView listAttributions=(ListView)findViewById(R.id.listAttributions);
        listAttributions.setAdapter(arrayAdapter);
    }
}