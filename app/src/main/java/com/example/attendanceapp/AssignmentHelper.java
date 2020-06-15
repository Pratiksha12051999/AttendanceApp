package com.example.attendanceapp;

public class AssignmentHelper {
    String Startdate;
    String Enddate;
    String Assno;
    String Question;

    public AssignmentHelper(){

    }
    public AssignmentHelper(String Startdate, String Enddate, String Assno, String Question){
        this.Startdate= Startdate;
        this.Enddate=Enddate;
        this.Assno=Assno;
        this.Question = Question;

    }

    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public String getAssno() {
        return Assno;
    }

    public void setAssno(String assno) {
        Assno = assno;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
