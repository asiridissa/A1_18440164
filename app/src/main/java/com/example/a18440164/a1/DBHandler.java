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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Event + "("
                + KEY_Id + " INTEGER PRIMARY KEY,"
                + KEY_Title + " TEXT,"
                + KEY_Description + " TEXT,"
                + KEY_Location + " TEXT,"
                + KEY_StartTime + " INTEGER,"
                + KEY_EndTime + " INTEGER, "
                + KEY_Reminder1 + " INTEGER , "
                + KEY_Reminder2+ " INTEGER , "
                + KEY_Reminder3+ " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
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
    void addEvent(EventModel event) {
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
        db.insert(TABLE_Event, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single event
//    EventModel getEvent(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_Event, new String[] { KEY_Title,
//                        KEY_Description, KEY_Location }, KEY_Title + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        EventModel event = new Event(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        // return event
//        return event;
//    }

    // code to get all contacts in a list view
    public List<EventModel> getAllEvents() {
        List<EventModel> contactList = new ArrayList<EventModel>();
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
                event.Reminder1 =cursor.getInt(6);
                event.Reminder2 =cursor.getInt(7);
                event.Reminder3 =cursor.getInt(8);

                // Adding event to list
                contactList.add(event);
            } while (cursor.moveToNext());
        }

        // return event list
        return contactList;
    }

    // code to update the single event
//    public int updateEvent(EventModel event) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_Title, event.Title);
//        values.put(KEY_Description, event.Description);
//        values.put(KEY_Location, event.Location);
//        values.put(KEY_Start, String.valueOf(event.Start));
//        values.put(KEY_End, String.valueOf(event.End));
//        values.put(KEY_Reminder1, event.Reminder1);
//        values.put(KEY_Reminder2, event.Reminder2);
//        values.put(KEY_Reminder3, event.Reminder3);
//
//        // updating row
//        return db.update(TABLE_Event, values, KEY_Title + " = ?",
//                new String[] { String.valueOf(event.getID()) });
//    }

    // Deleting single event
//    public void deleteEvent(EventModel event) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_Event, KEY_Title + " = ?",
//                new String[] { String.valueOf(event.getID()) });
//        db.close();
//    }

    // Getting contacts Count
//    public int getEventsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_Event;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }

}