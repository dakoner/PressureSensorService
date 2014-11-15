package com.google.android.apps.pressuresensorservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


public class PressureSensorService extends Service {
    private PressureSensorEventListener mPSEL;
    @Override

    public void onCreate() {
        mPSEL = new FakePressureSensorEventListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("PressureSensorService", "Received start id " + startId + ": " + intent);
        return START_STICKY;
    }

    public void onDestroy() {
        mPSEL.stopListening();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void OnPressureSensorChanged(float pressure) {
        Log.i("PressureSensorService", "Pressure: " + pressure);

        String pressureString = Float.toString(pressure);

        Intent uploadIntent = new Intent(this, UploadIntentService.class);
        uploadIntent.putExtra(UploadIntentService.EXTRA_KEY_IN, pressureString);
        startService(uploadIntent);

        CharSequence text = "Got pressure reading: " + pressure;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

        Intent intent = new Intent(this, PressureSensorService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 20 * 1000, pintent);

        // must stop listening, otherwise we get multiple events
        mPSEL.stopListening();
        stopSelf();
    }

}
