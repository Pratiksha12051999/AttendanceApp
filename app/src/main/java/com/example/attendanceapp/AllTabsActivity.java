package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AllTabsActivity extends AppCompatActivity {
    Button logoutButton;
    Button announcementButton = findViewById(R.id.button2);
    FirebaseAuth mAuth;

    public void showTabs(View view){
        Intent tabs=new Intent(this, Tabs.class);
        startActivity(tabs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tabs);
        logoutButton = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent goBackToLogin = new Intent(AllTabsActivity.this, MainActivity.class);
                startActivity(goBackToLogin);
            }
        });

        announcementButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent goToAnnouncement = new Intent(AllTabsActivity.this, Announcement.class);
                startActivity(goToAnnouncement);
            }
        });


    }
}
