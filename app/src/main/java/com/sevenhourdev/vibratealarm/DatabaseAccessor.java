package com.sevenhourdev.vibratealarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sevenhourdev.vibratealarm.Models.Alarm;

/**
 * Created by David on 1/11/2016.
 */
public class DatabaseAccessor extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "VibrateAlarm";

    // Contacts table name
    private static final String TABLE_NAME = "Alarm";

    public DatabaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(";
        for (String a : Alarm.makeTable.keySet()){
            CREATE_TABLE += " "+a+" "+Alarm.makeTable.get(a) + ",";
        }
        CREATE_TABLE = CREATE_TABLE.substring(0,CREATE_TABLE.length()-1);
        CREATE_TABLE+=")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
}