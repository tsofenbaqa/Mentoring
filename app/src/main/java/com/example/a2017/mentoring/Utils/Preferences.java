package com.example.a2017.mentoring.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 2017 on 04/02/2017.
 */

public class Preferences
{
    private static final String SHARED_PREFERENCES_FILE_NAME = "chatAppPreferences";
    private static final String LOGIN = "login";
    private static final String FIRST_RUN = "firstRun";

    public static boolean isLogin(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(LOGIN,false);
    }

    public static void setLogin(boolean isLogin,Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putBoolean(LOGIN,isLogin).apply();
    }

    public static boolean isFirstRun(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(FIRST_RUN,false);
    }

    public static void setFirstRun(boolean isLogin,Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putBoolean(FIRST_RUN,isLogin).apply();
    }


    private static SharedPreferences getPreferences(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,context.MODE_PRIVATE);
        return preferences;
    }
}
