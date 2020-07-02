package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Sem5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem5);

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

                                    TextView MP1=(TextView)findViewById(R.id.textMP1);
                                    TextView MP2=(TextView)findViewById(R.id.textMP2);
                                    TextView MP3=(TextView)findViewById(R.id.textMP3);
                                    TextView MP4=(TextView)findViewById(R.id.textMP4);

                                    MP1.setText(values.get("MP IA").toString()+"/20");
                                    MP2.setText(values.get("MP IA").toString()+"/20");
                                    MP3.setText(values.get("MP IA").toString()+"/20");
                                    MP4.setText(values.get("MP SE").toString()+"/80");

                                    TextView DBMS1=(TextView)findViewById(R.id.textDBMS1);
                                    TextView DBMS2=(TextView)findViewById(R.id.textDBMS2);
                                    TextView DBMS3=(TextView)findViewById(R.id.textDBMS3);
                                    TextView DBMS4=(TextView)findViewById(R.id.textDBMS4);

                                    DBMS1.setText(values.get("DBMS IA").toString()+"/20");
                                    DBMS2.setText(values.get("DBMS IA").toString()+"/20");
                                    DBMS3.setText(values.get("DBMS IA").toString()+"/20");
                                    DBMS4.setText(values.get("DBMS SE").toString()+"/80");

                                    TextView CN1=(TextView)findViewById(R.id.textCN1);
                                    TextView CN2=(TextView)findViewById(R.id.textCN2);
                                    TextView CN3=(TextView)findViewById(R.id.textCN3);
                                    TextView CN4=(TextView)findViewById(R.id.textCN4);

                                    CN1.setText(values.get("CN IA").toString()+"/20");
                                    CN2.setText(values.get("CN IA").toString()+"/20");
                                    CN3.setText(values.get("CN IA").toString()+"/20");
                                    CN4.setText(values.get("CN SE").toString()+"/80");

                                    TextView TCS1=(TextView)findViewById(R.id.textTCS1);
                                    TextView TCS2=(TextView)findViewById(R.id.textTCS2);
                                    TextView TCS3=(TextView)findViewById(R.id.textTCS3);
                                    TextView TCS4=(TextView)findViewById(R.id.textTCS4);

                                    TCS1.setText(values.get("TCS IA").toString()+"/20");
                                    TCS2.setText(values.get("TCS IA").toString()+"/20");
                                    TCS3.setText(values.get("TCS IA").toString()+"/20");
                                    TCS4.setText(values.get("TCS SE").toString()+"/80");

                                    TextView DLOC11=(TextView)findViewById(R.id.textDLOC11);
                                    TextView DLOC12=(TextView)findViewById(R.id.textDLOC12);
                                    TextView DLOC13=(TextView)findViewById(R.id.textDLOC13);
                                    TextView DLOC14=(TextView)findViewById(R.id.textDLOC14);

                                    DLOC11.setText(values.get("DLOCI IA").toString()+"/20");
                                    DLOC12.setText(values.get("DLOCI IA").toString()+"/20");
                                    DLOC13.setText(values.get("DLOCI IA").toString()+"/20");
                                    DLOC14.setText(values.get("DLOCI SE").toString()+"/80");

                                    TextView BCE1=(TextView)findViewById(R.id.textBCE1);
                                    TextView BCE2=(TextView)findViewById(R.id.textBCE2);
                                    TextView BCE3=(TextView)findViewById(R.id.textBCE3);
                                    TextView BCE4=(TextView)findViewById(R.id.textBCE4);

                                    BCE1.setText("-");
                                    BCE2.setText("-");
                                    BCE3.setText("-");
                                    BCE4.setText(values.get("BCE").toString()+"/50");
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