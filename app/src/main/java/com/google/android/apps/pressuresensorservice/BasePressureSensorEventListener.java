package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class BasePressureSensorEventListener implements SensorEventListener {
    protected PressureSensorService mPSE;
    protected boolean mIsListening;

    BasePressureSensorEventListener(PressureSensorService pse) {
        mPSE = pse;
    }

    public boolean isListening() {
        return mIsListening;
    }
    @Override
    public  void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public  void onSensorChanged(SensorEvent event) {
        onSensorChangedImplementation(event.values[0]);
    }

    public void onSensorChangedImplementation(float pressure) {
        mPSE.OnPressureSensorChanged(pressure);
    }

    public void startListening() {
        if (!isListening())
            Log.e("BasePressureSensorEventListening", "was already listening");
        mIsListening = true;
    }
    public void stopListening() {
        if (!isListening())
            Log.e("BasePressureSensorEventListening", "was already not listening");
        mIsListening = false;

    }

}