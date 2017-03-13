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
    private static final String PROFILEUPDATE = "profileupdate";
    private static final String REGISTEROBJECT= "registerobject";
    private static final String FIRST_RUN = "firstRun";
    private static final String MY_ID = "myId";
    private static final String IS_MENTEE = "isMentee";
    private static final String LAST_MEETING_NUMBER = "lastMeetingNumber";
    private static final String TOKEN ="token";

    public static int getLastMeetingNumber(Context context){
        SharedPreferences preferences = getPreferences(context);
        return preferences.getInt(LAST_MEETING_NUMBER,1);
    }
    public static void setLastMeetingNumber(int lastMeetingNumber,Context context){
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putInt(LAST_MEETING_NUMBER,lastMeetingNumber+1).apply();
    }

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

    public static boolean isProfileUpdate(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(PROFILEUPDATE,false);
    }
    public static void setProfileUpdate(boolean isProfileUpdate,Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putBoolean(PROFILEUPDATE,isProfileUpdate).apply();
    }

    public static String RegisterObject(Context context){
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(REGISTEROBJECT,"");
    }
    public static void setRegisterObject(String isRegisterObject,Context context){
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putString(REGISTEROBJECT,isRegisterObject).apply();
    }

    public static int myId(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getInt(MY_ID,0);
    }
    public static void setMyId(int id,Context context){
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putInt(MY_ID,id).apply();
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

    public static boolean isMentee(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getBoolean(IS_MENTEE,false);
    }

    public static void setMentee(boolean isMentee,Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putBoolean(IS_MENTEE,isMentee).apply();
    }

    public static String getMyToken(Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(TOKEN,null);
    }

    public static void setMyToken(String token,Context context)
    {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit().putString(TOKEN,token).apply();
    }

    private static SharedPreferences getPreferences(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,context.MODE_PRIVATE);
        return preferences;
    }
}
