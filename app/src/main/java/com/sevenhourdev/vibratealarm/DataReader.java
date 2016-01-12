package com.sevenhourdev.vibratealarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sevenhourdev.vibratealarm.Models.Alarm;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by David Liu on 1/11/2016.
 */
public class DataReader {
    Context c;
    public DataReader (Context c){
        this.c = c;
    }

    public void addToDatabase(Alarm alarm){
        DatabaseAccessor da = new DatabaseAccessor(c);
        SQLiteDatabase db = da.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name",alarm.name);
        cv.put("Color",alarm.getColorString());
        cv.put("Description",alarm.description);
        cv.put("PendingIntentID",alarm.pendingID);
        cv.put("Date",alarm.date);
        cv.put("Time",alarm.time);
        cv.put("Repeat",alarm.repeat);
        db.insert("Alarm", null,cv );
        db.close();
    }



    public ArrayList<Alarm> findAllFromDatabase(){
        DatabaseAccessor da = new DatabaseAccessor(c);
        SQLiteDatabase db = da.getReadableDatabase();
        ArrayList<Alarm> list = new ArrayList<>();
        Cursor cursor = db.query("Alarm", new String[] {"ID","Name", "Color", "Description", "PendingIntentID", "Date", "Time", "Repeat"},
                null,null,null,null,"ID");
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setColor(cursor.getString(cursor.getColumnIndex("Color")));
                alarm.name = cursor.getString(cursor.getColumnIndex("Name"));
                alarm.description = cursor.getString(cursor.getColumnIndex("Description"));
                alarm.pendingID = cursor.getString(cursor.getColumnIndex("PendingIntentID"));
                alarm.id = cursor.getInt(cursor.getColumnIndex("ID"));
                alarm.date = cursor.getString(cursor.getColumnIndex("Date"));
                alarm.time = cursor.getString(cursor.getColumnIndex("Time"));
                alarm.repeat = cursor.getString(cursor.getColumnIndex("Repeat"));
                list.add(alarm);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
