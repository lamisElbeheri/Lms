package com.neon.lms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetBlogData;
import com.neon.lms.ResponceModel.NetBlogDataBlogData;
import com.neon.lms.ResponceModel.NetCartListResultCourses;
import com.neon.lms.ResponceModel.NetOfferData;
import com.neon.lms.ResponceModel.NetOfferDataCoupons;
import com.neon.lms.adapter.BlogListAdapter;
import com.neon.lms.adapter.OfferListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityBloglistBinding;
import com.neon.lms.databinding.ActivityOfferlistBinding;
import com.neon.lms.model.CartModel;
import com.neon.lms.model.OfferListModel;
import com.neon.lms.model.BlogModel;
import com.neon.lms.model.OfferListModel;
import com.neon.lms.model.OfferModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class OfferListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private OfferListModel model;
    private ActivityOfferlistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offerlist);
        model = new OfferListModel();
        model.setArrayList(new ArrayList<OfferModel>());
        binding.setOfferListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.blog));

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
        blogListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new OfferListAdapter(OfferListActivity.this,
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


    public void blogListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        RetrofitClient.getInstance().getRestOkClient().
                getOfferList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetOfferData netOfferData = (NetOfferData) object;
            if (netOfferData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netOfferData.getCoupons());

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
    private void fillArrayList(List<NetOfferDataCoupons> items) {

        OfferModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new OfferModel();
            itemModel.setId(items.get(i).getId() + "");
            itemModel.setAmount(items.get(i).getAmount());
            itemModel.setCode(items.get(i).getCode());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setMin_price(items.get(i).getMin_price());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setName(items.get(i).getName());
            itemModel.setType(items.get(i).getType());
            itemModel.setPer_user_limit(items.get(i).getPer_user_limit());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setExpires_at(items.get(i).getExpires_at());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setCreated_at(items.get(i).getCreated_at());

            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


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