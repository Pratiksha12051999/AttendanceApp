package com.example.attendanceapp;

public class UserDetails {
    public Object admissionno;
    public Object email;
    public Object pwd;

    public UserDetails(){}
    public UserDetails(Object admissionno, Object email, Object pwd){
        this.email=email;
        this.admissionno=admissionno;
        this.pwd=pwd;
    }
}
