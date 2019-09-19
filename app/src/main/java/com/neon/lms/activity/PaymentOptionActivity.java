package com.neon.lms.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.neon.lms.BaseAppClass;
import com.neon.lms.Payment.PayPalConfig;
import com.neon.lms.R;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivityPaymentoptionBinding;
import com.neon.lms.model.PaymentOptionModel;
import com.neon.lms.net.GetOrderConformationTask;
import com.neon.lms.net.OnApiCalled;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
//import com.stripe.android.ApiResultCallback;
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.PaymentIntentResult;
//import com.stripe.android.Stripe;
//import com.stripe.android.model.PaymentIntent;

import org.json.JSONException;

import java.math.BigDecimal;

//import androidx.annotation.NonNull;

public class PaymentOptionActivity extends BaseActivity implements View.OnClickListener {
    private PaymentOptionModel model;
    private ActivityPaymentoptionBinding binding;


    public static final int PAYPAL_REQUEST_CODE = 123;

    private String paymentAmount;
    PayPalPayment thingToBuy;
//    private Stripe mStripe;

    private GetOrderConformationTask orderConformationTask;
    String coupon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        coupon = getIntent().getStringExtra("coupon");
        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID)
                .merchantName("LMS")
                .merchantPrivacyPolicyUri(
                        Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(
                        Uri.parse("https://www.example.com/legal"));
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);



//        PaymentConfiguration.init(PaymentOptionActivity.this,"pk_test_sREVqI5A1IYzKIKQWFuHRSy600Yyf3wWwf");
//        mStripe = new Stripe(this,
//                PaymentConfiguration.getInstance(PaymentOptionActivity.this).getPublishableKey());


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
                break; case
                    R.id.imgStripe:
//                callorderConformation();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    private void callorderConformation() {
        orderConformationTask = new GetOrderConformationTask(PaymentOptionActivity.this, coupon, true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String strError) {
            }
        });
    }
    /*paypal payment*/

    private void getPayment() {
        //Getting the amount from editText
        thingToBuy = new PayPalPayment(new BigDecimal(paymentAmount), "USD",
                "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaymentOptionActivity.this,
                PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

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
        else {
//            mStripe.onPaymentResult(requestCode, data,
//                    new ApiResultCallback<PaymentIntentResult>() {
//                        @Override
//                        public void onSuccess(@NonNull PaymentIntentResult result) {
//                            // If authentication succeeded, the PaymentIntent will
//                            // have user actions resolved; otherwise, handle the
//                            // PaymentIntent status as appropriate (e.g. the
//                            // customer may need to choose a new payment method)
//
//                            final PaymentIntent paymentIntent = result.getIntent();
//                            final PaymentIntent.Status status =
//                                    paymentIntent.getStatus();
//                            if (status == PaymentIntent.Status.Succeeded) {
//                                // show success UI
//                            } else if (PaymentIntent.Status.RequiresPaymentMethod
//                                    == status) {
//                                // attempt authentication again or
//                                // ask for a new Payment Method
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Exception e) {
//                            // handle error
//                        }
//                    });
        }


    }

}
