package com.example.a18440164.a1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Notification channel id
    private final String CHANNEL_ID = String.valueOf(R.string.channnel_id);
    ArrayList<EventModel> events = new ArrayList<EventModel>();
    DayEventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Calender date select
        CalendarView view = (CalendarView) findViewById(R.id.calendarView);
        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month, int date) {
                Toast.makeText(getApplicationContext(), "Selected Date : " + date + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                openForm(year, month, date);
            }
        });

        //Initialize adapter and bind to list view
        adapter = new DayEventsAdapter(this,events);
        ListView listView = findViewById(R.id.eventList);
        listView.setAdapter(adapter);

        UpdateEventList();

        //Channel initiation
        createNotificationChannel();
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = this.getPackageName();
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + packageName));
                this.startActivity(intent);
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        //Update future event count
        updateCount(new Date());
        UpdateEventList();
    }

    //open form activity with selected date
    void openForm(int year, int month, int date) {
        Intent launchEditorIntent = new Intent(this, FormActivity.class);
        EventModel model = new EventModel();
        Calendar calendar = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        //Set times to show in form
        model.StartTime = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        model.EndTime = calendar.getTime();

        //Discard time parts
        calendar.set(year, month, date, 0, 0, 0);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        //Validate for future dates
        if (today.getTime().getTime() > calendar.getTime().getTime()) {
            Toast.makeText(this, "Cannot schedule for past", Toast.LENGTH_LONG).show();
            return;
        }

        //Open form activity with passing EventModel
        launchEditorIntent.putExtra("model", (Serializable) model);
        launchEditorIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(launchEditorIntent);
    }

    //Update event count from db
    void updateCount(Date date) {
        TextView c = (TextView) findViewById(R.id.txtCount);
        String count = String.valueOf(new DBHandler(this).getEventsCount(date));
        c.setText(count + " scheduled event(s)");
    }

    //Notification channel initiate
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void UpdateEventList(){
        adapter.clear();
        adapter.addAll((List<EventModel>) new DBHandler(this).getFutureEvents(new Date()));
    }
}