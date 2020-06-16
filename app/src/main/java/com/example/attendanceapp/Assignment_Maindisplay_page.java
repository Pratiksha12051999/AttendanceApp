package com.example.attendanceapp;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Assignment_Maindisplay_page extends AppCompatActivity {
    ViewPager2 viewPager;
    Adapter adapter;
    List<Assignment_Model> models ;
    Integer[] colors= null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_assignment);

        models = new ArrayList<>();
        models.add(new Assignment_Model(R.drawable.vesitlogo, "VesitLogo", ""));
        models.add(new Assignment_Model(R.drawable.contact, "Contact", ""));
        models.add(new Assignment_Model(R.drawable.discussion, "Discussion", ""));
        models.add(new Assignment_Model(R.drawable.fees, "Fees", ""));
        adapter = new Adapter(models, this);
        viewPager = (ViewPager2) findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        adapter = new Adapter(models);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);



    }
}
