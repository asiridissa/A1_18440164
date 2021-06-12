package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {
    EventModel model;
    DateFormat tf;

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