package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetWhyusData;
import com.neon.lms.ResponceModel.NetWhyusDataResultData;
import com.neon.lms.adapter.WhyusListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityWhyuslistBinding;
import com.neon.lms.model.WhyusListModel;
import com.neon.lms.model.WhyusModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class WhyusListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private WhyusListModel model;
    private ActivityWhyuslistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_whyuslist);
        model = new WhyusListModel();
        model.setArrayList(new ArrayList<WhyusModel>());
        binding.setWhyusListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.why)+" "+getString(R.string.us));
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
        courseApi();
        initRecycler();


    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new WhyusListAdapter(WhyusListActivity.this,
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


    public void courseApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                getWhyUsList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetWhyusData netWhyusData = (NetWhyusData) object;
            if (netWhyusData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netWhyusData.getResult().getData());


            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            binding.progressBar.setVisibility(View.VISIBLE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetWhyusDataResultData> items) {
        model.getArrayList().clear();


        WhyusModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new WhyusModel();
            itemModel.setContent(items.get(i).getContent());
            itemModel.setId(items.get(i).getId());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setIcon(items.get(i).getIcon());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setStatus(items.get(i).getStatus());
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
