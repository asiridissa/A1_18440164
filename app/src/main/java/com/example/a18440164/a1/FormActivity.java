package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EventModel model;
    DateFormat tf;
    List<Integer> reminderMinutes = Arrays.asList(1,5,15,30,60);
    List<String> reminderLabels = Arrays.asList("1 Min", "5 Min", "15 Min", "30 Min", "1 hour");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model =(EventModel)getIntent().getSerializableExtra("model");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setTitle("Add on " + df.format(model.Start));
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
        start.setText((String)tf.format(model.Start));
        end.setText((String)tf.format(model.End));

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
        //Toast.makeText(getApplicationContext(), "Selected User: "+users[position] ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public void showTimePickerDialog(View v) {
        EditText time = (EditText) v;

        Bundle bundle = new Bundle();
        bundle.putLong("time", model.Start.getTime());
        bundle.putInt("viewId", v.getId());

        DialogFragment fragment = new TimePickerFragment();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void SetTime(int hourOfDay, int minute, int viewId){
        EditText text  = (EditText)findViewById(viewId);
        if (viewId == R.id.editTextStart) {
            model.Start.setHours(hourOfDay);
            model.Start.setMinutes(minute);
            text.setText(tf.format(model.Start));
        }else{
            model.End.setHours(hourOfDay);
            model.End.setMinutes(minute);
            text.setText(tf.format(model.End));
        }
    }
}