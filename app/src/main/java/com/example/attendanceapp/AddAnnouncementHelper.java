package com.example.attendanceapp;

import android.widget.EditText;

public class AddAnnouncementHelper {
    EditText Subject, Date, Content;

    public AddAnnouncementHelper() {

    }

    public AddAnnouncementHelper(EditText subject, EditText date, EditText content) {
        Subject = subject;
        Date = date;
        Content = content;
    }

    public EditText getSubject() {
        return Subject;
    }

    public void setSubject(EditText subject) {
        Subject = subject;
    }

    public EditText getDate() {
        return Date;
    }

    public void setDate(EditText date) {
        Date = date;
    }

    public EditText getContent() {
        return Content;
    }

    public void setContent(EditText content) {
        Content = content;
    }
}
