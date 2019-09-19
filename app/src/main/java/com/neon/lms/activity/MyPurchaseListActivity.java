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
import com.neon.lms.ResponceModel.NetMyPurchaseData;
import com.neon.lms.ResponceModel.NetMyPurchaseDataResultBundles;
import com.neon.lms.ResponceModel.NetMyPurchaseDataResultCourses;
import com.neon.lms.adapter.MyPurchaseListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityMypurchaselistBinding;
import com.neon.lms.model.MyPurchaseListModel;
import com.neon.lms.model.MyPurchaseModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyPurchaseListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private MyPurchaseListModel model;
    private ActivityMypurchaselistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypurchaselist);
        model = new MyPurchaseListModel();
        model.setArrayList(new ArrayList<MyPurchaseModel>());
        binding.setMyPurchaseListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.myPurchase));
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
       myPurchaseApi("");
        initRecycler();

    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MyPurchaseListAdapter(MyPurchaseListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(MyPurchaseListActivity.this,CourseDetailActivity.class);
                intent.putExtra("isfree",model.getArrayList().get(position).getFree());
                intent.putExtra("id",model.getArrayList().get(position).getId()+"");
                startActivity(intent);
                overridePendingTransition(R.anim.animation, R.anim.animation2);



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


    public void myPurchaseApi(String type) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                getMyPurchase("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetMyPurchaseData netMyPurchaseData = (NetMyPurchaseData) object;
            if (netMyPurchaseData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netMyPurchaseData.getResult().getBundles());
                fillArrayListCourse(netMyPurchaseData.getResult().getCourses());
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

    private void fillArrayList(List<NetMyPurchaseDataResultBundles> items) {
        binding.progressBar.setVisibility(View.GONE);


        MyPurchaseModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new MyPurchaseModel();
            itemModel.setId(items.get(i).getId()+"");
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setFree(items.get(i).getFree());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setCourse(false);

            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }

    private void fillArrayListCourse(List<NetMyPurchaseDataResultCourses> items) {
        binding.progressBar.setVisibility(View.GONE);


        MyPurchaseModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new MyPurchaseModel();
            itemModel.setId(items.get(i).getId()+"");
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setFree(items.get(i).getFree());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setCourse(true);

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
