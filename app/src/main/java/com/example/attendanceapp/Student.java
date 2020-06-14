package com.example.attendanceapp;

/**
 * Created by Belal on 4/17/2018.
 */

public class Student {

    public String id, Name, Year, Division, Month;
    public int Roll_No;
    public String Subjects, Total_Attendance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public int getRoll_No() {
        return Roll_No;
    }

    public void setRoll_No(int roll_No) {
        Roll_No = roll_No;
    }

    public String getSubjects() {
        return Subjects;
    }

    public void setSubjects(String subjects) {
        Subjects = subjects;
    }

    public String getTotal_Attendance() {
        return Total_Attendance;
    }

    public void setTotal_Attendance(String total_Attendance) {
        Total_Attendance = total_Attendance;
    }

    public Student() {
    }
}
