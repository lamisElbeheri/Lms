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
import com.neon.lms.ResponceModel.NetMessageData;
import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.adapter.BlogListAdapter;
import com.neon.lms.adapter.CourseListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityBloglistBinding;
import com.neon.lms.databinding.ActivityCourselistBinding;
import com.neon.lms.model.BlogListModel;
import com.neon.lms.model.BlogListModel;
import com.neon.lms.model.BlogModel;
import com.neon.lms.model.CourseModel;
import com.neon.lms.model.MessageModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class BlogListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private BlogListModel model;
    private ActivityBloglistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bloglist);
        model = new BlogListModel();
        model.setArrayList(new ArrayList<BlogModel>());
        binding.setBlogListModel(model);

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
        binding.recyclerView.setAdapter(new BlogListAdapter(BlogListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(BlogListActivity.this, BlogDetailActivity.class);
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


    public void blogListApi() {
        model.getArrayList().clear();
        RetrofitClient.getInstance().getRestOkClient().
                getBlogList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {

            NetBlogData messageData = (NetBlogData) object;
            if (messageData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(messageData.getBlog().getData());


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

    private void fillArrayList(List<NetBlogDataBlogData> items) {
        binding.progressBar.setVisibility(View.GONE);


        BlogModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new BlogModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setAuthor(items.get(i).getAuthor());
            itemModel.setBlog_author(items.get(i).getBlog_author());
            itemModel.setBlog_category(items.get(i).getBlog_category());
            itemModel.setBlog_image(items.get(i).getBlog_image());
            itemModel.setCategory(items.get(i).getCategory());
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setViews(items.get(i).getViews());
            itemModel.setContent(items.get(i).getContent());

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
