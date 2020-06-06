package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TeacherAttendance extends AppCompatActivity {
    private Spinner spinMonth;
    EditText yearTextBox;
    EditText divisionTextBox;
    EditText subjectTextBox;
    EditText startRowTextBox;
    EditText endRowTextBox;
    EditText rollNumberColumn;
    EditText nameColumn;
    EditText attendanceColumn;
    Button uploadButton;
    String selectedMonth = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);
        spinMonth = findViewById(R.id.spinDate);
        yearTextBox = findViewById(R.id.Year);
        divisionTextBox = findViewById(R.id.Division);
        subjectTextBox = findViewById(R.id.Subject);
        startRowTextBox = findViewById(R.id.startRow);
        endRowTextBox = findViewById(R.id.endRow);
        rollNumberColumn = findViewById(R.id.rollNo);
        nameColumn = findViewById(R.id.name);
        attendanceColumn = findViewById(R.id.attendance);
        uploadButton = findViewById(R.id.uploadButton);

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
                String yearText = yearTextBox.getText().toString().trim();
                String divisionText = divisionTextBox.getText().toString().trim();
                String subjectText = subjectTextBox.getText().toString().trim();
                String startRowText = startRowTextBox.getText().toString().trim();
                String endRowText = endRowTextBox.getText().toString().trim();
                String rollNumberText = rollNumberColumn.getText().toString().trim();
                String nameText = nameColumn.getText().toString().trim();
                String attendanceText = attendanceColumn.getText().toString().trim();
                if(yearText.isEmpty()){
                    yearTextBox.setError("Please enter the year");
                    yearTextBox.requestFocus();
                }
                else if(divisionText.isEmpty()){
                    divisionTextBox.setError("Please enter the division");
                    divisionTextBox.requestFocus();
                }
                else if((subjectText).isEmpty()){
                    subjectTextBox.setError("Please enter the subject");
                    subjectTextBox.requestFocus();
                }
                else if(startRowText.isEmpty()){
                    startRowTextBox.setError("Please enter the start row");
                    startRowTextBox.requestFocus();
                }
                else if(endRowText.isEmpty()){
                    endRowTextBox.setError("Please enter the end row");
                    endRowTextBox.requestFocus();
                }
                else if(rollNumberText.isEmpty()){
                    rollNumberColumn.setError("Please enter the roll number column");
                    rollNumberColumn.requestFocus();
                }
                else if(nameText.isEmpty()){
                    nameColumn.setError("Please enter the name column number");
                    nameColumn.requestFocus();
                }
                else if(attendanceText.isEmpty()){
                    attendanceColumn.setError("Please enter attendance column number");
                    attendanceColumn.requestFocus();
                }
                else{
                    Intent goToUploadAttendance = new Intent(TeacherAttendance.this, UploadAttendance.class);
                    goToUploadAttendance.putExtra("Start_Row_Number", startRowText);
                    goToUploadAttendance.putExtra("End_Row_Number", endRowText);
                    goToUploadAttendance.putExtra("Roll_Number_Column", rollNumberText);
                    goToUploadAttendance.putExtra("Name_Column", nameText);
                    goToUploadAttendance.putExtra("Attendance_Column", attendanceText);
                    goToUploadAttendance.putExtra("Attendance_Division", divisionText);
                    goToUploadAttendance.putExtra("Attendance_Subject", subjectText);
                    goToUploadAttendance.putExtra("Attendance_Year", yearText);
                    goToUploadAttendance.putExtra("Attendance_Month", selectedMonth);
                    startActivity(goToUploadAttendance);
                }
            }
        });

    }
}