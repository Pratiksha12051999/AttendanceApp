package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.GridView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tabs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    Button studentAttendance;
    Button grievance;

    public void showAttributions(View view) { startActivity(new Intent(this, Attributions.class)); }

    public void showResults(View view) {
        startActivity(new Intent(this, Semesters.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        GridLayout mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        mainGrid.setTranslationY(10000);
        mainGrid.animate().translationYBy(-10000).setDuration(1000);


        announcementButton = findViewById(R.id.cell5);;
        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAnnouncement;
                goToAnnouncement = new Intent(Tabs.this, Announcements.class);
                startActivity(goToAnnouncement);
            }
        });

        examButton = findViewById(R.id.cell6);;
        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToExam;
                goToExam = new Intent(Tabs.this, Exam.class);
                startActivity(goToExam);
            }
        });
        assignButton = findViewById(R.id.cell2);;
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAssign;
                goToAssign = new Intent(Tabs.this, Assignment_yearbutton.class);
                startActivity(goToAssign);
            }
        });
        eventButton = findViewById(R.id.cell4);;
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToEvent;
                goToEvent = new Intent(Tabs.this, Events_show_cards_on_recycler.class);
                startActivity(goToEvent);
            }
        });
//        assignButton = findViewById(R.id.cell2_);;
//        assignButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToAssign;
//                goToAssign = new Intent(Tabs.this, Assignment_Maindisplay_page.class);
//                startActivity(goToAssign);
//            }
//        });

//        assignButton = findViewById(R.id.cell2_);;
//        assignButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToAssign;
//                goToAssign = new Intent(Tabs.this, ShowAssignment.class);
//                startActivity(goToAssign);
//            }
//        });
		studentAttendance = findViewById(R.id.studentAttendance);

        grievance = findViewById(R.id.grievance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        studentAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance = new Intent(Tabs.this, StudentAttendance.class);
                startActivity(attendance);
            }
        });

        grievance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent grievancePage = new Intent(Tabs.this, AddGrievance.class);
                startActivity(grievancePage);

            }});

        FirebaseUser current=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbUsers= FirebaseDatabase.getInstance().getReference();
        dbUsers.child("Users").child(current.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserDetails details=dataSnapshot.getValue(UserDetails.class);
                    TextView studentName=(TextView)findViewById(R.id.tvName);
                    studentName.setText(details.email.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        super.onBackPressed();

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
                Intent goBackToLogin = new Intent(Tabs.this, MainActivity.class);
                startActivity(goBackToLogin);
        }
        return true;
    }
}
