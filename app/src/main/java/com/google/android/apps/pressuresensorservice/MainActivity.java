package com.google.android.apps.pressuresensorservice;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        CharSequence text = "Starting service via MainActivity";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent2 = new Intent(context, PressureSensorService.class);
        context.startService(intent2);
        finish();
    }

}
