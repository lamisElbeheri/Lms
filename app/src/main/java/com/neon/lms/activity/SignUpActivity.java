package com.neon.lms.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetSignUpData;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivitySignupBinding;
import com.neon.lms.model.AccountDetailModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;
import com.neon.lms.util.CustomProgressDialog;
import com.neon.lms.util.Utility;
import com.neon.lms.util.Validation;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private AccountDetailModel model;
    private ActivitySignupBinding binding;

    CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        model = new AccountDetailModel();

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.signUp));
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, SignUpActivity.this).createProgressBar();

        binding.btnSignUp.setOnClickListener(this);

    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    //     Login Api Codeall
    public void getSignudata() {

        RetrofitClient.getInstance().getRestOkClient().
                getSignupForm("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSignUpData netAboutData = (NetSignUpData) object;
            if (netAboutData != null) {

            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.edtFname.getText().toString())) {
            if (!Validation.isStringEmpty(binding.edtLname.getText().toString())) {
//                if (!Validation.isStringEmpty(binding.edtPhone.getText().toString())) {
                if (Utility.validate(binding.edtEmail.getText().toString())) {
                    return true;
                } else
                    Toast.makeText(this, getString(R.string.enteremail), Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(this, getString(R.string.enterNumber), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, getString(R.string.enterLname), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.enterFname), Toast.LENGTH_SHORT).show();


        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

            case R.id.btnSignUp:
                if (validateInput())
                    signUpAPi();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    //     Login Api Codeall
    public void signUpAPi() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                signUp(binding.edtFname.getText().toString(),
                        binding.edtLname.getText().toString(),
                        binding.edtEmail.getText().toString(),
                        "123456",
//                        binding.edtPhone.getText().toString(),
//                        "",
//                        "",
//                        binding.edtAdd.getText().toString(),
                        binding.edtCity.getText().toString(),
//                        binding.edtpin.getText().toString(),
//                        binding.edtState.getText().toString(),
//                        binding.edtcountry.getText().toString(),
//                        ""
                        signUpcallback);
        }
        else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback signUpcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess NetSuccess = (NetSuccess) object;
            if (NetSuccess != null) {

                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                Toast.makeText(SignUpActivity.this, getString(R.string.signUpSuccess), Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


}
