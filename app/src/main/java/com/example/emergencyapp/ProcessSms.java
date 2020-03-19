package com.example.emergencyapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ProcessSms {

    private static final String ABBAS_PHONE_NUMBER = "+989360520604";
    private static final String STATE_MESSAGE = "حالت:روشن";

    public ProcessSms(Context context, Intent intent) {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsMessages = null;
        String sender;
        if (bundle != null) {
            //---retrieve the SMS message received---
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsMessages = new SmsMessage[pdus.length];
                for (int i = smsMessages.length - 1; i > smsMessages.length - 3; i--) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sender = smsMessages[i].getOriginatingAddress();
                    String messageBody = smsMessages[i].getMessageBody();
                    /*Toast.makeText(context, "sender: " + sender, Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "body: " + messageBody, Toast.LENGTH_LONG).show();*/
                    if (sender.equals(ABBAS_PHONE_NUMBER) && messageBody.equals(STATE_MESSAGE)) {
                        AudioManager audioManager = (AudioManager) context.
                                getApplicationContext()
                                .getSystemService(Context.AUDIO_SERVICE);
                        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            Toast.makeText(context, "set to vibration", Toast.LENGTH_SHORT).show();
                        } else {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Toast.makeText(context, "set to normal", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Exception caught", e.getMessage());
            }
        }
    }
}
