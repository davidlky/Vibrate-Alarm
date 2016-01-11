package com.sevenhourdev.vibratealarm.Models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by davidl on 1/11/16.
 */
public class Alarm implements Serializable {
    public Time time;
    public String name;
    public Highlight color = Highlight.Red;
    public String repeat ="";
    public Date date = null;

    enum Highlight{
        Red,Blue,Green,Cyan,Yellow,Purple
    }
}
