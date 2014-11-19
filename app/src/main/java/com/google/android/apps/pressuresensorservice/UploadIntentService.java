package com.google.android.apps.pressuresensorservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadIntentService extends IntentService {
    public static final String UPLOAD_INTENT = "com.google.android.apps.pressuresensorservice.UploadIntentService";

    public UploadIntentService() {
        super(UPLOAD_INTENT);
    }

    private HttpClient createHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

        return new DefaultHttpClient(conMgr, params);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        float pressure = Float.parseFloat(intent.getStringExtra(PressureSensorEventListener.pressureKey));


        HttpClient httpClient = createHttpClient();

        String url = "https://goosci-outreach.appspot.com/weather/314159";
        HttpPost post = new HttpPost(url);
        StringEntity params;
        long responseCode = -1;

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        try {
            try {
                params = new StringEntity("{\"pressure\":" + pressure + ",\"collected_at\":\"" + sdf.format(new Date()) + "\",\"us_units\":0}");
            } catch (UnsupportedEncodingException e) {
                throw e;
            }

            post.addHeader("content-type", "application/json");
            post.setEntity(params);
            HttpResponse response;

            try {
                response = httpClient.execute(post);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            Log.e("UploadIntentService: ", "Upload failed", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}