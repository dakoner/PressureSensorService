package com.google.android.apps.pressuresensorservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        CharSequence text = "Starting service via BootReceiver";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent2 = new Intent(context, PressureSensorService.class);
        context.startService(intent2);

    }
}