package com.example.a18440164.a1;

import java.util.Date;

public class EventModel {
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
}
