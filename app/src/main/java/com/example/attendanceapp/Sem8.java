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

public class Sem8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem8);

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

                                    TextView HMI1=(TextView)findViewById(R.id.textHMI1);
                                    TextView HMI2=(TextView)findViewById(R.id.textHMI2);
                                    TextView HMI3=(TextView)findViewById(R.id.textHMI3);
                                    TextView HMI4=(TextView)findViewById(R.id.textHMI4);

                                    HMI1.setText(values.get("HMI IA").toString()+"/20");
                                    HMI2.setText(values.get("HMI IA").toString()+"/20");
                                    HMI3.setText(values.get("HMI IA").toString()+"/20");
                                    HMI4.setText(values.get("HMI SE").toString()+"/80");

                                    TextView DC1=(TextView)findViewById(R.id.textDC1);
                                    TextView DC2=(TextView)findViewById(R.id.textDC2);
                                    TextView DC3=(TextView)findViewById(R.id.textDC3);
                                    TextView DC4=(TextView)findViewById(R.id.textDC4);

                                    DC1.setText(values.get("DC IA").toString()+"/20");
                                    DC2.setText(values.get("DC IA").toString()+"/20");
                                    DC3.setText(values.get("DC IA").toString()+"/20");
                                    DC4.setText(values.get("DC SE").toString()+"/80");

                                    TextView DLOC41=(TextView)findViewById(R.id.textDLOC41);
                                    TextView DLOC42=(TextView)findViewById(R.id.textDLOC42);
                                    TextView DLOC43=(TextView)findViewById(R.id.textDLOC43);
                                    TextView DLOC44=(TextView)findViewById(R.id.textDLOC44);

                                    DLOC41.setText(values.get("DLOCIV IA").toString()+"/20");
                                    DLOC42.setText(values.get("DLOCIV IA").toString()+"/20");
                                    DLOC43.setText(values.get("DLOCIV IA").toString()+"/20");
                                    DLOC44.setText(values.get("DLOCIV SE").toString()+"/80");

                                    TextView ILOC21=(TextView)findViewById(R.id.textILOC21);
                                    TextView ILOC22=(TextView)findViewById(R.id.textILOC22);
                                    TextView ILOC23=(TextView)findViewById(R.id.textILOC23);
                                    TextView ILOC24=(TextView)findViewById(R.id.textILOC24);

                                    ILOC21.setText(values.get("ILOCII IA").toString()+"/20");
                                    ILOC22.setText(values.get("ILOCII IA").toString()+"/20");
                                    ILOC23.setText(values.get("ILOCII IA").toString()+"/20");
                                    ILOC24.setText(values.get("ILOCII SE").toString()+"/80");

                                    TextView MaProj21=(TextView)findViewById(R.id.textMaProj21);
                                    TextView MaProj22=(TextView)findViewById(R.id.textMaProj22);
                                    TextView MaProj23=(TextView)findViewById(R.id.textMaProj23);
                                    TextView MaProj24=(TextView)findViewById(R.id.textMaProj24);

                                    MaProj21.setText("-\n/50");
                                    MaProj22.setText("-\n/50");
                                    MaProj23.setText("-\n/50");
                                    MaProj24.setText(values.get("MajorProject II").toString()+"\n/50");
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