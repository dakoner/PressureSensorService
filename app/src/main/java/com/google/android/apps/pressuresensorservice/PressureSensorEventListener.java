package com.google.android.apps.pressuresensorservice;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

class PressureSensorEventListener implements SensorEventListener {
    Float mPressure;
    CountDownLatch mLatch;

    PressureSensorEventListener(CountDownLatch latch, Float pressure) {
        super();
        mLatch = latch;
        mPressure = pressure;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        Log.i("PressureSensorEventListener", "Pressure=" + event.values[0]);
        mPressure = event.values[0];
        mLatch.countDown();
    }
}