package com.google.android.apps.pressuresensorservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;


public class PressureSensorIntentService extends IntentService {
    public PressureSensorIntentService() {
        super("com.google.android.apps.pressuresensorservice.PressureSensorIntentService");
    }

    public void onHandleIntent(Intent intent) {
        SensorManager sm = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor ps = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        // Even though the intent service is exiting, we're registering an event listener that will
        // be called by the framework at  some unspecified time in the future.  We enable a partial
        // wake lock to ensure the phone does not sleep before the reading is acquired.
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PressureSensorEventListener");
        wl.acquire();
        PressureSensorEventListener pl = new PressureSensorEventListener(getApplicationContext(), sm, wl);

        sm.registerListener(pl, ps, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
