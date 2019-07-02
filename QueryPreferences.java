package com.example.priti.smartbin;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;

public class QueryPreferences {
    private static final String USER_NAME_PREFERENCE="com.example.priti.smartbin.user_name_shared_preference";
    private static final String USER_PASSWORD_PREFERENCE="com.example.priti.smartbin.user_password_shared_preference";
    private static final String REMEMBER_U_AND_P_PREFERENCE="com.example.priti.smartbin.remember_user_and_password_shared_preference";
    private static final String APP_PASSWORD_PREFERENCE="com.example.priti.smartbin.app_password_shared_preference";
    private static final String GARBAGE_LEVEL_LIMIT="com.example.priti.smartbin.garbage_level_limit";
    private static final String MESSAGE_ENABLED_NOTIFY="com.example.priti.smartbin.message_enabled_notify";
    private static final String MESSAGE_NUMBER="com.example.priti.smartbin.message_number";

    public static String getUserName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(USER_NAME_PREFERENCE, null);
    }

    public static String getUserPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(USER_PASSWORD_PREFERENCE, null);
    }

    public static void setUserNameAndPassword(Context context, String userName, String password) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USER_NAME_PREFERENCE, userName)
                .putString(USER_PASSWORD_PREFERENCE,password)
                .apply();
    }
public static void setIsRemeberUserAndPassword(Context context,boolean isTrue){
    PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(REMEMBER_U_AND_P_PREFERENCE, isTrue)
            .apply();
}
public static boolean isRemeberUserAndPassword(Context context){
    return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(REMEMBER_U_AND_P_PREFERENCE, false);
}
    public static void setAppPassword(Context context, String password) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(APP_PASSWORD_PREFERENCE,password)
                .apply();
    }

    public static String getAppPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(APP_PASSWORD_PREFERENCE, "12345");
    }

    public static void setGarbageLevelLimit(Context context, int limit) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(GARBAGE_LEVEL_LIMIT,limit)
                .apply();
    }

    public static int getGarbageLevelLimit(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(GARBAGE_LEVEL_LIMIT, 40);
    }

    public static void setMessageEnabledNotify(Context context, boolean isEnabled) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(MESSAGE_ENABLED_NOTIFY,isEnabled)
                .apply();
    }

    public static boolean isMessageEnabledNotify(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(MESSAGE_ENABLED_NOTIFY, false);
    }

    public static void setMessageNumber(Context context, String number) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(MESSAGE_NUMBER,number)
                .apply();
    }

    public static String getMessageNumber(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(MESSAGE_NUMBER, "null");
    }

}
