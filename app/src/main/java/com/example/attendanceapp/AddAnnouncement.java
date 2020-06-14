package com.example.attendanceapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class AddAnnouncement extends AppCompatActivity {
    public EditText subject, content;
    Button Add;
    FirebaseDatabase root;
    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addannouncement);
        subject = findViewById(R.id.AddSubject);
        content = findViewById(R.id.AddContent);
        root = FirebaseDatabase.getInstance();
        mDatabase = root.getReference();
        final Timestamp New = new Timestamp(System.currentTimeMillis());



        Add = findViewById(R.id.AddAnnouncement);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth= FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference("Announcements");

                String Subject = subject.getText().toString().trim();
                String Content = content.getText().toString().trim();
                String timestamp =New.toString();
                Ann ann = new Ann(Subject, Content, timestamp);
                addNotification(Subject);
                mDatabase.push().setValue(ann);

                Intent goToAnnouncement;
                goToAnnouncement = new Intent(AddAnnouncement.this, Announcements.class);
                startActivity(goToAnnouncement);

            }

            private void addNotification(String Subject) {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(AddAnnouncement.this)
                                .setSmallIcon(R.drawable.vesitlogo)
                                .setContentTitle("New Announcement")
                                .setContentText(Subject);

                Intent notificationIntent = new Intent(AddAnnouncement.this, Announcements.class);
                PendingIntent contentIntent = PendingIntent.getActivity(AddAnnouncement.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
            }
        });

    }
}
