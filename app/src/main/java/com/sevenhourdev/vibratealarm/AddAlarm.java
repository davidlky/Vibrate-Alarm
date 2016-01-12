package com.sevenhourdev.vibratealarm;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sevenhourdev.vibratealarm.Models.Alarm;

public class AddAlarm extends AppCompatActivity {

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
            alarm.time = tp.getCurrentHour()+":"+tp.getCurrentMinute();
            alarm.name = "Testing";
            alarm.description="";
            DataReader dr = new DataReader(this);
            dr.addToDatabase(alarm);
            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
            i.putExtra(AlarmClock.EXTRA_HOUR, tp.getCurrentHour());
            i.putExtra(AlarmClock.EXTRA_MINUTES, tp.getCurrentMinute());
            i.putExtra(AlarmClock.VALUE_RINGTONE_SILENT, true);
            i.putExtra(AlarmClock.EXTRA_VIBRATE, true);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
