package com.google.android.apps.pressuresensorservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;


public class PressureSensorIntentService extends IntentService {
    public PressureSensorIntentService() {
        super("com.google.android.apps.pressuresensorservice.PressureSensorIntentService");
    }

    public void onHandleIntent(Intent intent) {
        SensorManager sm = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor ps = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);

        PressureSensorEventListener pl = new PressureSensorEventListener(getApplicationContext(), sm);

        sm.registerListener(pl, ps, SensorManager.SENSOR_DELAY_NORMAL);
    }
}