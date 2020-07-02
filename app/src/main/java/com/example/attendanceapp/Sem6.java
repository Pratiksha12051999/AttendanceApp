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

public class Sem6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem6);

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

                                    TextView SE1=(TextView)findViewById(R.id.textSE1);
                                    TextView SE2=(TextView)findViewById(R.id.textSE2);
                                    TextView SE3=(TextView)findViewById(R.id.textSE3);
                                    TextView SE4=(TextView)findViewById(R.id.textSE4);

                                    SE1.setText(values.get("SE IA").toString()+"/20");
                                    SE2.setText(values.get("SE IA").toString()+"/20");
                                    SE3.setText(values.get("SE IA").toString()+"/20");
                                    SE4.setText(values.get("SE SE").toString()+"/80");

                                    TextView SPCC1=(TextView)findViewById(R.id.textSPCC1);
                                    TextView SPCC2=(TextView)findViewById(R.id.textSPCC2);
                                    TextView SPCC3=(TextView)findViewById(R.id.textSPCC3);
                                    TextView SPCC4=(TextView)findViewById(R.id.textSPCC4);

                                    SPCC1.setText(values.get("SPCC IA").toString()+"/20");
                                    SPCC2.setText(values.get("SPCC IA").toString()+"/20");
                                    SPCC3.setText(values.get("SPCC IA").toString()+"/20");
                                    SPCC4.setText(values.get("SPCC SE").toString()+"/80");

                                    TextView DWM1=(TextView)findViewById(R.id.textDWM1);
                                    TextView DWM2=(TextView)findViewById(R.id.textDWM2);
                                    TextView DWM3=(TextView)findViewById(R.id.textDWM3);
                                    TextView DWM4=(TextView)findViewById(R.id.textDWM4);

                                    DWM1.setText(values.get("DWM IA").toString()+"/20");
                                    DWM2.setText(values.get("DWM IA").toString()+"/20");
                                    DWM3.setText(values.get("DWM IA").toString()+"/20");
                                    DWM4.setText(values.get("DWM SE").toString()+"/80");

                                    TextView CSS1=(TextView)findViewById(R.id.textCSS1);
                                    TextView CSS2=(TextView)findViewById(R.id.textCSS2);
                                    TextView CSS3=(TextView)findViewById(R.id.textCSS3);
                                    TextView CSS4=(TextView)findViewById(R.id.textCSS4);

                                    CSS1.setText(values.get("CSS IA").toString()+"/20");
                                    CSS2.setText(values.get("CSS IA").toString()+"/20");
                                    CSS3.setText(values.get("CSS IA").toString()+"/20");
                                    CSS4.setText(values.get("CSS SE").toString()+"/80");

                                    TextView DLOC21=(TextView)findViewById(R.id.textDLOC21);
                                    TextView DLOC22=(TextView)findViewById(R.id.textDLOC22);
                                    TextView DLOC23=(TextView)findViewById(R.id.textDLOC23);
                                    TextView DLOC24=(TextView)findViewById(R.id.textDLOC24);

                                    DLOC21.setText(values.get("DLOCII IA").toString()+"/20");
                                    DLOC22.setText(values.get("DLOCII IA").toString()+"/20");
                                    DLOC23.setText(values.get("DLOCII IA").toString()+"/20");
                                    DLOC24.setText(values.get("DLOCII SE").toString()+"/80");

                                    TextView MiProj1=(TextView)findViewById(R.id.textMiProj1);
                                    TextView MiProj2=(TextView)findViewById(R.id.textMiProj2);
                                    TextView MiProj3=(TextView)findViewById(R.id.textMiProj3);
                                    TextView MiProj4=(TextView)findViewById(R.id.textMiProj4);

                                    MiProj1.setText("-\n/50");
                                    MiProj2.setText("-\n/50");
                                    MiProj3.setText("-\n/50");
                                    MiProj4.setText(values.get("MiniProject").toString()+"\n/50");
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