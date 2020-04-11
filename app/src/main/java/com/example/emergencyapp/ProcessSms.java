package com.example.emergencyapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.emergencyapp.utils.QueryPreferences;

public class ProcessSms {

    private static final String ABBAS_PHONE_NUMBER = "+989360520604";
    private static final String STATE_MESSAGE = "تغییر وضعیت صدا";
    private static final int SEND_SMS_REQUEST_CODE = 2;
    private String SEND_RESPONSE;

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
                    if (sender.equals(ABBAS_PHONE_NUMBER) && messageBody.equals(STATE_MESSAGE)) {
                        if (QueryPreferences.canDisturb(context)) {
                            changeRingerMode(context);
                        } else {
                            sendingSmsResponse(context);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("Exception caught", e.getMessage());
                Toast.makeText(context,
                        "Exception caught, can not read SMS",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeRingerMode(Context context) {
        AudioManager audioManager = (AudioManager) context.
                getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            Toast.makeText(context, "set to vibration", Toast.LENGTH_LONG).show();
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context, "set to normal", Toast.LENGTH_LONG).show();
        }
    }

    private void sendingSmsResponse(Context context) {
        //Getting intent and PendingIntent instance
        Intent intent = new Intent(context.getApplicationContext(), context.getClass());
        PendingIntent pi = PendingIntent.getActivity(context.getApplicationContext(),
                SEND_SMS_REQUEST_CODE,
                intent,
                0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager smsManager = SmsManager.getDefault();
        SEND_RESPONSE = "در حالت مزاحم نشوید می باشد.";
        smsManager.sendTextMessage(ABBAS_PHONE_NUMBER,
                null,
                SEND_RESPONSE,
                pi,
                null);

        Toast.makeText(context.getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }
}
