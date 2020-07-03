package com.example.attendanceapp;

import android.widget.Button;

public class notneededAssignment_yearwise {
    private int img;
    private String text1;
    String btn1, btn2;

    public notneededAssignment_yearwise(int img, String text1, String btn1, String btn2) {
        this.img = img;
        this.text1 = text1;
        this.btn1 = btn1;
        this.btn2 = btn2;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getBtn1() {
        return btn1;
    }

    public void setBtn1(String btn1) {
        this.btn1 = btn1;
    }

    public String getBtn2() {
        return btn2;
    }

    public void setBtn2(String btn2) {
        this.btn2 = btn2;
    }
}
