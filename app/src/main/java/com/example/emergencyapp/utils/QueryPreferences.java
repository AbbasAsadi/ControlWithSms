package com.example.emergencyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    private static final String PREF_NAME = "preF";

    private static final String PREF_QUERY = "query";
    private static final String PREF_CAN_DISTURB = "canDisturb";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getQuery(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);

        if (prefs == null)
            return null;

        return prefs.getString(PREF_QUERY, null);
    }

    public static void setQuery(Context context, String query) {
        SharedPreferences prefs = getSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_QUERY, query);
        editor.apply();
    }

    public static boolean canDisturb(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);

        if (prefs == null)
            return false;

        return prefs.getBoolean(PREF_CAN_DISTURB, true);
    }

    public static void setDisturb(Context context, boolean canDisturb) {
        getSharedPreferences(context)
                .edit()
                .putBoolean(PREF_CAN_DISTURB, canDisturb)
                .apply();
    }

}
