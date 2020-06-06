package com.example.attendanceapp;

public class Attendance {
    public String year,month,division,subject,roll_no,name,attendance;
    public Attendance(){

    }
    public Attendance(String year, String month, String division, String subject, String roll_no, String name, String attendance){
        this.year = year;
        this.month = month;
        this.division = division;
        this.subject = subject;
        this.roll_no = roll_no;
        this.name = name;
        this.attendance = attendance;
    }
}
