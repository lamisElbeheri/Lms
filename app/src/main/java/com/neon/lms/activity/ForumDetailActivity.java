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
import com.neon.lms.adapter.ForumDetailAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityForumdetailBinding;
import com.neon.lms.model.ForumDetailListModel;
import com.neon.lms.model.ForumDetailModel;

import java.util.ArrayList;

public class ForumDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private ForumDetailListModel model;
    private ActivityForumdetailBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forumdetail);
        model = new ForumDetailListModel();
        model.setArrayList(new ArrayList<ForumDetailModel>());
        binding.setForumDetailListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.forums));

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

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        binding.recyclerView.setAdapter(new ForumDetailAdapter(ForumDetailActivity.this,
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

        }
    }





}