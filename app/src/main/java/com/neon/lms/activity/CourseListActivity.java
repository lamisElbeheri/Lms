package com.neon.lms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetCourseData;
import com.neon.lms.ResponceModel.NetCourseDataResultData;
import com.neon.lms.adapter.CourseListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityCourselistBinding;
import com.neon.lms.model.CourseListModel;
import com.neon.lms.model.CourseModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private CourseListModel model;
    private ActivityCourselistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courselist);
        model = new CourseListModel();
        model.setArrayList(new ArrayList<CourseModel>());
        binding.setCourseListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.course));
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
        if (getIntent().getStringExtra(VALUE).equalsIgnoreCase("trending")){
            binding.llFilter.setVisibility(View.GONE);
        }
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courseSort, R.layout.simple_spiner);
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);
        binding.spinner.setAdapter(adapter);
        courseApi("");
        initRecycler();

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position==1)
                    courseApi("popular");
                else if (position==2)
                    courseApi("trending");
                else if (position==3)
                    courseApi("featured");
                else
                    courseApi("");

                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CourseListAdapter(CourseListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(CourseListActivity.this,CourseDetailActivity.class);
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


    public void courseApi(String type) {
        binding.progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.getInstance().getRestOkClient().
                getCourseListApi(type,
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetCourseData netCourseData = (NetCourseData) object;
            if (netCourseData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netCourseData.getResult().getData());


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

    private void fillArrayList(List<NetCourseDataResultData> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        CourseModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CourseModel();
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setId(items.get(i).getId());
            itemModel.setTrending(items.get(i).getTrending());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setFree(items.get(i).getFree());
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
