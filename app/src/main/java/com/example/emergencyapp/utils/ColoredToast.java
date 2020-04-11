package com.example.emergencyapp.utils;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyapp.R;

public class ColoredToast {
    private static final int IC_CAN_NOT_DISTURB = R.drawable.ic_do_not_disturb_24dp;
    private static final int IC_CAN_DISTURB = R.drawable.ic_do_not_disturb_off_24dp;

    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;


    private Context context;
    public View view;
    Toast toast;

    public ColoredToast(Context context) {
        this.context = context;
        this.toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.colored_toast_layout, null);
        toast.setView(view);
    }

    public View getView() {
        return view;
    }

    public void setText(String text) {
        if (view == null)
            return;
        ((TextView) view.findViewById(R.id.toast_msg)).setText(text);
    }

    public void setIcon(int iconResId) {
        if (view == null)
            return;
        ((ImageView) view.findViewById(R.id.toast_icon)).setImageResource(iconResId);
    }

    public Toast getToast() {
        return toast;
    }


    public static void canDisturb(Context context, String text, int duration) {
        ColoredToast coloredToast = new ColoredToast(context);
        coloredToast.setText(text);
        coloredToast.getToast().setDuration(duration);
        coloredToast.getToast().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 50);
        coloredToast.setIcon(IC_CAN_DISTURB);
        coloredToast.getView().setBackgroundColor(Color.BLACK);
        coloredToast.getToast().show();
    }

    public static void canNotDisturb(Context context, String text, int duration) {
        ColoredToast coloredToast = new ColoredToast(context);
        coloredToast.setText(text);
        coloredToast.getToast().setDuration(duration);
        coloredToast.getToast().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 50);
        coloredToast.setIcon(IC_CAN_NOT_DISTURB);
        coloredToast.getView().setBackgroundColor(Color.RED);
        coloredToast.getToast().show();
    }
}
