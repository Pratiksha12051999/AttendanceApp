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

public class Sem7 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem7);

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

                                    TextView studentname=(TextView)findViewById(R.id.textStudentName);
                                    studentname.setText(values.get("Name").toString());

                                    TextView DSIP1=(TextView)findViewById(R.id.textDSIP1);
                                    TextView DSIP2=(TextView)findViewById(R.id.textDSIP2);
                                    TextView DSIP3=(TextView)findViewById(R.id.textDSIP3);
                                    TextView DSIP4=(TextView)findViewById(R.id.textDSIP4);

                                    DSIP1.setText(values.get("DSIP IA").toString()+"/20");
                                    DSIP2.setText(values.get("DSIP IA").toString()+"/20");
                                    DSIP3.setText(values.get("DSIP IA").toString()+"/20");
                                    DSIP4.setText(values.get("DSIP SE").toString()+"/80");

                                    TextView MCC1=(TextView)findViewById(R.id.textMCC1);
                                    TextView MCC2=(TextView)findViewById(R.id.textMCC2);
                                    TextView MCC3=(TextView)findViewById(R.id.textMCC3);
                                    TextView MCC4=(TextView)findViewById(R.id.textMCC4);

                                    MCC1.setText(values.get("MCC IA").toString()+"/20");
                                    MCC2.setText(values.get("MCC IA").toString()+"/20");
                                    MCC3.setText(values.get("MCC IA").toString()+"/20");
                                    MCC4.setText(values.get("MCC SE").toString()+"/80");

                                    TextView AISC1=(TextView)findViewById(R.id.textAISC1);
                                    TextView AISC2=(TextView)findViewById(R.id.textAISC2);
                                    TextView AISC3=(TextView)findViewById(R.id.textAISC3);
                                    TextView AISC4=(TextView)findViewById(R.id.textAISC4);

                                    AISC1.setText(values.get("AISC IA").toString()+"/20");
                                    AISC2.setText(values.get("AISC IA").toString()+"/20");
                                    AISC3.setText(values.get("AISC IA").toString()+"/20");
                                    AISC4.setText(values.get("AISC SE").toString()+"/80");

                                    TextView DLOC31=(TextView)findViewById(R.id.textDLOC31);
                                    TextView DLOC32=(TextView)findViewById(R.id.textDLOC32);
                                    TextView DLOC33=(TextView)findViewById(R.id.textDLOC33);
                                    TextView DLOC34=(TextView)findViewById(R.id.textDLOC34);

                                    DLOC31.setText(values.get("DLOCIII IA").toString()+"/20");
                                    DLOC32.setText(values.get("DLOCIII IA").toString()+"/20");
                                    DLOC33.setText(values.get("DLOCIII IA").toString()+"/20");
                                    DLOC34.setText(values.get("DLOCIII SE").toString()+"/80");

                                    TextView ILOC11=(TextView)findViewById(R.id.textILOC11);
                                    TextView ILOC12=(TextView)findViewById(R.id.textILOC12);
                                    TextView ILOC13=(TextView)findViewById(R.id.textILOC13);
                                    TextView ILOC14=(TextView)findViewById(R.id.textILOC14);

                                    ILOC11.setText(values.get("ILOCI IA").toString()+"/20");
                                    ILOC12.setText(values.get("ILOCI IA").toString()+"/20");
                                    ILOC13.setText(values.get("ILOCI IA").toString()+"/20");
                                    ILOC14.setText(values.get("ILOCI SE").toString()+"/80");

                                    TextView MaProj11=(TextView)findViewById(R.id.textMaProj11);
                                    TextView MaProj12=(TextView)findViewById(R.id.textMaProj12);
                                    TextView MaProj13=(TextView)findViewById(R.id.textMaProj13);
                                    TextView MaProj14=(TextView)findViewById(R.id.textMaProj14);

                                    MaProj11.setText("-");
                                    MaProj12.setText("-");
                                    MaProj13.setText("-");
                                    MaProj14.setText(values.get("MajorProject I").toString()+"/50");
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