package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Sem5 extends AppCompatActivity {

    DatabaseReference dbResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem5);

        dbResults= FirebaseDatabase.getInstance().getReference("Users");
        dbResults.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    UserDetails details=dataSnapshot.getValue(UserDetails.class);
                    String admno=details.admissionno;
                    Toast.makeText(Sem5.this, admno, Toast.LENGTH_SHORT).show();
                    Query query=FirebaseDatabase.getInstance().getReference("1xdYDgDXnj1ZVODHEdcSAv51ex58GuGVPBrBTQVFOrUs/Sheet1").orderByChild("admissionno").equalTo(admno);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    Map<String, String> values=(Map<String, String>)issue.getValue();
                                    TextView MP1=(TextView)findViewById(R.id.textMP1);
                                    TextView MP2=(TextView)findViewById(R.id.textMP2);
                                    TextView MP3=(TextView)findViewById(R.id.textMP3);
                                    TextView MP4=(TextView)findViewById(R.id.textMP4);

                                    MP1.setText(values.get("MP IA"));
                                    MP2.setText(values.get("MP IA"));
                                    MP3.setText(values.get("MP IA"));
                                    MP4.setText(values.get("MP SE"));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}