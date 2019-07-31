package com.neon.lms;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;


import com.facebook.FacebookSdk;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.AppPreference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


public class BaseAppClass extends Application {
    private static AppPreference preferences;

    private static BaseAppClass instance;

    public static Context f5166a = null;
    public static SharedPreferences f5167b = null;

    public static BaseAppClass getInstance() {
        return instance;
    }

    public static AppPreference getPreferences() {
        return preferences;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible = false;

    public static boolean f5169d = false;

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        instance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());

        preferences = new AppPreference(this);
        f5167b = getApplicationContext().getSharedPreferences("my_app_preference", 0);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    AppConstant.getPackageName(getApplicationContext()),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.printf("YourKeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    public static void changeLang(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }


}
