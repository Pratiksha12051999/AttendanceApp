package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tabs extends AppCompatActivity {

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
    }
}