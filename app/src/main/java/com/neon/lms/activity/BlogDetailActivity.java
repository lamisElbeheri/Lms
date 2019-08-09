package com.neon.lms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetBlogDetailData;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.databinding.ActivityBlogdetailBinding;
import com.neon.lms.model.BlogDetailModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.squareup.picasso.Picasso;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class BlogDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String BLOG_ID = "blogId";
    private BlogDetailModel model;
    private ActivityBlogdetailBinding binding;

    String id;
    String next;
    String  privious;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blogdetail);
        model = new BlogDetailModel();
        binding.setBlogDetailModel(model);

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
        id = getIntent().getStringExtra(BLOG_ID);
        binding.included.imgBack.setOnClickListener(this);
        binding.next.setOnClickListener(this);
        binding.privious.setOnClickListener(this);

        blogDetailApi();

    }

    public void blogDetailApi() {
        RetrofitClient.getInstance().getRestOkClient().
                getBlogDetailList(id,
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {

            NetBlogDetailData netBlogDetailData = (NetBlogDetailData) object;
            if (netBlogDetailData.getStatus().equalsIgnoreCase("success")) {
                binding.blogTitle.setText(netBlogDetailData.getBlog().getTitle());
                binding.blogDes.setText(netBlogDetailData.getBlog().getContent());
                Picasso.with(BlogDetailActivity.this)
                        .load(netBlogDetailData.getBlog().getImage())
                        .into(binding.blogImg);
                binding.blogdate.setText(AppConstant.getDate(netBlogDetailData.getBlog().getCreated_at(), AppConstant.sdfFormateDate));
                binding.author.setText(netBlogDetailData.getBlog().getAuthor().getFirst_name());
                next = String.valueOf(netBlogDetailData.getNext());
                privious = String.valueOf(netBlogDetailData.getPrevious());


            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


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

            case R.id.next:
                if (!next.equalsIgnoreCase(null)) {
                    Intent intent = new Intent(BlogDetailActivity.this, BlogDetailActivity.class);
                    intent.putExtra(BlogDetailActivity.BLOG_ID, next + "");
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);
                    finish();
                }

                break;
            case R.id.privious:
                if (!privious.equalsIgnoreCase(null)) {
                    Intent preintent = new Intent(BlogDetailActivity.this, BlogDetailActivity.class);
                    preintent.putExtra(BlogDetailActivity.BLOG_ID, privious + "");
                    startActivity(preintent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    finish();

                }
                break;

        }
    }


}
