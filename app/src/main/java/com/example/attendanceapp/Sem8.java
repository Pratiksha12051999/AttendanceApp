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
                                    TextView HMI1=(TextView)findViewById(R.id.textHMI1);
                                    TextView HMI2=(TextView)findViewById(R.id.textHMI2);
                                    TextView HMI3=(TextView)findViewById(R.id.textHMI3);
                                    TextView HMI4=(TextView)findViewById(R.id.textHMI4);

                                    HMI1.setText(values.get("HMI IA").toString());
                                    HMI2.setText(values.get("HMI IA").toString());
                                    HMI3.setText(values.get("HMI IA").toString());
                                    HMI4.setText(values.get("HMI SE").toString());

                                    TextView DC1=(TextView)findViewById(R.id.textDC1);
                                    TextView DC2=(TextView)findViewById(R.id.textDC2);
                                    TextView DC3=(TextView)findViewById(R.id.textDC3);
                                    TextView DC4=(TextView)findViewById(R.id.textDC4);

                                    DC1.setText(values.get("DC IA").toString());
                                    DC2.setText(values.get("DC IA").toString());
                                    DC3.setText(values.get("DC IA").toString());
                                    DC4.setText(values.get("DC SE").toString());

                                    TextView DLOC41=(TextView)findViewById(R.id.textDLOC41);
                                    TextView DLOC42=(TextView)findViewById(R.id.textDLOC42);
                                    TextView DLOC43=(TextView)findViewById(R.id.textDLOC43);
                                    TextView DLOC44=(TextView)findViewById(R.id.textDLOC44);

                                    DLOC41.setText(values.get("DLOCIV IA").toString());
                                    DLOC42.setText(values.get("DLOCIV IA").toString());
                                    DLOC43.setText(values.get("DLOCIV IA").toString());
                                    DLOC44.setText(values.get("DLOCIV SE").toString());

                                    TextView ILOC21=(TextView)findViewById(R.id.textILOC21);
                                    TextView ILOC22=(TextView)findViewById(R.id.textILOC22);
                                    TextView ILOC23=(TextView)findViewById(R.id.textILOC23);
                                    TextView ILOC24=(TextView)findViewById(R.id.textILOC24);

                                    ILOC21.setText(values.get("ILOCII IA").toString());
                                    ILOC22.setText(values.get("ILOCII IA").toString());
                                    ILOC23.setText(values.get("ILOCII IA").toString());
                                    ILOC24.setText(values.get("ILOCII SE").toString());

                                    TextView MaProj21=(TextView)findViewById(R.id.textMaProj21);
                                    TextView MaProj22=(TextView)findViewById(R.id.textMaProj22);
                                    TextView MaProj23=(TextView)findViewById(R.id.textMaProj23);
                                    TextView MaProj24=(TextView)findViewById(R.id.textMaProj24);

                                    MaProj21.setText("-");
                                    MaProj22.setText("-");
                                    MaProj23.setText("-");
                                    MaProj24.setText(values.get("MajorProject II").toString());
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