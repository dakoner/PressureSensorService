package com.google.android.apps.pressuresensorservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class PressureBroadcastReceiver extends BroadcastReceiver {
    Activity mActivity;

    PressureBroadcastReceiver(Activity activity) {
        super();
        mActivity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String pressureString = intent.getStringExtra(PressureSensorEventListener.PRESSURE_KEY);
        TextView view = (TextView) mActivity.findViewById(R.id.textView);
        view.setText(pressureString);
    }
}
