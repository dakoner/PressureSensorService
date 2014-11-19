package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

class PressureSensorEventListener implements SensorEventListener {
    public static final String pressureKey = "PRESSURE";
    public static final String pressureAction = "com.google.android.apps.pressuresensorservice.OnPressure";
    Context mContext;
    SensorManager mSensorManager;

    PressureSensorEventListener(Context context, SensorManager sensorManager) {
        super();
        mContext = context;
        mSensorManager = sensorManager;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float pressure = event.values[0];

        Intent bi = new Intent();
        bi.setAction(pressureAction);
        bi.putExtra(pressureKey, Float.toString(pressure));
        mContext.sendBroadcast(bi);

        String pressureString = Float.toString(pressure);
        Intent uploadIntent = new Intent(mContext, UploadIntentService.class);
        uploadIntent.putExtra(pressureKey, pressureString);
        mContext.startService(uploadIntent);

        mSensorManager.unregisterListener(this);
    }
}