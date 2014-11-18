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

        // TODO(dek): better support for multiple readings in one intent cycle.
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
        bi.setAction(getString(R.string.pressure_action));
        bi.putExtra(getString(R.string.pressure_key), Float.toString(pressure));
        sendBroadcast(bi);

        String pressureString = Float.toString(pressure);
        Intent uploadIntent = new Intent(this, UploadIntentService.class);
        uploadIntent.putExtra(getString(R.string.pressure_key, pressureString), pressureString);
        startService(uploadIntent);

    }
}