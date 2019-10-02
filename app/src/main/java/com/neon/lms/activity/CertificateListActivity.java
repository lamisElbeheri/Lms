package com.neon.lms.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetCertificatModel;
import com.neon.lms.ResponceModel.NetCertificatModelResult;
import com.neon.lms.adapter.CertificateListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityCertificatelistBinding;
import com.neon.lms.model.CertificateListModel;
import com.neon.lms.model.CertificateModel;
import com.neon.lms.net.DownloadTask;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CertificateListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private CertificateListModel model;
    private ActivityCertificatelistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificatelist);
        model = new CertificateListModel();
        model.setArrayList(new ArrayList<CertificateModel>());
        binding.setCertificateListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.certificate));

            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        certificateList();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CertificateListAdapter(CertificateListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {

                new DownloadTask(CertificateListActivity.this, model.getArrayList().get(position).getCertificate_link());

            }
        }));


    }


    public void certificateList() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    apiMyCertificate("",
                            callback);
        }
        else {
        Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

    }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetCertificatModel certificatModel = (NetCertificatModel) object;
            if (certificatModel.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(certificatModel.getResult());

                notyFyDat();
            } else {

//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetCertificatModelResult> items) {
        binding.progressBar.setVisibility(View.GONE);


        CertificateModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CertificateModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setCertificate_link(items.get(i).getCertificate_link());
            itemModel.setCourse_id(items.get(i).getCourse_id());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setName(items.get(i).getName());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setUrl(items.get(i).getUrl());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }
    private void notyFyDat() {
        if (model.getArrayList().size() > 0) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeActivity() {
        AppConstant.hideKeyboard(this, binding.recyclerView);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

        }
    }
}
