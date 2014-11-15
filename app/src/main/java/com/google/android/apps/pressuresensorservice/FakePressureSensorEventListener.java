package com.google.android.apps.pressuresensorservice;

import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.util.Log;


// TODO(dek): make this inherit directly from SensorEventListener, and make the code that references this use that type as well
public class FakePressureSensorEventListener extends PressureSensorEventListener {
    private SensorManager mSensorManager;
    private AsyncTask<PressureSensorService, Integer, Long> mTask;
    FakePressureSensorEventListener(PressureSensorService pse) {
        super(pse);
        class UploadAsyncTask extends AsyncTask<PressureSensorService, Integer, Long> {
            protected Long doInBackground(PressureSensorService... pss) {
                while (!isCancelled()) {
                    if (isCancelled()) {
                        Log.i("FakePressureSensorEventListener", "already told to stop listening!");
                        break;
                    }
                    pss[0].OnPressureSensorChanged(1015.0f);
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                Log.i("FakePressureSensorEventListener", "told to stop listening");
                return 0L;
            }
        }

        mTask = new UploadAsyncTask().execute(pse);
    }

    @Override
    public void stopListening() {
        mTask.cancel(false);
    }

}