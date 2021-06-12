package com.example.a18440164.a1;

import java.io.Serializable;
import java.util.Date;

public class EventModel implements Serializable {
    public EventModel(){}

    public EventModel(Date start, Date end){
        Start = start;
        End = end;
    }
    public String Title;
    public String Description;
    public String Location;
    public Date Start;
    public Date End;
    public int Reminder1;
    public int Reminder2;
    public int Reminder3;
}
