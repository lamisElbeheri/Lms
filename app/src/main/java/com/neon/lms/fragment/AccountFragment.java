package com.neon.lms.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.TokenModel;
import com.neon.lms.activity.SignInActivity;
import com.neon.lms.adapter.OrderListAdapter;
import com.neon.lms.basecomponent.BaseFragment;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.FragmentAccountDetailBinding;
import com.neon.lms.databinding.FragmentOrderlistBinding;
import com.neon.lms.model.AccountDetailModel;
import com.neon.lms.model.OrderModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class AccountFragment extends BaseFragment {
    public static final String TAG = "Home";
    private AccountDetailModel model;
    private FragmentAccountDetailBinding binding;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_detail, container, false);
        model = new AccountDetailModel();
        binding.setAccountDetailModel(model);

        initView();


        return binding.getRoot();

    }

    private void initView() {
        setUserData();
    }

    private void setUserData() {
        binding.txtFname.setText(BaseAppClass.getPreferences().getUserFirstName());
        binding.txtLname.setText(BaseAppClass.getPreferences().getUserName());
        binding.txtPhone.setText(BaseAppClass.getPreferences().getUserMobile());
        binding.txtEmail.setText(BaseAppClass.getPreferences().getUserEmail());
        binding.line1Address.setText(BaseAppClass.getPreferences().getUserLine1());
        binding.txtCity.setText(BaseAppClass.getPreferences().getUserCity());
        binding.txtState.setText(BaseAppClass.getPreferences().getUserState());
        binding.txtCountry.setText(BaseAppClass.getPreferences().getUserCountry());
        binding.txtPin.setText(BaseAppClass.getPreferences().getPincode());
    }


}

