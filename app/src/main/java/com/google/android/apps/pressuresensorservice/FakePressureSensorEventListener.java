package com.google.android.apps.pressuresensorservice;


import android.hardware.Sensor;

import android.hardware.SensorManager;
import android.os.AsyncTask;


// TODO(dek): make this inherit directly from SensorEventListener, and make the code that references this use that type as well
public class FakePressureSensorEventListener extends PressureSensorEventListener {
    private SensorManager mSensorManager;

    FakePressureSensorEventListener(PressureSensorService pse) {
        super(pse);
        class UploadAsyncTask extends AsyncTask<PressureSensorService, Integer, Long> {

            protected Long doInBackground(PressureSensorService... pss) {
                while (true) {
                    // must call in UI thread
                    pss[0].OnPressureSensorChanged(1015.0f);
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        new UploadAsyncTask().execute(pse);
    }
    public void stopListening() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

}