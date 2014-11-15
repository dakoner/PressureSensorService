package com.google.android.apps.pressuresensorservice;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

// TODO(dek): make this inherit directly from SensorEventListener, and make the code that references this use that type as well
public class FakePressureSensorEventListener extends PressureSensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private PressureSensorService mPSE;

    FakePressureSensorEventListener(PressureSensorService pse) {
        super(pse);
        class UploadAsyncTask extends AsyncTask<PressureSensorService, Integer, Long> {
            PressureSensorService mPSS;

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

        new UploadAsyncTask().execute(mPSE);
    }
    public void stopListening() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

}