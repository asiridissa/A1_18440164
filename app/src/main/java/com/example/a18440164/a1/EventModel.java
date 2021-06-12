package com.example.a18440164.a1;

import java.io.Serializable;
import java.util.Date;

public class EventModel implements Serializable {
    public EventModel(){}

    public EventModel(Date start, Date end){
        StartTime = start;
        EndTime = end;
    }
    public String Title;
    public String Description;
    public String Location;
    public Date StartTime;
    public Date EndTime;
    public Integer Reminder1;
    public Integer Reminder2;
    public Integer Reminder3;
}
