package com.neon.lms.activity;

import androidx.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivityContactusBinding;
import com.neon.lms.model.ContactUsModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;
import com.neon.lms.util.CustomProgressDialog;
import com.neon.lms.util.Utility;
import com.neon.lms.util.Validator;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "ContactUsActivity";
    private ContactUsModel model;
    private ActivityContactusBinding binding;
    CustomProgressDialog dialog;


    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contactus);
        model = new ContactUsModel();
        binding.setContactUsModel(model);
    }

    @Override
    public void setToolBar() {
        binding.included.imgBack.setVisibility(View.VISIBLE);
        binding.included.txtTitle.setText(getString(R.string.contect));


    }


    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, ContactUsActivity.this).createProgressBar();

        binding.txtSubmit.setOnClickListener(this);
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void closeActivity() {
        AppConstant.hideKeyboard(this, binding.llMian);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    private boolean validateInput() {
        if (Validator.validateString(binding.edtName.getText().toString())) {
            if (Utility.validate(binding.edtEmail.getText().toString())) {
                if (Validator.validateString(binding.edtPhone.getText().toString())) {
                    if (Validator.validateString(binding.edtComment.getText().toString())) {
                        return true;
                    } else
                        Toast.makeText(ContactUsActivity.this, getString(R.string.addComment), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ContactUsActivity.this, getString(R.string.addPhone), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(ContactUsActivity.this, getString(R.string.addEmail), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ContactUsActivity.this, getString(R.string.addName), Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSubmit:
                if (validateInput()) {
                    feedbackApicall();
                }
                break;

            case R.id.imgBack:
                closeActivity();
                break;
        }

    }

    public void feedbackApicall() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                getContactUs(
                        binding.edtName.getText().toString(),
                        binding.edtEmail.getText().toString(),
                        binding.edtPhone.getText().toString(),
                        binding.edtComment.getText().toString(),
                        callback);
        }
        else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }

    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;

            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(ContactUsActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(ContactUsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(ContactUsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();


        }
    };
}
