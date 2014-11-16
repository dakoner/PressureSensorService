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
    private BasePressureSensorEventListener mPSEL;
    private Intent mRestartIntent;
    private PendingIntent mRestartPendingIntent;

    @Override
    public void onCreate() {
        Log.i("PressureSensorService", "onCreate");
        mRestartIntent = new Intent(this, PressureSensorService.class);
        mRestartPendingIntent = PendingIntent.getService(this, 0, mRestartIntent, 0);
        mPSEL = new FakePressureSensorEventListener(this);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 20 * 1000, 20 * 1000, mRestartPendingIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("PressureSensorService", "Received start id " + startId + ": " + intent);
        mPSEL.startListening();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("PressureSensorService", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void OnPressureSensorChanged(float pressure) {
        Log.i("PressureSensorService", "Pressure: " + pressure);
        mPSEL.stopListening();

/*        String pressureString = Float.toString(pressure);
*/
 /*
        Log.i("PressureSensorService", "Starting upload intent.");

        Intent uploadIntent = new Intent(this, UploadIntentService.class);
        uploadIntent.putExtra(UploadIntentService.EXTRA_KEY_IN, pressureString);
        startService(uploadIntent);
        */
/*
        CharSequence text = "Got pressure reading: " + pressure;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
*/

        /*

        // must stop listening, otherwise we get multiple events
        Log.i("PressureSensorService", "Stopping listening.");
        mPSEL.stopListening();

        Log.i("PressureSensorService", "Starting restart intent.");

        Log.i("PressureSensorService", "pintent=" + mRestartPendingIntent);



        Log.i("PressureSensorService", "Finishing OnPressureSensorChanged");

        stopSelf();
        */

    }

}
