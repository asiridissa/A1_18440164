package com.example.a18440164.a1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DayEventsAdapter extends ArrayAdapter<EventModel> {

    public DayEventsAdapter(@NonNull Context context, @NonNull ArrayList<EventModel> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventModel user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDate = (TextView) convertView.findViewById(R.id.txtItemDate);
        TextView tvName = (TextView) convertView.findViewById(R.id.textItemTitle);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.txtItemDescription);
        // Populate the data into the template view using the data object

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm aa");

        tvDate.setText(simpleDateFormat.format(user.StartTime));
        tvName.setText(user.Title);
        tvDescription.setText(user.Description);
        // Return the completed view to render on screen
        return convertView;
    }
}
