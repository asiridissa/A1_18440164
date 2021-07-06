package com.example.a18440164.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EventDB";
    private static final String TABLE_Event = "Event";
    private static final String KEY_Id = "Id";
    private static final String KEY_Title = "Title";
    private static final String KEY_Description = "Description";
    private static final String KEY_Location = "Location";
    private static final String KEY_StartTime = "StartTime";
    private static final String KEY_EndTime = "EndTime";
    private static final String KEY_Reminder1 = "Reminder1";
    private static final String KEY_Reminder2 = "Reminder2";
    private static final String KEY_Reminder3 = "Reminder3";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EventS_TABLE = "CREATE TABLE " + TABLE_Event + "("
                + KEY_Id + " INTEGER PRIMARY KEY,"
                + KEY_Title + " TEXT,"
                + KEY_Description + " TEXT,"
                + KEY_Location + " TEXT,"
                + KEY_StartTime + " INTEGER,"
                + KEY_EndTime + " INTEGER, "
                + KEY_Reminder1 + " INTEGER , "
                + KEY_Reminder2 + " INTEGER , "
                + KEY_Reminder3 + " INTEGER"
                + ")";
        db.execSQL(CREATE_EventS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Event);

        // Create tables again
        onCreate(db);
    }

    // code to add the new event
    long addEvent(EventModel event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Title, event.Title);
        values.put(KEY_Description, event.Description);
        values.put(KEY_Location, event.Location);
        values.put(KEY_StartTime, event.StartTime.getTime());
        values.put(KEY_EndTime, event.EndTime.getTime());
        values.put(KEY_Reminder1, event.Reminder1);
        values.put(KEY_Reminder2, event.Reminder2);
        values.put(KEY_Reminder3, event.Reminder3);

        // Inserting Row
        long id = db.insert(TABLE_Event, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // code to get all events in a list view
    public List<EventModel> getAllEvents() {
        List<EventModel> eventList = new ArrayList<EventModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Event;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.Id = Integer.parseInt(cursor.getString(0));
                event.Title = cursor.getString(1);
                event.Description = cursor.getString(2);
                event.Location = cursor.getString(3);
                event.StartTime = new Date(cursor.getInt(4));
                event.EndTime = new Date(cursor.getInt(5));
                event.Reminder1 = cursor.getInt(6);
                event.Reminder2 = cursor.getInt(7);
                event.Reminder3 = cursor.getInt(8);

                // Adding event to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return event list
        return eventList;
    }


    //ist of future events
    public List<String> getFutureEventSummary() {
        List<String> eventList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Event + " WHERE " + KEY_StartTime + " > " + new Date().getTime() + " AND " + KEY_Title + " IS NOT NULL";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String event = cursor.getString(1) + " " + new Date(cursor.getInt(4)).toString();
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return event list
        return eventList;
    }

    public List<EventModel> getFutureEvents(Date date) {
        List<EventModel> eventList = new ArrayList<EventModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Event + " WHERE " + KEY_StartTime + " > " + new Date().getTime() + " AND " + KEY_Title + " IS NOT NULL ORDER BY " + KEY_StartTime;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //String event = cursor.getString(1) + " " + new Date(cursor.getInt(4)).toString();
                EventModel event = new EventModel();
                event.Id = Integer.parseInt(cursor.getString(0));
                event.Title = cursor.getString(1);
                event.Description = cursor.getString(2);
                event.Location = cursor.getString(3);
                event.StartTime = new Date(cursor.getLong(4));
                event.EndTime = new Date(cursor.getLong(5));
                event.Reminder1 = cursor.getInt(6);
                event.Reminder2 = cursor.getInt(7);
                event.Reminder3 = cursor.getInt(8);
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return event list
        return eventList;
    }

    // Getting future Events Count
    public int getEventsCount(Date date) {
        String countQuery = "SELECT  * FROM " + TABLE_Event + " WHERE " + KEY_StartTime + " >= " + date.getTime();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}