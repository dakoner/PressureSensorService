package com.google.android.apps.pressuresensorservice;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class PressureSensorEventListener implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private PressureSensorService mPSE;

    PressureSensorEventListener(PressureSensorService pse) {
        mPSE = pse;
        mSensorManager = (SensorManager) mPSE.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager.registerListener(this,
                mPressure,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stopListening() {
        mSensorManager.unregisterListener(this);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        mPSE.OnPressureSensorChanged(event.values[0]);
    }



}