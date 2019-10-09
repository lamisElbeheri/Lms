
package com.neon.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.adapter.UserListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityUserlistBinding;
import com.neon.lms.model.UserListModel;
import com.neon.lms.model.UserModel;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserListActivity extends BaseActivity implements View.OnClickListener {


    public static final String TITLE = "title";
    private UserListModel model;
    private ActivityUserlistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userlist);
        model = new UserListModel();
        model.setArrayList(new ArrayList<UserModel>());
        binding.setUserListModel(model);

    }

    @Override
    public void setToolBar() {

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
        ArrayList<UserModel> models = getIntent().getParcelableArrayListExtra(getString(R.string.detail));
        if (models != null) {
            model.setArrayList(models);
        } else {
            model.setArrayList(new ArrayList<UserModel>());
        }

        initRecycler();


    }


    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new UserListAdapter(UserListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(UserListActivity.this, MessageDetailActivity.class);
                intent.putExtra(MessageDetailActivity.TITLE, model.getArrayList().get(position).getFull_name() + "");
                intent.putExtra(getString(R.string.id), model.getArrayList().get(position).getId() + "");
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


        }
    }


}

