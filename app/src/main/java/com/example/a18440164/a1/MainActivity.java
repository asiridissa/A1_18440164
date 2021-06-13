package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = String.valueOf(R.string.channnel_id);

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

        //Channel needed for notifications
        createNotificationChannel();

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
        Calendar today = Calendar.getInstance();

        calendar.set(year, month, date,0,0,0);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        //Check for past dates
        if(today.getTime().getTime() > calendar.getTime().getTime()) {
            Toast.makeText(this,"Cannot schedule for past",Toast.LENGTH_LONG).show();
            return;
        }

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}