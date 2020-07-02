package com.example.attendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeacherAttendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Spinner spinMonth;
    String selectedMonth = null;
    Button uploadButton;
    EditText divisionTextBox;
    EditText startDateTextBox;
    EditText endDateTextBox;
    EditText numberOfStudentsTextBox;
    EditText yearTextBox;
    private DrawerLayout drawer;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);
        spinMonth = findViewById(R.id.spinDate);
        uploadButton = findViewById(R.id.uplodButton);
        divisionTextBox = findViewById(R.id.Division);
        startDateTextBox = findViewById(R.id.startDate);
        endDateTextBox = findViewById(R.id.endDate);
        numberOfStudentsTextBox = findViewById(R.id.numberOfStudentsTextBox);
        yearTextBox = findViewById(R.id.yearTextBox);
        yearTextBox.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);

        List<String> Month = new ArrayList<>();
        Month.add("January");
        Month.add("February");
        Month.add("March");
        Month.add("April");
        Month.add("May");
        Month.add("June");
        Month.add("July");
        Month.add("August");
        Month.add("September");
        Month.add("October");
        Month.add("November");
        Month.add("Design");
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Month);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(monthAdapter);
        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedMonth = spinMonth.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String divisionText = divisionTextBox.getText().toString().trim();
                String startDateText = startDateTextBox.getText().toString().trim();
                String endDateText = endDateTextBox.getText().toString().trim();
                String numberOfStudents = numberOfStudentsTextBox.getText().toString().trim();
                if(divisionText.isEmpty()){
                    divisionTextBox.setError("Please enter the division");
                    divisionTextBox.requestFocus();
                }
                else if((startDateText).isEmpty()){
                    startDateTextBox.setError("Please enter the Start Date");
                    startDateTextBox.requestFocus();
                }
                else if(endDateText.isEmpty()){
                    endDateTextBox.setError("Please enter the End Date");
                    endDateTextBox.requestFocus();
                }
                else if(selectedMonth == null){
                    Toast.makeText(TeacherAttendance.this, "Kindly select a month", Toast.LENGTH_SHORT).show();
                }
                else if(numberOfStudents.isEmpty())
                {
                    numberOfStudentsTextBox.setError("Please enter the Number of Students");
                    numberOfStudentsTextBox.requestFocus();
                }else{
                    Intent goToUploadAttendance = new Intent(TeacherAttendance.this, UploadAttendance.class);
                    goToUploadAttendance.putExtra("startDateText", startDateText);
                    goToUploadAttendance.putExtra("endDateText", endDateText);
                    goToUploadAttendance.putExtra("divisionText", divisionText);
                    goToUploadAttendance.putExtra("selectedMonth", selectedMonth);
                    goToUploadAttendance.putExtra("numberOfStudents", numberOfStudents);
                    startActivity(goToUploadAttendance);
                }
            }
        });

        /*Side dock part*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.nav_announcement:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_contact:
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent goBackToLogin = new Intent(TeacherAttendance.this, MainActivity.class);
                startActivity(goBackToLogin);
        }
        return true;
    }
}