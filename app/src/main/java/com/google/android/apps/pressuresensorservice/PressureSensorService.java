package com.google.android.apps.pressuresensorservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;


public class PressureSensorService extends Service {
    public static final String EXTRA_KEY_IN = "EXTRA_IN";

    private BasePressureSensorEventListener mPSEL;
    private Intent mRestartIntent;
    private PendingIntent mRestartPendingIntent;

    @Override
    public void onCreate() {
        Log.i("PressureSensorService", "onCreate");
        mRestartIntent = new Intent(this, PressureSensorService.class);
        mRestartPendingIntent = PendingIntent.getService(this, 0, mRestartIntent, 0);
        mPSEL = new PressureSensorEventListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("PressureSensorService", "Received start id " + startId + ": " + intent);
        if (!mPSEL.isListening())
            mPSEL.startListening();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("PressureSensorService", "onDestroy");
        mPSEL.stopListening();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void OnPressureSensorChanged(float pressure) {
        Log.i("PressureSensorService", "Pressure: " + pressure);
        mPSEL.stopListening();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_IN, Float.toString(pressure));
        intent.setAction("com.google.android.apps.pressuresensorservice.OnPressure");
        sendBroadcast(intent);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 20 * 1000, mRestartPendingIntent);

        /*
        String pressureString = Float.toString(pressure);

        Log.i("PressureSensorService", "Starting upload intent.");

        Intent uploadIntent = new Intent(this, UploadIntentService.class);
        uploadIntent.putExtra(UploadIntentService.EXTRA_KEY_IN, pressureString);
        startService(uploadIntent);
        */

        stopSelf();
    }
}
