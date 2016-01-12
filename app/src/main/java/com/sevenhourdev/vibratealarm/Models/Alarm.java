package com.sevenhourdev.vibratealarm.Models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidl on 1/11/16.
 */
public class Alarm implements Serializable {
    public int id;
    public String name;
    public Highlight color = Highlight.Red;
    public String description ="";
    public String pendingID;
    public String date;
    public String time;
    public String repeat;

    enum Highlight{
        Red,Blue,Green,Cyan,Yellow,Purple
    }

    public static HashMap<String,String> makeTable = new HashMap<String,String>();

    public String getColorString(){
        switch (color) {
            case Red:
                return "red";
            case Blue:
                return "blue";
            case Green:
                return "green";
            case Cyan:
                return "cyan";
            case Yellow:
                return "yellow";
            case Purple:
                return "purple";
            default:
                return "red";
        }
    }

    public void setColor(String color){
        if(color.equals("red")){
            this.color = Highlight.Red;
        }
        if(color.equals("blue")){
            this.color = Highlight.Blue;
        }
        if(color.equals("green")){
            this.color = Highlight.Green;
        }
        if(color.equals("cyan")){
            this.color = Highlight.Cyan;
        }
        if(color.equals("yellow")){
            this.color = Highlight.Yellow;
        }
        if(color.equals("purple")){
            this.color = Highlight.Purple;
        }
    }


    static {
        HashMap<String, String> aMap= new HashMap<>();
        aMap.put("ID", "Integer PRIMARY KEY AUTOINCREMENT");
        aMap.put("Name", "TEXT");
        aMap.put("Color", "TEXT");
        aMap.put("Description", "TEXT");
        aMap.put("PendingIntentID", "TEXT");
        aMap.put("Date", "TEXT");
        aMap.put("Time", "TEXT");
        aMap.put("Repeat", "TEXT");
        makeTable = new HashMap<>(aMap);
    }
}
