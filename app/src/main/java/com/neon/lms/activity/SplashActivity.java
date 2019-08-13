package com.neon.lms.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.util.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;


public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int delay = 500;
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splash);
        initView();

    }


    private void initView() {
        callHandler(delay);
    }


    /**
     * Check and starts Location service if not already running.
     */



    public void callHandler(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsUserLoggedIn();
            }
        }, delay);
    }

    /**
     * Check if the use is logged IN Or Not
     */

    private void checkIsUserLoggedIn() {

        if (!BaseAppClass.getPreferences().isUserLoggedIn()) {

                openSignInActivity();
//                openMainActivity();
        } else {
            openMainActivity();
        }
    }


    private void openSignInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    /**
     * Open's USer Specific Activity
     */

    private void openMainActivity() {
//        AppConstant.openUserSpecificScreen(this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.txtRefress:
//                initView();
//                break;
        }
    }





}
