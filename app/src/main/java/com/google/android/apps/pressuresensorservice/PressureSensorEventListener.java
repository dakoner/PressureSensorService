package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class PressureSensorEventListener implements SensorEventListener {
    private PressureSensorService mPSE;
    private SensorManager mSensorManager;

    PressureSensorEventListener(PressureSensorService pse) {
        mPSE = pse;
        mSensorManager = (SensorManager) mPSE.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stopListening() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

    @Override
    public  void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public  void onSensorChanged(SensorEvent event) {
        mPSE.OnPressureSensorChanged(event.values[0]);
    }
}