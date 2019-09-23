package com.neon.lms.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.basecomponent.BaseFragment;
import com.neon.lms.databinding.FragmentAccountDetailBinding;
import com.neon.lms.model.AccountDetailModel;


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

