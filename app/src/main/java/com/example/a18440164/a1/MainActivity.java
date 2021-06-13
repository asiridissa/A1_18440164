package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CalendarView view = new CalendarView(this);
//        setContentView(view);
        CalendarView view = (CalendarView) findViewById(R.id.calendarView);
        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                Toast.makeText(getApplicationContext(),"Selected Date : " +date+ "/"+month+"/"+year,Toast.LENGTH_LONG).show();
                openForm(year,month,date);
            }
        });

//        ListView lv = new ListView(this);
//        setContentView(lv);
//
//        DBHandler db = new DBHandler(this);
//        eventsLV = (ListView) findViewById(R.id.eventsList);
//        ArrayAdapter<EventModel> eventAdapter = new ArrayAdapter<EventModel>(this, android.R.layout.simple_list_item_1, db.getAllEvents());
//        eventsLV.setAdapter(eventAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCount(new Date());
    }

    void openForm(int year, int month, int date){
        Intent launchEditorIntent = new Intent(this, FormActivity.class);
        EventModel model = new EventModel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        model.StartTime = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        model.EndTime = calendar.getTime();
        launchEditorIntent.putExtra("model", (Serializable) model);
        launchEditorIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(launchEditorIntent);
    }

    void updateCount(Date date){
        TextView c = (TextView) findViewById(R.id.txtCount);
        String count = String.valueOf(new DBHandler(this).getEventsCount(date));
        c.setText(count + " scheduled events");
    }
}