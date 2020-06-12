package com.example.attendanceapp;

public class Blog {
    private String Subject;
    private String Content;

    public Blog(String subject, String content) {
        Subject = subject;
        Content = content;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Blog(){

    }

}
