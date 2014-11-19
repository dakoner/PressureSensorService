package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

class PressureSensorEventListener implements SensorEventListener {
    public static final String PRESSURE_KEY = "PRESSURE";
    public static final String PRESSURE_ACTION = "com.google.android.apps.pressuresensorservice.OnPressure";
    Context mContext;
    SensorManager mSensorManager;
  PowerManager.WakeLock mWl;

  PressureSensorEventListener(Context context, SensorManager sensorManager, PowerManager.WakeLock wl) {
        super();
        mContext = context;
        mSensorManager = sensorManager;
        mWl = wl;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float pressure = event.values[0];

        Intent bi = new Intent();
        bi.setAction(PRESSURE_ACTION);
        bi.putExtra(PRESSURE_KEY, Float.toString(pressure));
        mContext.sendBroadcast(bi);

        String pressureString = Float.toString(pressure);
        Intent uploadIntent = new Intent(mContext, UploadIntentService.class);
        uploadIntent.putExtra(PRESSURE_KEY, pressureString);
        mContext.startService(uploadIntent);

        mSensorManager.unregisterListener(this);
        mWl.release();
    }
}
