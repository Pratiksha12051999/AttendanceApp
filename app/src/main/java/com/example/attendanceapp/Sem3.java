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

public class Sem3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem3);

        FirebaseUser current= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbResults= FirebaseDatabase.getInstance().getReference();
        dbResults.child("Users").child(current.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    UserDetails details=dataSnapshot.getValue(UserDetails.class);
                    String admno=details.admissionno.toString();
                    Query query=FirebaseDatabase.getInstance().getReference("1xdYDgDXnj1ZVODHEdcSAv51ex58GuGVPBrBTQVFOrUs/Sheet1").orderByChild("admissionno").equalTo(Integer.parseInt(admno));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                    Map<String, Object> values=(Map<String, Object>)issue.getValue();
                                    TextView AM31=(TextView)findViewById(R.id.textAM31);
                                    TextView AM32=(TextView)findViewById(R.id.textAM32);
                                    TextView AM33=(TextView)findViewById(R.id.textAM33);
                                    TextView AM34=(TextView)findViewById(R.id.textAM34);

                                    AM31.setText(values.get("AMIII IA").toString());
                                    AM32.setText(values.get("AMIII IA").toString());
                                    AM33.setText(values.get("AMIII IA").toString());
                                    AM34.setText(values.get("AMIII SE").toString());

                                    TextView DM1=(TextView)findViewById(R.id.textDM1);
                                    TextView DM2=(TextView)findViewById(R.id.textDM2);
                                    TextView DM3=(TextView)findViewById(R.id.textDM3);
                                    TextView DM4=(TextView)findViewById(R.id.textDM4);

                                    DM1.setText(values.get("DM IA").toString());
                                    DM2.setText(values.get("DM IA").toString());
                                    DM3.setText(values.get("DM IA").toString());
                                    DM4.setText(values.get("DM SE").toString());

                                    TextView DLDA1=(TextView)findViewById(R.id.textDLDA1);
                                    TextView DLDA2=(TextView)findViewById(R.id.textDLDA2);
                                    TextView DLDA3=(TextView)findViewById(R.id.textDLDA3);
                                    TextView DLDA4=(TextView)findViewById(R.id.textDLDA4);

                                    DLDA1.setText(values.get("DLDA IA").toString());
                                    DLDA2.setText(values.get("DLDA IA").toString());
                                    DLDA3.setText(values.get("DLDA IA").toString());
                                    DLDA4.setText(values.get("DLDA SE").toString());

                                    TextView ECCF1=(TextView)findViewById(R.id.textECCF1);
                                    TextView ECCF2=(TextView)findViewById(R.id.textECCF2);
                                    TextView ECCF3=(TextView)findViewById(R.id.textECCF3);
                                    TextView ECCF4=(TextView)findViewById(R.id.textECCF4);

                                    ECCF1.setText(values.get("ECCF IA").toString());
                                    ECCF2.setText(values.get("ECCF IA").toString());
                                    ECCF3.setText(values.get("ECCF IA").toString());
                                    ECCF4.setText(values.get("ECCF SE").toString());

                                    TextView DS1=(TextView)findViewById(R.id.textDS1);
                                    TextView DS2=(TextView)findViewById(R.id.textDS2);
                                    TextView DS3=(TextView)findViewById(R.id.textDS3);
                                    TextView DS4=(TextView)findViewById(R.id.textDS4);

                                    DS1.setText(values.get("DS IA").toString());
                                    DS2.setText(values.get("DS IA").toString());
                                    DS3.setText(values.get("DS IA").toString());
                                    DS4.setText(values.get("DS SE").toString());

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
