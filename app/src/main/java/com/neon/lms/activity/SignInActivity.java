package com.neon.lms.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetForgot;
import com.neon.lms.ResponceModel.TokenModel;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.TwoButtonListener;
import com.neon.lms.databinding.ActivitySigninBinding;
import com.neon.lms.model.SignInModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AlertDialogAndIntents;
import com.neon.lms.util.Constants;
import com.neon.lms.util.CustomProgressDialog;
import com.neon.lms.util.Utility;
import com.neon.lms.util.Validation;

import java.util.Arrays;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends BaseActivity implements SignInModel.BtnClick, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private SignInModel loginModel;
    private ActivitySigninBinding binding;
    public static final String EXTRA_USERNAME = "userName";
    public static final String EXTRA_PASSWORD = "password";
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;


    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    private CallbackManager mCallbackManager;

    boolean isShow;
    CustomProgressDialog dialog ;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        setModel();
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initViews() {
        dialog= new CustomProgressDialog(Constants.PROGRESS_IMAGE, SignInActivity.this).createProgressBar();

        mCallbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        binding.etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return true;
                }
                return false;
            }
        });
        binding.btnSignin.setOnClickListener(this);
        binding.showPassword.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.forgot.setOnClickListener(this);
        binding.loginGoogle.setOnClickListener(this);
        binding.demoFb.setOnClickListener(this);
// Set the initial permissions to request from the user while logging in
        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL, USER_POSTS));

        binding.loginButton.setAuthType(AUTH_TYPE);

        // Register a callback to respond to the user
        binding.loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                // Handle exception
            }
        });

    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignin:
                if (validateInput())
                    loginApi();
                break;

            case R.id.btnSignUp:
                gotSignUp();
                break;

            case R.id.showPassword:
                if (isShow)
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;

            case R.id.loginGoogle:
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
                break;

            case R.id.demoFb:
                binding.loginButton.performClick();
                break;

            case R.id.forgot:
                AlertDialogAndIntents.forgotDialog(SignInActivity.this, new TwoButtonListener() {
                    @Override
                    public void positiveClick() {
                        forgotApi();
                    }

                    @Override
                    public void negativeClick() {

                    }
                });
                break;

        }
    }


    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.etEmailorMobile.getText().toString())) {
            if (Utility.validate(binding.etEmailorMobile.getText().toString())) {

                if (!Validation.isStringEmpty(binding.etPassword.getText().toString())) {
                    return true;
                } else
                    Toast.makeText(this, getString(R.string.enterpassword), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, getString(R.string.validemail), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.enteremail), Toast.LENGTH_SHORT).show();


        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.e("data", data.toString());
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            GoogleSignInAccount account = result.getSignInAccount();

        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            gotoProfile(result.getSignInAccount().getIdToken());
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.signcancel), Toast.LENGTH_LONG).show();
        }
    }

    private void gotoProfile(String token) {
        socialLogin("google", token);
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void gotSignUp() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getString(R.string.email_hint), loginModel.getEmail());
        savedInstanceState.putString(getString(R.string.password_hint), loginModel.getPass());
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        loginModel.setEmail(savedInstanceState.getString(getString(R.string.email_hint)));
//        loginModel.setPass(savedInstanceState.getString(getString(R.string.password_hint)));
    }


    private void setModel() {
        loginModel = new SignInModel(this);

    }


    @Override
    public void onSignUpBtnClick() {
//        startActivityForResult(new Intent(this, SignUpActivity.class), Constants.LOGIN_REQUEST_CODE);
//        //  startActivityForResult(new Intent(this, SignUpAnimActivity.class), CustomConstants.LOGIN_REQUEST_CODE);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    /**
     * Open"s Main Activity {@link MainActivity}
     */
    private void openMainActivity() {

        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);

    }


    //     Login Api Codeall
    public void loginApi() {
        dialog.setCancelable(false);
        dialog.show();
        RetrofitClient.getInstance().getRestOkClient().
                passwordLogin("password",
                        Constants.CLIENT_ID,
                        Constants.CLIENT_SECRET,
                        binding.etEmailorMobile.getText().toString(),
                        binding.etPassword.getText().toString(),
                        "*",
                        callback);
    }

    //     Login Api Codeall
    public void socialLogin(String provider, String token) {
        dialog.setCancelable(false);
        dialog.show();
        RetrofitClient.getInstance().getRestOkClient().
                socialLogin("social",
                        Constants.CLIENT_ID,
                        Constants.CLIENT_SECRET,
                        "*",
                        provider,
                        token,

                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            TokenModel tokenModel = (TokenModel) object;
            if (tokenModel != null) {
                BaseAppClass.getPreferences().saveToken(tokenModel.getAccess_token());
                BaseAppClass.getPreferences().saveIsTokenAvailable(true);
                openMainActivity();

            } else {
                Toast.makeText(SignInActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    //     Login Api Codeall
    public void forgotApi() {
        dialog.setCancelable(false);
        dialog.show();
        RetrofitClient.getInstance().getRestOkClient().
                forgotPassword(binding.etEmailorMobile.getText().toString(),
                        forgotcallback);
    }

    private final retrofit.Callback forgotcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetForgot netForgot = (NetForgot) object;
            if (netForgot != null) {

            } else {
                Toast.makeText(SignInActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


}
