package com.google.android.apps.pressuresensorservice;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;


class GenerateFakePressureAsyncTask extends AsyncTask<FakePressureSensorEventListener, Integer, Long> {
    protected Long doInBackground(FakePressureSensorEventListener... fpsel) {
        Log.i("GenerateFakePressureAsyncTask", "Starting loop");

        while (!isCancelled()) {
            Log.i("UploadAsyncTask", "providing fake reading!");
            fpsel[0].onSensorChangedImplementation(1015.0f);
            /*if (isCancelled()) break;
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/
            break;
        }
        Log.i("FakePressureSensorEventListener", "told to stop listening");
        return 0L;
    }
}

public class FakePressureSensorEventListener extends BasePressureSensorEventListener {
    private AsyncTask<FakePressureSensorEventListener, Integer, Long> mTask;
    FakePressureSensorEventListener(PressureSensorService pse) {
        super(pse);

    }
    @Override
    public void startListening() {
        Log.i("FakePressureSensorEventListener", "startListening");

        mTask = new GenerateFakePressureAsyncTask().execute(this);
    }

    @Override
    public void stopListening() {
        Log.i("FakePressureSensorEventListener", "stopListening");

        mTask.cancel(true);
        mTask = null;
    }

}