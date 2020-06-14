package com.example.attendanceapp;

import android.widget.EditText;

public class ExamHelper {
    public EditText FirstName, MiddleName, LastName, emailid, Address, Contact, DOB;

    public ExamHelper() {

    }

    public ExamHelper(EditText firstName, EditText middleName, EditText lastName, EditText emailid, EditText address, EditText contact, EditText DOB) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        this.emailid = emailid;
        Address = address;
        Contact = contact;
        this.DOB = DOB;
    }

    public EditText getFirstName() {
        return FirstName;
    }

    public void setFirstName(EditText firstName) {
        FirstName = firstName;
    }

    public EditText getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(EditText middleName) {
        MiddleName = middleName;
    }

    public EditText getLastName() {
        return LastName;
    }

    public void setLastName(EditText lastName) {
        LastName = lastName;
    }

    public EditText getEmailid() {
        return emailid;
    }

    public void setEmailid(EditText emailid) {
        this.emailid = emailid;
    }

    public EditText getAddress() {
        return Address;
    }

    public void setAddress(EditText address) {
        Address = address;
    }

    public EditText getContact() {
        return Contact;
    }

    public void setContact(EditText contact) {
        Contact = contact;
    }

    public EditText getDOB() {
        return DOB;
    }

    public void setDOB(EditText DOB) {
        this.DOB = DOB;
    }
}
