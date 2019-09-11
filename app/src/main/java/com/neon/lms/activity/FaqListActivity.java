package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetFaqData;
import com.neon.lms.ResponceModel.NetFaqDataResultData;
import com.neon.lms.adapter.FaqListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityFaqlistBinding;
import com.neon.lms.model.FaqListModel;
import com.neon.lms.model.FaqModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FaqListActivity extends BaseActivity implements View.OnClickListener {

    private FaqListModel model;
    private ActivityFaqlistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqlist);
        model = new FaqListModel();
        model.setArrayList(new ArrayList<FaqModel>());
        binding.setFaqListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.faq));
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
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        faqListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new FaqListAdapter(FaqListActivity.this,
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

    public void faqListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getRestOkClient().
                getFaqListApi("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetFaqData faqData = (NetFaqData) object;
            binding.progressBar.setVisibility(View.GONE);

            if (faqData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(faqData.getResult().getData());


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

    private void fillArrayList(List<NetFaqDataResultData> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        FaqModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new FaqModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setQuestion(items.get(i).getQuestion());
            itemModel.setAnswer(items.get(i).getAnswer());
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
