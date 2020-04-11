package com.example.emergencyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.emergencyapp.utils.ColoredToast;
import com.example.emergencyapp.utils.QueryPreferences;
import com.suke.widget.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    private TextView mTextViewMessage;
    private TextView mTextViewStatus;
    private SwitchButton mButtonDisturb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        grandPermission();

        mTextViewMessage.setText("welcome honey :) \n");

        mButtonDisturb.setChecked(!QueryPreferences.canDisturb(this));
        setStatusMessage();

        showCustomToast();

        mButtonDisturb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                QueryPreferences.setDisturb(MainActivity.this, !isChecked);
                showCustomToast();
                setStatusMessage();
            }
        });
    }

    private void showCustomToast() {
        if (QueryPreferences.canDisturb(this)) {
            ColoredToast.canDisturb(this,
                    "do not disturb: OFF",
                    ColoredToast.LENGTH_LONG);
        } else {
            ColoredToast.canNotDisturb(this,
                    "do not disturb: ON",
                    ColoredToast.LENGTH_LONG);
        }
    }

    private void setStatusMessage() {
        if (QueryPreferences.canDisturb(this)) {
            mTextViewStatus.setTextColor(Color.BLACK);
        } else {
            mTextViewStatus.setTextColor(Color.RED);
        }
    }

    private void initUI() {
        mTextViewMessage = findViewById(R.id.text_view);
        mTextViewStatus = findViewById(R.id.status_text);
        mButtonDisturb = findViewById(R.id.switch_button);
    }

    private void grandPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                            != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS};
                requestPermissions(permissions, SMS_PERMISSION_REQUEST_CODE);
            }
        }
    }
}
