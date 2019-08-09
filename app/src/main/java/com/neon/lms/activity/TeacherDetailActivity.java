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
import com.neon.lms.ResponceModel.NetTeacherDetailData;
import com.neon.lms.adapter.TeacherSpecialistAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityTeacherDetailBinding;
import com.neon.lms.model.TeacherSpeciaListModel;
import com.neon.lms.model.TeacherSpecialModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private TeacherSpeciaListModel model;
    private ActivityTeacherDetailBinding binding;

    public static final String TEACHER_ID ="id";
    String teacherId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_detail);
        model = new TeacherSpeciaListModel();
        model.setArrayList(new ArrayList<TeacherSpecialModel>());
        binding.setTeacherSpeciaListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.teachers));

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
        teacherId =getIntent().getStringExtra(TEACHER_ID);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        getTeacherDetailApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(new TeacherSpecialistAdapter(TeacherDetailActivity.this,
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


    public void getTeacherDetailApi() {
        RetrofitClient.getInstance().getRestOkClient().
                getTeacherDetail(
                        "2",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetTeacherDetailData netTeacherDetailData = (NetTeacherDetailData) object;
            if (netTeacherDetailData.getStatus().equalsIgnoreCase("success")) {
                binding.fullName.setText(netTeacherDetailData.getResult().getTeacher().getFull_name());
                binding.txtAddress.setText(netTeacherDetailData.getResult().getTeacher().getAddress());
                binding.txtNum.setText(netTeacherDetailData.getResult().getTeacher().getPhone());
                binding.txtEmail.setText(netTeacherDetailData.getResult().getTeacher().getEmail());
                Picasso.with(TeacherDetailActivity.this)
                        .load(netTeacherDetailData.getResult().getTeacher().getImage())
                        .into(binding.teacherImage);
            } else {

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

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
