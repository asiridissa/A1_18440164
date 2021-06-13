package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.app.AlarmManager ;
import android.app.PendingIntent ;
import android.content.Intent ;
import android.os.Bundle ;
import android.view.View ;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar ;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EventModel model;
    DateFormat tf;
    List<Integer> reminderMinutes = Arrays.asList(0,1,5,10,15,30,60);
    List<String> reminderLabels = Arrays.asList("None", "1 Min", "5 Min","10 Min", "15 Min", "30 Min", "1 hour");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model =(EventModel)getIntent().getSerializableExtra("model");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setTitle("Add on " + df.format(model.StartTime));
        setContentView(R.layout.activity_form);

//        EditText title  = (EditText)findViewById(R.id.editTextTitle);
//        EditText description  = (EditText)findViewById(R.id.editTextDescription);
//        EditText location  = (EditText)findViewById(R.id.editTextLocation);
        EditText start  = (EditText)findViewById(R.id.editTextStart);
        EditText end  = (EditText)findViewById(R.id.editTextEnd);

//        title.setText(model.Title);
//        description.setText(model.Description);
//        location.setText(model.Location);
        tf = new SimpleDateFormat("hh:mm a", Locale.US);
        start.setText((String)tf.format(model.StartTime));
        end.setText((String)tf.format(model.EndTime));

        //Set spinner data source
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, reminderLabels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);

        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(this);
        spin2.setAdapter(adapter);
        spin2.setOnItemSelectedListener(this);
        spin3.setAdapter(adapter);
        spin3.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Set Reminder values
        switch (arg0.getId()){
            case R.id.spinner1:
                model.Reminder1 = ((Switch) findViewById(R.id.switch1)).isChecked() ? reminderMinutes.get(position) : 0;
                break;
            case R.id.spinner2:
                model.Reminder2 = ((Switch) findViewById(R.id.switch2)).isChecked() ? reminderMinutes.get(position) : 0;
                break;
            case R.id.spinner3:
                model.Reminder3 = ((Switch) findViewById(R.id.switch3)).isChecked() ? reminderMinutes.get(position) : 0;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // No code needed here
    }

    public void showTimePickerDialog(View v) {
        EditText time = (EditText) v;

        Bundle bundle = new Bundle();
        bundle.putLong("time", model.StartTime.getTime());
        bundle.putInt("viewId", v.getId());

        DialogFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void SetTime(int hourOfDay, int minute, int viewId){
        EditText text  = (EditText)findViewById(viewId);
        if (viewId == R.id.editTextStart) {
            model.StartTime.setHours(hourOfDay);
            model.StartTime.setMinutes(minute);
            text.setText(tf.format(model.StartTime));
        }else{
            model.EndTime.setHours(hourOfDay);
            model.EndTime.setMinutes(minute);
            text.setText(tf.format(model.EndTime));
        }
    }

    public void SaveEvent(View v){
        DBHandler db = new DBHandler(this);
        model.Title = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();
        model.Description = ((EditText)findViewById(R.id.editTextDescription)).getText().toString();
        model.Location = ((EditText)findViewById(R.id.editTextLocation)).getText().toString();
        //Times set in SetTime
        //Reminder values set in onItemSelected

        if(model.Title.isEmpty()) {
            Toast.makeText(this, "Title required", Toast.LENGTH_LONG).show();
            return;
        }

        long id = db.addEvent(model);
        Toast.makeText(this,"Saved " + model.Title,Toast.LENGTH_SHORT).show();
        if(model.Reminder1 > 0)
            setNotification(model.Title, model.Description, ((Long)id).intValue(), new Date(model.StartTime.getTime() - (model.Reminder1 * 60 * 1000)));
        if(model.Reminder2 > 0)
            setNotification(model.Title, model.Description, ((Long)id).intValue(), new Date(model.StartTime.getTime() - (model.Reminder2 * 60 * 1000)));
        if(model.Reminder3 > 0)
            setNotification(model.Title, model.Description, ((Long)id).intValue(), new Date(model.StartTime.getTime() - (model.Reminder3 * 60 * 1000)));
        finish();
    }

    void setNotification(String title, String detail, int id, Date time){
        Date now = new Date();
        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(NotifyWorker.class)
                        .setInitialDelay(time.getTime() - now.getTime(), TimeUnit.MILLISECONDS)
                        .setInputData(new Data.Builder()
                                .putInt(getString(R.string.notification_id),id)
                                .putString(getString(R.string.notification_title),title)
                                .putString(getString(R.string.notification_detail),detail)
                                .putLong(getString(R.string.notification_time),time.getTime())
                                .build())
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(uploadWorkRequest);

    }
}