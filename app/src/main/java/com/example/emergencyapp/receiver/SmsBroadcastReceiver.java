package com.example.emergencyapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.emergencyapp.ProcessSms;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "Received: " + intent.getAction();
        Log.d(TAG, msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            new ProcessSms(context, intent);
        }
    }
}
