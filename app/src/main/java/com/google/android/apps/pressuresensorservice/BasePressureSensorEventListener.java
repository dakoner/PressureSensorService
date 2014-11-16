package com.google.android.apps.pressuresensorservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class BasePressureSensorEventListener implements SensorEventListener {
    protected PressureSensorService mPSE;

    BasePressureSensorEventListener(PressureSensorService pse) {
        mPSE = pse;
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

    }
    public void stopListening() {

    }

}