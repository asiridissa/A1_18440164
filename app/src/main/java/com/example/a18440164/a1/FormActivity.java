package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventModel model =(EventModel)getIntent().getSerializableExtra("model");
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
        DateFormat tf = new SimpleDateFormat("hh:mm a", Locale.US);
        start.setText((String)tf.format(model.Start));
        end.setText((String)tf.format(model.End));

    }

    public void showTimePickerDialog(View v) {
        EventModel model =(EventModel)getIntent().getSerializableExtra("model");
        DialogFragment fragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("start", model.Start.getTime());
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "timePicker");
    }
}