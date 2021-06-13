package com.example.a18440164.a1;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Date;

//Fragment for place timepicker
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    int viewId;
    private EventModel eventModel;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long time = getArguments().getLong("time");
        viewId = getArguments().getInt("viewId");

        Date date = new Date(time);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, date.getHours(), date.getMinutes(), DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Update model and UI with selected time
        ((FormActivity) getActivity()).SetTime(hourOfDay, minute, viewId);
    }
}