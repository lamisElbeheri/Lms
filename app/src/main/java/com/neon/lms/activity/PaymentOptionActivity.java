package com.neon.lms.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.BaseAppClass;
import com.neon.lms.Payment.PayPalConfig;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetAboutData;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivityAboutusBinding;
import com.neon.lms.databinding.ActivityPaymentoptionBinding;
import com.neon.lms.model.AbouUsModel;
import com.neon.lms.model.PaymentOptionModel;
import com.neon.lms.net.RetrofitClient;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaymentOptionActivity extends BaseActivity implements View.OnClickListener {
    private PaymentOptionModel model;
    private ActivityPaymentoptionBinding binding;


    public static final int PAYPAL_REQUEST_CODE = 123;

    private String paymentAmount;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this, PayPalService.class);
//
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        startService(intent);

    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paymentoption);
        model = new PaymentOptionModel();

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.payment));
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        paymentAmount = getIntent().getStringExtra("total");
        binding.txtTotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + paymentAmount);
        binding.imgPaypal.setOnClickListener(this);
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;
            case R.id.imgPaypal:
                getPayment();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    /*paypal payment*/

    private void getPayment() {
        //Getting the amount from editText

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "", "",
                PayPalPayment.PAYMENT_INTENT_ORDER);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, PaymentOptionActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

}
