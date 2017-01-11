package com.sevenhourdev.vibratealarm.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sevenhourdev.vibratealarm.Models.Alarm;
import com.sevenhourdev.vibratealarm.helper.AlarmDataProvider;
import com.sevenhourdev.vibratealarm.receiver.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by david on 1/11/2017.
 */

public class AlarmBackgroundService extends Service implements AlarmDataProvider.AlarmDataCallback {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private AlarmDataProvider mAlertProvider;
    private Alarm currentAlarm;

    public class AlarmBinder extends Binder {
        public AlarmBackgroundService getService(Context context) {
            setContext(context);
            return AlarmBackgroundService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("hello","bind");
        return new AlarmBinder();
    }

    public AlarmBackgroundService(){
    }

    public void setContext(Context context){
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mAlertProvider = AlarmDataProvider.getInstance(context,this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAlertProvider.removeCallback(this);
    }

    @Override
    public void newDataSet(ArrayList<Alarm> alarms) {
        Log.d("hello","newdata");
        currentAlarm = null;
        alarmManager.cancel(alarmIntent);
        Date cal = new Date();
        for (Alarm alarm: alarms) {
            if (cal.before(alarm.time)){
                if (currentAlarm == null) {
                    currentAlarm = alarm;
                }
                if (currentAlarm.time.after(alarm.time)) {
                    currentAlarm = alarm;
                }
            }
        }
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentAlarm.time.setTime(System.currentTimeMillis()+1000);
            Log.d("hello",""+currentAlarm.time.getTime());
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(currentAlarm.time.getTime(),
                    alarmIntent),alarmIntent);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, currentAlarm.time.getTime(),
                    alarmIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, currentAlarm.time.getTime(),
                    alarmIntent);
        }
    }
}
