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


import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.AppPreference;
import com.neon.lms.util.Constants;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Crashlytics());
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)
                .build();
        Twitter.initialize(twitterConfig);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.


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

    public static void changeLang(Context context, int lang) {
        Locale myLocale = new Locale(getCode(lang));
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static String getCode(int lang) {
        String languageCode = null;
        if (lang == Constants.ENGLISH)
            languageCode = "en";
        else if (lang == Constants.AREBIC)
            languageCode = "ar";
        else if (lang == Constants.SPANISH)
            languageCode = "es";
        else if (lang == Constants.FRANCH)
            languageCode = "fr";

        return languageCode;

    }


}
