package com.example.attendanceapp;

public class Events_show_Helper {
    String eventName, deptName, dateofevent, fromTime, toTime, desc, link;

    public Events_show_Helper() {

    }

    public Events_show_Helper(String eventName, String deptName, String dateofevent, String fromTime, String toTime, String desc, String link) {
        this.eventName = eventName;
        this.deptName = deptName;
        this.dateofevent = dateofevent;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.desc = desc;
        this.link = link;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDateofevent() {
        return dateofevent;
    }

    public void setDateofevent(String dateofevent) {
        this.dateofevent = dateofevent;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
