package com.sevenhourdev.vibratealarm.Models;

import android.app.AlarmManager;
import android.app.PendingIntent;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {
    @PrimaryKey
    public int id;
    public Date time = new Date();
//    public Highlight color = Highlight.Red;
//    public Tag[] tags;
    public Long longitude;
    public Long latititude;
//    public RepeatInterval repeat;
//    public Day[] days;
    public boolean silent;
    public boolean vibrate;
    public boolean active;

    @Ignore
    PendingIntent intent;

    enum Highlight{
        Red,Blue,Green,Cyan,Yellow,Purple
    }

    enum RepeatInterval{
        Daily, Weekly, Monthly
    }

    enum Day{
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
    }
}
