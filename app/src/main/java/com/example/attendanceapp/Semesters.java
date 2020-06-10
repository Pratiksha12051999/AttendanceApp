package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Semesters extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semesters);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.widget.GridLayout gridLayout=(GridLayout)findViewById(R.id.grid);

        int childCount = gridLayout.getChildCount();

        for (int i= 0; i < childCount; i++){
            CardView container = (androidx.cardview.widget.CardView) gridLayout.getChildAt(i);
            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    switch(view.getId())
                    {
                        case R.id.cardSem1: break;
                        case R.id.cardSem2: break;
                        case R.id.cardSem3: break;
                        case R.id.cardSem4: break;
                        case R.id.cardSem5: startActivity(new Intent(Semesters.this, Sem5.class));
                            break;
                        case R.id.cardSem6: startActivity(new Intent(Semesters.this, Sem6.class));
                            break;
                        case R.id.cardSem7: startActivity(new Intent(Semesters.this, Sem7.class));
                            break;
                        case R.id.cardSem8: startActivity(new Intent(Semesters.this, Sem8.class));
                            break;
                        default: break;
                    }
                }
            });
        }

        /*gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.cardSem1: break;
                    case R.id.cardSem2: break;
                    case R.id.cardSem3: break;
                    case R.id.cardSem4: break;
                    case R.id.cardSem5: startActivity(new Intent(Semesters.this, Sem5.class));
                                        break;
                    case R.id.cardSem6: startActivity(new Intent(Semesters.this, Sem6.class));
                                        break;
                    case R.id.cardSem7: startActivity(new Intent(Semesters.this, Sem7.class));
                                        break;
                    case R.id.cardSem8: startActivity(new Intent(Semesters.this, Sem8.class));
                                        break;
                    default: break;
                }
            }
        });*/
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
                Intent goBackToLogin = new Intent(Semesters.this, MainActivity.class);
                startActivity(goBackToLogin);
        }
        return true;
    }
}