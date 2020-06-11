package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Sem1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem1);

        FirebaseUser current= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbResults= FirebaseDatabase.getInstance().getReference();
        dbResults.child("Users").child(current.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    UserDetails details=dataSnapshot.getValue(UserDetails.class);
                    String aAP1no=details.admissionno.toString();
                    Query query=FirebaseDatabase.getInstance().getReference("1xdYDgDXnj1ZVODHEdcSAv51ex58GuGVPBrBTQVFOrUs/Sheet1").orderByChild("admissionno").equalTo(Integer.parseInt(aAP1no));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    Map<String, Object> values=(Map<String, Object>)issue.getValue();
                                    TextView AM11=(TextView)findViewById(R.id.textAM11);
                                    TextView AM12=(TextView)findViewById(R.id.textAM12);
                                    TextView AM13=(TextView)findViewById(R.id.textAM13);
                                    TextView AM14=(TextView)findViewById(R.id.textAM14);

                                    AM11.setText(values.get("AMI IA").toString());
                                    AM12.setText(values.get("AMI IA").toString());
                                    AM13.setText(values.get("AMI IA").toString());
                                    AM14.setText(values.get("AMI SE").toString());

                                    TextView AP11=(TextView)findViewById(R.id.textAP11);
                                    TextView AP12=(TextView)findViewById(R.id.textAP12);
                                    TextView AP13=(TextView)findViewById(R.id.textAP13);
                                    TextView AP14=(TextView)findViewById(R.id.textAP14);

                                    AP11.setText(values.get("API IA").toString());
                                    AP12.setText(values.get("API IA").toString());
                                    AP13.setText(values.get("API IA").toString());
                                    AP14.setText(values.get("API SE").toString());

                                    TextView AC11=(TextView)findViewById(R.id.textAC11);
                                    TextView AC12=(TextView)findViewById(R.id.textAC12);
                                    TextView AC13=(TextView)findViewById(R.id.textAC13);
                                    TextView AC14=(TextView)findViewById(R.id.textAC14);

                                    AC11.setText(values.get("ACI IA").toString());
                                    AC12.setText(values.get("ACI IA").toString());
                                    AC13.setText(values.get("ACI IA").toString());
                                    AC14.setText(values.get("ACI SE").toString());

                                    TextView EM1=(TextView)findViewById(R.id.textEM1);
                                    TextView EM2=(TextView)findViewById(R.id.textEM2);
                                    TextView EM3=(TextView)findViewById(R.id.textEM3);
                                    TextView EM4=(TextView)findViewById(R.id.textEM4);

                                    EM1.setText(values.get("EM IA").toString());
                                    EM2.setText(values.get("EM IA").toString());
                                    EM3.setText(values.get("EM IA").toString());
                                    EM4.setText(values.get("EM SE").toString());

                                    TextView BEE1=(TextView)findViewById(R.id.textBEE1);
                                    TextView BEE2=(TextView)findViewById(R.id.textBEE2);
                                    TextView BEE3=(TextView)findViewById(R.id.textBEE3);
                                    TextView BEE4=(TextView)findViewById(R.id.textBEE4);

                                    BEE1.setText(values.get("BEE IA").toString());
                                    BEE2.setText(values.get("BEE IA").toString());
                                    BEE3.setText(values.get("BEE IA").toString());
                                    BEE4.setText(values.get("BEE SE").toString());

                                    TextView EVS1=(TextView)findViewById(R.id.textEVS1);
                                    TextView EVS2=(TextView)findViewById(R.id.textEVS2);
                                    TextView EVS3=(TextView)findViewById(R.id.textEVS3);
                                    TextView EVS4=(TextView)findViewById(R.id.textEVS4);

                                    EVS1.setText(values.get("EVS IA").toString());
                                    EVS2.setText(values.get("EVS IA").toString());
                                    EVS3.setText(values.get("EVS IA").toString());
                                    EVS4.setText(values.get("EVS SE").toString());

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