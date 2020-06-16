package com.example.attendanceapp;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Assignment_Maindisplay_page extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Assignment_Model> models ;
    Integer[] colors= null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_assignment);

        models = new ArrayList<>();
        models.add(new Assignment_Model(R.drawable.maths, "Maths", ""));
        models.add(new Assignment_Model(R.drawable.physics, "Physics", ""));
        models.add(new Assignment_Model(R.drawable.chemistry, "Chemistry", ""));
        models.add(new Assignment_Model(R.drawable.mechanics, "Mechanics", ""));
        adapter = new Adapter(models, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new Adapter(models, this);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
        };
        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount() -1 ) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }
                else{
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
