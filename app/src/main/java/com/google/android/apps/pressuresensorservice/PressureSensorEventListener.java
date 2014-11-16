package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class PressureSensorEventListener extends BasePressureSensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    PressureSensorEventListener(PressureSensorService pse) {
        super(pse);
        mSensorManager = (SensorManager) mPSE.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    public void startListening() {
        if (!isListening()) {
            Log.e("PressureSensorEventListener", "Was already listening");
        } else {
            mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
            mIsListening = true;
        }
    }

    @Override
    public void stopListening() {
        if (isListening()) {
            mSensorManager.unregisterListener(this);
            mIsListening = false;
        } else {
            Log.e("PressureSensorEventListener", "Was already not listening");
        }
    }

}