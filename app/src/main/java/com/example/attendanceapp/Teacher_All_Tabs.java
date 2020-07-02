package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Teacher_All_Tabs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    Button showAttendance;
    TextView iconAttributions;

    public void showResults(View view) {
        startActivity(new Intent(this, UploadResults.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__all__tabs);
        GridLayout mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        mainGrid.setTranslationY(10000);
        mainGrid.animate().translationYBy(-10000).setDuration(1000);
        showAttendance = findViewById(R.id.showAttendance);
        iconAttributions = findViewById(R.id.textIconAttributions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAttendancePage = new Intent(Teacher_All_Tabs.this, TeacherAttendance.class);
                startActivity(goToAttendancePage);
            }
        });

        iconAttributions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attris = new Intent(Teacher_All_Tabs.this, Attributions.class);
                startActivity(attris);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.nav_announcement:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_contact:
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent goBackToLogin = new Intent(Teacher_All_Tabs.this, TeacherActivity.class);
                startActivity(goBackToLogin);
        }
        return true;
    }
}