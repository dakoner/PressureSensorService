package com.google.android.apps.pressuresensorservice;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends Activity {
    private BroadcastReceiver mReceiver = new PressureBroadcastReceiver(this);

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PressureSensorEventListener.PRESSURE_ACTION);
        registerReceiver(mReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Run the pressure sensor collection immediately.
        Context context = getApplicationContext();
        context.startService(new Intent(context, PressureSensorIntentService.class));

        // Also reschedule it (even if the app stops).
        Intent restartIntent = new Intent(this, PressureSensorIntentService.class);
        PendingIntent restartPendingIntent = PendingIntent.getService(this, 0, restartIntent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 600 * 1000, 600 * 1000, restartPendingIntent);
    }
}
