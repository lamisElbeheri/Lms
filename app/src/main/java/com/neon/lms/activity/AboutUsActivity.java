package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetAboutData;
import com.neon.lms.ResponceModel.NetForgot;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivityAboutusBinding;
import com.neon.lms.model.AbouUsModel;
import com.neon.lms.net.RetrofitClient;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private AbouUsModel model;
    private ActivityAboutusBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);
        model = new AbouUsModel();

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.about));
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        aboutDataAPI();
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    //     Login Api Codeall
    public void aboutDataAPI() {
        RetrofitClient.getInstance().getRestOkClient().
                getAboutUs("about-us",
                        forgotcallback);
    }

    private final retrofit.Callback forgotcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetAboutData netAboutData = (NetAboutData) object;
            if (netAboutData != null) {
                binding.txtTitle.setText(netAboutData.getResult().getTitle());
                binding.txtDes.setText(netAboutData.getResult().getContent());

            } else {
                Toast.makeText(AboutUsActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(AboutUsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


}
