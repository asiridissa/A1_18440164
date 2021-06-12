package com.example.a18440164.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventModel model =(EventModel)getIntent().getSerializableExtra("model");
        setTitle("Add on " + model.Start.toString());
        setContentView(R.layout.activity_form);
    }
}