package com.neon.lms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetSingleCourseData;
import com.neon.lms.ResponceModel.NetSingleCourseDataResultCourse_timeline;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.adapter.CourseDetailAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityCoursedetailBinding;
import com.neon.lms.db.CartDbAdapter;
import com.neon.lms.model.CourseDetailListModel;
import com.neon.lms.model.CourseDetailModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private CourseDetailListModel model;
    private ActivityCoursedetailBinding binding;

    int freeCourse;
    String courseId;

    CartDbAdapter dbAdapter;

    boolean isPurchase;
    String title;
    String img;
    String des;
    String  price;
    String  courseType;
    String cretaedAt;
    String updatedAt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coursedetail);
        model = new CourseDetailListModel();
        model.setArrayList(new ArrayList<CourseDetailModel>());
        binding.setCourseDetailListModel(model);

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
        dbAdapter =new CartDbAdapter(CourseDetailActivity.this);
        freeCourse = getIntent().getIntExtra("isfree", 0);
        courseId = getIntent().getStringExtra("id");
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        binding.addTocart.setOnClickListener(this);

        initRecycler();
        singleCourseDetail();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CourseDetailAdapter(CourseDetailActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(CourseDetailActivity.this, LessionListActivity.class);
                intent.putExtra(LessionListActivity.LESSION_ID, model.getArrayList().get(position).getId() + "  ");
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
            case R.id.addTocart:
                if (freeCourse == 1)
                    getFreeCourse();
                else
                    AddtocartLocaly();
                break;

        }
    }



    public void AddtocartLocaly() {
        try {
            dbAdapter.open();
            dbAdapter.insUpdate(courseId,title,img,"",des,"course",
                    price,"",cretaedAt,updatedAt);
            dbAdapter.close();
            Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void AddtocartApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                addtocartApi("course",
                        courseId,
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            binding.progressBar.setVisibility(View.GONE);

            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CourseDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    public void getFreeCourse() {
        RetrofitClient.getInstance().getRestOkClient().
                getFreeCourse(
                        courseId,
                        freecallback);
    }

    private final retrofit.Callback freecallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CourseDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    public void singleCourseDetail() {
        RetrofitClient.getInstance().getRestOkClient().
                getSingleCourse(courseId,
                        courseCallback);
    }

    private final retrofit.Callback courseCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSingleCourseData netSingleCourseData = (NetSingleCourseData) object;
            if (netSingleCourseData.getStatus().equalsIgnoreCase("success")) {
                binding.title.setText(netSingleCourseData.getResult().getCourse().getTitle());
                binding.descriptiom.setText(netSingleCourseData.getResult().getCourse().getDescription());
                binding.price.setText(netSingleCourseData.getResult().getCourse().getPrice()+"");

                isPurchase = netSingleCourseData.getResult().getPurchased_course();
                title = netSingleCourseData.getResult().getCourse().getTitle();
                img = netSingleCourseData.getResult().getCourse().getImage();
                des = netSingleCourseData.getResult().getCourse().getDescription();
                cretaedAt = netSingleCourseData.getResult().getCourse().getCreated_at();
                updatedAt = netSingleCourseData.getResult().getCourse().getUpdated_at();
                price = netSingleCourseData.getResult().getCourse().getPrice();

                if (isPurchase)
                    binding.addTocart.setVisibility(View.GONE);
                fillArrayList(netSingleCourseData.getResult().getCourse_timeline());


            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);

        }
    };

    /*set language list*/

    private void fillArrayList(List<NetSingleCourseDataResultCourse_timeline> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        CourseDetailModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CourseDetailModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setCompleted(items.get(i).getCompleted());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setType(items.get(i).getType());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }




}
