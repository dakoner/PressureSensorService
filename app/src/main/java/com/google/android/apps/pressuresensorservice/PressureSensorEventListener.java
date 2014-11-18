package com.google.android.apps.pressuresensorservice;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

class PressureSensorEventListener implements SensorEventListener {
    float mPressure = 0.f;
    CountDownLatch mLatch;

    PressureSensorEventListener(CountDownLatch latch) {
        super();
        mLatch = latch;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        Log.i("PressureSensorEventListener", "Pressure=" + event.values[0]);
        mPressure = event.values[0];
        mLatch.countDown();
    }

    public float getPressure() {
        return mPressure;
    }
}