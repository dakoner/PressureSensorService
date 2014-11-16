package com.google.android.apps.pressuresensorservice;

import android.os.AsyncTask;
import android.util.Log;


class GenerateFakePressureAsyncTask extends AsyncTask<FakePressureSensorEventListener, Integer, Long> {
    protected Long doInBackground(FakePressureSensorEventListener... fpsel) {
        Log.i("GenerateFakePressureAsyncTask", "Starting loop");

        while (!isCancelled()) {
            Log.i("GenerateFakePressureAsyncTask", "providing fake reading!");
            fpsel[0].onSensorChangedImplementation(1015.0f);
            /*if (isCancelled()) break;
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/
            break;
        }
        Log.i("FakePressureSensorEventListener", "loop ended");
        Log.i("FakePressureSensorEventListener", "isCancelled:" + isCancelled());
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
        if (!isListening()) {
            mTask = new GenerateFakePressureAsyncTask().execute(this);
            mIsListening = true;
        } else {
            Log.e("FakePressureSensorEventListener", "was already listening");
        }
    }

    @Override
    public void stopListening() {
        Log.i("FakePressureSensorEventListener", "stopListening");
        if (isListening()) {
            Log.i("FakePressureSensorEventListener", "cancelling");
            mTask.cancel(true);
            mIsListening = false;

        } else {
            Log.e("FakePressureSensorEventListener", "was not already listening");
        }
    }
}