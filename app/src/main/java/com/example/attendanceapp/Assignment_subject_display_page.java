package com.example.attendanceapp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class Assignment_subject_display_page extends AppCompatActivity {
    ViewPager viewPager;
    Adapter_Assignment adapter;
    List<Assignment_Model> models ;
    Integer[] colors= null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_assignment);

        models = new ArrayList<>();
        models.add(new Assignment_Model(R.drawable.vesitlogo, "Maths", "helllllllloooooooooooooooooooooooooo"));
        models.add(new Assignment_Model(R.drawable.contact, "Physics", "helllllllloooooooooooooooooooooooooo"));
        models.add(new Assignment_Model(R.drawable.discussion, "Chemistry", "helllllllloooooooooooooooooooooooooo"));
        models.add(new Assignment_Model(R.drawable.fees, "Fees", "helllllllloooooooooooooooooooooooooo"));
        adapter = new Adapter_Assignment(models, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new Adapter_Assignment(models, this);
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                    //                if(position < (adapter.getCount() -1 ) && position < (colors.length - 1)){

//                    viewPager.setBackgroundColor(
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                }
//                else{
//                    viewPager.setBackgroundColor(colors[colors.length - 1]);
//                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    openmaths();
                }
                if (position == 1){
//                    openphysics();
                }
                if (position == 2){
                    openchemistry();
                }
                if (position == 3){
                    openeng();
                }

            }
//            public void openphysics(){
//                Intent maths = new Intent(Assignment_subject_display_page.this,Assignment.class);
//                startActivity(maths);
//            }
        public void openeng(){
                Intent maths = new Intent(Assignment_subject_display_page.this,Assignment.class);
                startActivity(maths);
            }public void openchemistry(){
                Intent maths = new Intent(Assignment_subject_display_page.this,Assignment.class);
                startActivity(maths);
            }
            public void openmaths(){
                Intent maths = new Intent(Assignment_subject_display_page.this,ShowAssignment.class);
                startActivity(maths);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
