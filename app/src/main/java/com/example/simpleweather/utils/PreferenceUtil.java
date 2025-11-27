package com.example.simpleweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private static final String PREF_NAME = "weather_pref";
    private static final String KEY_LAST_CITY = "last_city";

    public static void saveLastCity(Context context, String city) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_CITY, city);
        editor.apply();
    }

    public static String getLastCity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_CITY, "Beijing");
    }
}