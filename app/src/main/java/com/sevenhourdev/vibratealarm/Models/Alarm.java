package com.sevenhourdev.vibratealarm.Models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidl on 1/11/16.
 */
public class Alarm implements Serializable {
    public int id;
    public Time time;
    public String name;
    public Highlight color = Highlight.Red;
    public String repeat ="";
    public Date date = null;

    enum Highlight{
        Red,Blue,Green,Cyan,Yellow,Purple
    }

    public HashMap<String,String> makeTable = new HashMap<String,String>();


    static {
        Map<Integer, String> aMap =;
        aMap.put(1, "one");
        aMap.put(2, "two");
        myMap = Collections.unmodifiableMap(aMap);
    }
}
