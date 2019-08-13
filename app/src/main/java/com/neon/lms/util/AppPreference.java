package com.neon.lms.util;

import android.content.Context;


public class AppPreference {

    private static String SAVE_USER_DATA = "saveUserID";
    private static String TOKENDATA = "tokenData";
    private static String USER_DATA = "userData";
    private static String USER_TOKEN = "userToken";
    private static String IS_USER_TOKEN = "isUserToken";


    public static String disclaimer = "isdisclaimer";
    public static String noti = "noti";
    public static String isRatting = "isRatting";
    public static String tokenDate = "tokenDate";

    private static String INTRO_PREF = "Intro";
    public static String isIntroScreen = "isIntroScreen";
    private Context context;


    //Master Sync Data Pref

    private static String ARN_PREF = "arnData";
    public static String token = "token";

    public AppPreference(Context context) {
        this.context = context;
    }
    public boolean getIsIntro() {
        return context.getSharedPreferences(INTRO_PREF, Context.MODE_PRIVATE).getBoolean(isIntroScreen, true);
    }

    public void saveIsIntro(boolean isIntro) {
        (context.getSharedPreferences(INTRO_PREF, Context.MODE_PRIVATE)).edit().putBoolean(isIntroScreen, isIntro).commit();

    }
    public void saveUserData(String userData) {
        context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).edit().putString(USER_DATA, userData).commit();
    }


    public String getUserData() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_DATA, null);
    }

    public boolean getIsDisclaimer() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(disclaimer, true);
    }

    public void saveIsDisclaimer(boolean isIntro) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(disclaimer, isIntro).commit();

    }


    public boolean getIsNotification() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(noti, true);
    }

    public void saveIsNotification(boolean isIntro) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(noti, isIntro).commit();

    }

    public boolean isTokenAvailable() {
        return context.getSharedPreferences(TOKENDATA, Context.MODE_PRIVATE).getBoolean(IS_USER_TOKEN, false);
    }

    public void saveIsTokenAvailable(boolean isToken) {
        (context.getSharedPreferences(TOKENDATA, Context.MODE_PRIVATE)).edit().putBoolean(IS_USER_TOKEN, isToken).commit();

    }

    public void saveToken(String token) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_TOKEN, token).commit();
    }

    public String getToken() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_TOKEN, "");
    }

    public boolean isUserLoggedIn() {
        if (getToken() == null || getToken().length() == 0)
            return false;
        else
            return true;
    }

    public static void saveFCMToken(Context context, String strEndArn) {
        context.getSharedPreferences(ARN_PREF, Context.MODE_PRIVATE).edit().putString(token, strEndArn).commit();
    }

    public static String getFCMToken(Context context) {
        return context.getSharedPreferences(ARN_PREF, Context.MODE_PRIVATE).getString(token, null);
    }


    public boolean ishShowRatting() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(isRatting, true);
    }

    public void saveSHowRatting(boolean ratting) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(isRatting, ratting).commit();

    }
    public static void saveTokenDate(Context context, long token) {
        context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).edit().putLong(tokenDate, token).commit();
    }

    public static long getTokenDate(Context context) {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getLong(tokenDate, 0);
    }

    /**
     * Clears the User's Data Stored in Preferences
     */
    public void clearUserPrefs() {
        clearUserData();

    }


    public void clearUserData() {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().clear().commit();
    }


}
