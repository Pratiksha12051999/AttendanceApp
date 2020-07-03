package com.example.notificationdemo;

import android.os.Bundle;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceapp.R;

public class NotificationView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}