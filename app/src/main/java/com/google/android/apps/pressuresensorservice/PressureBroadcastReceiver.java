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
        mActivity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String pressureString = intent.getStringExtra(PressureSensorIntentService.EXTRA_KEY_IN);
        TextView view = (TextView) mActivity.findViewById(R.id.textView);
        view.setText(pressureString);

        CharSequence text = "Got pressure reading: " + pressureString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
};
