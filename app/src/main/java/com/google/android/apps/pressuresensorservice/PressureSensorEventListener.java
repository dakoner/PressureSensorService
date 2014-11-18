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
        mPressure = event.values[0];
        mLatch.countDown();
    }

    // TODO(dek): is there a better way for the owner of this class to get the latest pressure value?
    public float getPressure() {
        return mPressure;
    }
}