package com.sevenhourdev.vibratealarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.sevenhourdev.vibratealarm.AlarmActivity;

/**
 * Created by david on 1/11/2017.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"HEllo",Toast.LENGTH_SHORT).show();
        Intent newActivity = new Intent(context, AlarmActivity.class);
        newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newActivity);
    }
}
