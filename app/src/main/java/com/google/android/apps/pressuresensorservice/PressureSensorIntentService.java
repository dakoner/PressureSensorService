package com.google.android.apps.pressuresensorservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;


public class PressureSensorIntentService extends IntentService {
    public static final String EXTRA_KEY_IN = "EXTRA_IN";

    public PressureSensorIntentService() {
        super("com.google.android.apps.pressuresensorservice.PressureSensorIntentService");
    }

    public void onHandleIntent(Intent intent) {
        SensorManager sm = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor ps = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);

        CountDownLatch latch = new CountDownLatch(1);
        PressureSensorEventListener pl = new PressureSensorEventListener(latch);

        sm.registerListener(pl, ps, SensorManager.SENSOR_DELAY_NORMAL);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        sm.unregisterListener(pl);

        float pressure = pl.getPressure();

        Intent bi = new Intent();
        bi.setAction("com.google.android.apps.pressuresensorservice.OnPressure");
        bi.putExtra(EXTRA_KEY_IN, Float.toString(pressure));
        sendBroadcast(bi);

        String pressureString = Float.toString(pressure);
        Intent uploadIntent = new Intent(this, UploadIntentService.class);
        uploadIntent.putExtra(UploadIntentService.EXTRA_KEY_IN, pressureString);
        startService(uploadIntent);

        Log.i("PressureSensorIntentService", "Pressure=" + pressure);
    }
}