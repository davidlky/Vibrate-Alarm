package com.sevenhourdev.vibratealarm;

import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.sevenhourdev.vibratealarm.Models.Alarm;
import com.sevenhourdev.vibratealarm.helper.AlarmDataProvider;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class AddAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            TimePicker tp = ((TimePicker) findViewById(R.id.timePicker));
            Alarm alarm = new Alarm();
            Calendar cal = Calendar.getInstance();
            int hour, minute;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = tp.getHour();
                minute = tp.getMinute();
            }else{
                hour = tp.getCurrentHour();
                minute = tp.getCurrentMinute();
            }
            //noinspection WrongConstant
            if (hour<cal.get(cal.HOUR_OF_DAY)||
                    (cal.get(cal.HOUR_OF_DAY)==hour&&minute<cal.get(Calendar.MINUTE))){
                cal.add(Calendar.DATE, 1);
            }
            cal.set(cal.HOUR_OF_DAY,hour);
            cal.set(cal.MINUTE,minute);
            cal.set(cal.SECOND,0);
            cal.set(cal.MILLISECOND,0);
            alarm.time = new Date(cal.getTimeInMillis());
            alarm.name = "Testing";
            finish();
            AlarmDataProvider.getInstance(this, null).addAlarm(alarm);
        }

        return super.onOptionsItemSelected(item);
    }
}
