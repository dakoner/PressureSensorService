package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class PressureSensorEventListener extends BasePressureSensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;

    PressureSensorEventListener(PressureSensorService pse) {
        super(pse);
    }



    public void startListening() {
        mSensorManager = (SensorManager) mPSE.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListening() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
            mPressure = null;
        }
    }

}