package com.example.emergencyapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.emergencyapp.ProcessSms;

public class StartupReceiver extends BroadcastReceiver {
    //public static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

//        Toast.makeText(context, "in " + TAG, Toast.LENGTH_LONG).show();
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            new ProcessSms(context, intent);
        }


    }
}
