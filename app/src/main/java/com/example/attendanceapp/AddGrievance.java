package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddGrievance extends AppCompatActivity {
    private Spinner spinner;
    String selectedField;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Button uploadButton;
    Button submitButton;
    Uri pdfUri;
    Button button2;
    EditText titleTextBox, descriptionTextBox;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grievance);
        spinner = findViewById(R.id.toTextBox);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        uploadButton = findViewById(R.id.button2);
        submitButton = findViewById(R.id.submitButton);
        titleTextBox = findViewById(R.id.titleTextBox);
        descriptionTextBox = findViewById(R.id.descriptionTextBox);
        progressBar = findViewById(R.id.progressBar4);
        button2 = findViewById(R.id.button2);

        List<String> fields = new ArrayList<>();
        fields.add("Admission");
        fields.add("Exams");
        fields.add("Fees");
        fields.add("Academics");
        fields.add("Results");
        fields.add("Attendance");
        fields.add("Events");
        fields.add("Others");
        ArrayAdapter<String> fieldsadapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, fields);
        fieldsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fieldsadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedField = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddGrievance.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else{
                    ActivityCompat.requestPermissions(AddGrievance.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null){
                    uploadFile(pdfUri);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(AddGrievance.this, "Select a file", Toast.LENGTH_SHORT).show();
                }
                String title = titleTextBox.getText().toString().trim();
                String description = descriptionTextBox.getText().toString().trim();

                if(title.isEmpty()){
                    titleTextBox.setError("Please enter a Title");
                    titleTextBox.requestFocus();
                }else if(description.isEmpty()){
                    descriptionTextBox.setError("Please enter the description");
                    descriptionTextBox.requestFocus();
                }
                else{
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String userEmail = user.getEmail();
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference reference = firebaseDatabase.getReference();
                    reference.child("Grievance").child(currentuser).child("Title").setValue(title);
                    reference.child("Grievance").child(currentuser).child("Email").setValue(userEmail);
                    reference.child("Grievance").child(currentuser).child("Description").setValue(description);
                    reference.child("Grievance").child(currentuser).child("To").setValue(selectedField);
                }
            }
        });
    }

    private void uploadFile(final Uri pdfUri) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userEmail = user.getEmail();
        final String filename = System.currentTimeMillis()+" ";
        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Uploads").child(filename).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference reference = firebaseDatabase.getReference();
                                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                reference.child("Grievance").child(currentuser).child("File").child(filename).setValue(uri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(AddGrievance.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        public void run() {
                                                            Intent goBack = new Intent(AddGrievance.this, Tabs.class);
                                                            startActivity(goBack);
                                                        }
                                                    }, 2000);
                                                }
                                            }
                                        });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddGrievance.this, "File not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(AddGrievance.this, "Please provide the required permissions",Toast.LENGTH_SHORT).show();
        }
    }

    public void selectPdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            button2.setText("File Selected");
            pdfUri = data.getData();
        }else{
            Toast.makeText(AddGrievance.this, "Please select a file",Toast.LENGTH_SHORT).show();
        }
    }
}