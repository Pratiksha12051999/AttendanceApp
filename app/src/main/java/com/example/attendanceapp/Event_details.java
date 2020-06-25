package com.example.attendanceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Event_details extends AppCompatActivity {
    //Firebase
    FirebaseAuth fireabseAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Events");
    FirebaseDatabase mFirebaseDatabase;
    StorageReference storageReference;

    //Android layout
    EditText eventName, deptName, dateofevent, fromTime, toTime, desc, link;
    Button submit, upload_img;
    ImageView img;
    private static final int PICK_IMAGE_CODE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_CODE)
        {
//            Toast.makeText(this, "Successfully uploaded",Toast.LENGTH_SHORT).show();
            UploadTask uploadTask = storageReference.putFile(data.getData());

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        Toast.makeText(Event_details.this, "failed to upload",Toast.LENGTH_SHORT).show();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        String url = task.getResult().toString().substring(0,task.getResult().toString().indexOf("&token"));
                        Log.d("DIRECTLINK",url);
                        Picasso.get().load(url).into(img);
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventName = findViewById(R.id.event);
        deptName = findViewById(R.id.deptName);
        dateofevent = findViewById(R.id.dateofevent);
        fromTime = findViewById(R.id.fromTime);
        toTime = findViewById(R.id.toTime);
        desc = findViewById(R.id.desc);
        link = findViewById(R.id.link);

        img = findViewById(R.id.img);
        upload_img = findViewById(R.id.upload_img);
        submit = findViewById(R.id.submit);

        storageReference = FirebaseStorage.getInstance().getReference("Event_details");
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image to upload"),PICK_IMAGE_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = dateofevent.getText().toString().trim();
                String Dept = deptName.getText().toString().trim();
                String Description = desc.getText().toString().trim();
                String EventName= eventName.getText().toString().trim();
                String Fromtime = fromTime.getText().toString().trim();
                String Link = link.getText().toString().trim();
                String Totime = toTime.getText().toString().trim();

                mDatabase.child(EventName).child("Eventname").setValue(EventName);
                mDatabase.child(EventName).child("Date").setValue(Date);
                mDatabase.child(EventName).child("Dept").setValue(Dept);
                mDatabase.child(EventName).child("Description").setValue(Description);
                mDatabase.child(EventName).child("Fromtime").setValue(Fromtime);
                mDatabase.child(EventName).child("Link").setValue(Link);
                mDatabase.child(EventName).child("Totime").setValue(Totime);

                Toast.makeText(Event_details.this, "Successfully submitted event",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
