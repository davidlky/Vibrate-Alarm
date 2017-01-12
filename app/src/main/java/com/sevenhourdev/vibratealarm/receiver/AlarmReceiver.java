package com.sevenhourdev.vibratealarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by david on 1/11/2017.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("hello","world");
        Toast.makeText(context,"HEllo",Toast.LENGTH_SHORT).show();
    }
}
