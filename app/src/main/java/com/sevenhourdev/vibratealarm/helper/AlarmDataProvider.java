package com.sevenhourdev.vibratealarm.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

import com.sevenhourdev.vibratealarm.MainActivity;
import com.sevenhourdev.vibratealarm.Models.Alarm;
import com.sevenhourdev.vibratealarm.receiver.AlarmReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class AlarmDataProvider {
    private Realm realm;
    private static Context mContext;
    private static AlarmDataProvider instance;
    private static List<AlarmDataCallback> mCallbacks;

    private AlarmDataProvider(Context context){
        mCallbacks = new ArrayList<>();
        realm = Realm.getDefaultInstance();
    }

    public static AlarmDataProvider getInstance(Context context, AlarmDataCallback callback){
        if (instance==null){
            instance = new AlarmDataProvider(context);
        }
        mContext = context;
        instance.addCallback(callback);
        return instance;
    }

    public void removeCallback(AlarmDataCallback callback){
        mCallbacks.remove(callback);
    }

    public void getAllAlarms(){
        realm.where(Alarm.class).findAllAsync().addChangeListener(
             new RealmChangeListener<RealmResults<Alarm>>(){
                 @Override
                 public void onChange(RealmResults<Alarm> alarms) {
                     for (AlarmDataCallback callback : mCallbacks){
                         callback.newDataSet(new ArrayList<>(Arrays.asList(alarms.toArray(new Alarm[alarms.size()]))));
                     }
                 }
             }
        );
    }

    public void addAlarm(final Alarm alarm){
        alarm.id = realm.where(Alarm.class).max("id").intValue() + 1;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm returnedRealm) {
                returnedRealm.insert(alarm);
            }
        });
        getAllAlarms();
    }

    public void removeAlarm(Alarm alarm){
        realm.where(Alarm.class).equalTo("id", alarm.id).findFirst().deleteFromRealm();
    }

    public void addCallback(AlarmDataCallback callback) {
        if(callback!=null)
            mCallbacks.add(callback);
    }

    public interface AlarmDataCallback {
        void newDataSet(ArrayList<Alarm> alarms);
    }
}
