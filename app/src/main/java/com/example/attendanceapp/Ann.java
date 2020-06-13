package com.example.attendanceapp;

import android.widget.TextView;

public class Ann {
    String Subject;
    String Content;
    String Timestamp;

    public Ann() {
    }

    public Ann(String Subject, String Content, String Timestamp) {
        this.Subject = Subject;
        this.Content = Content;
        this.Timestamp = Timestamp;
    }


    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }
}
