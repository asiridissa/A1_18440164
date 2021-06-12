package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView view = new CalendarView(this);
        setContentView(view);

        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                Toast.makeText(getApplicationContext(),date+ "/"+month+"/"+year,Toast.LENGTH_LONG).show();
                openForm(year,month,date);
            }
        });
    }

    void openForm(int year, int month, int date){
        Intent launchEditorIntent = new Intent(this, FormActivity.class);
        EventModel model = new EventModel();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        model.Start = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        model.End = calendar.getTime();
        launchEditorIntent.putExtra("model", (Serializable) model);
        launchEditorIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(launchEditorIntent);
    }
}