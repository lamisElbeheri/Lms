package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetSponserData;
import com.neon.lms.ResponceModel.NetSponserDataResultData;
import com.neon.lms.adapter.SponserListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivitySponserlistBinding;
import com.neon.lms.model.SponserListModel;
import com.neon.lms.model.SponserModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SponserListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private SponserListModel model;
    private ActivitySponserlistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sponserlist);
        model = new SponserListModel();
        model.setArrayList(new ArrayList<SponserModel>());
        binding.setSponserListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.sponser));

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
        binding.included.txtTitle.setText(getIntent().getStringExtra(getString(R.string.sponser)));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        sponsorListApi();
    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(new SponserListAdapter(SponserListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {



            }
        }));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((recyclerView.getLayoutManager().getChildCount()
                        + ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                        >= recyclerView.getLayoutManager().getItemCount()) {

                    if (/*model.getCount() > model.getArrayList().size() &&*/ !model.isApiCallActive()) {

                    }
                }
            }
        });

    }


    public void sponsorListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                getSponserList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetSponserData netSponserData = (NetSponserData) object;
            if (netSponserData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netSponserData.getResult().getData());
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

    private void fillArrayList(List<NetSponserDataResultData> items) {
        model.getArrayList().clear();
        SponserModel itemModel;

        for (int i = 0; i < items.size(); i++) {
            itemModel = new SponserModel();
            itemModel.setName(items.get(i).getName());
            itemModel.setId(items.get(i).getId());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setLink(items.get(i).getLink());
            itemModel.setLogo(items.get(i).getLogo());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setImage(items.get(i).getImage());
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
