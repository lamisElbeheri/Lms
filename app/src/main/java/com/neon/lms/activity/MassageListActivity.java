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
import com.neon.lms.ResponceModel.NetMessageData;
import com.neon.lms.ResponceModel.NetMessageDataThreads;
import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.adapter.MessageListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityMessagelistBinding;
import com.neon.lms.model.MessageListModel;
import com.neon.lms.model.MessageModel;
import com.neon.lms.net.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MassageListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private MessageListModel model;
    private ActivityMessagelistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messagelist);
        model = new MessageListModel();
        model.setArrayList(new ArrayList<MessageModel>());
        binding.setMessageListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.message));

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
        messageListApi();

    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MessageListAdapter(MassageListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(MassageListActivity.this,MessageDetailActivity.class);
                intent.putExtra(getString(R.string.id),model.getArrayList().get(position).getPivot().getThread_id());
                intent.putParcelableArrayListExtra(getString(R.string.detail), model.getArrayList().get(position).getMessages());
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

    public void messageListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        RetrofitClient.getInstance().getRestOkClient().
                getMessageList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetMessageData messageData = (NetMessageData) object;
            if (messageData.getStatus().equalsIgnoreCase("success")) {
                for (int i = 0; i <messageData.getThreads().size() ; i++) {
                    model.setThread_id(messageData.getThreads().get(i).getId());
                    fillArrayList(messageData.getThreads());

                }


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

    private void fillArrayList(List<NetMessageDataThreads> items) {
        binding.progressBar.setVisibility(View.GONE);


        MessageModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new MessageModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setMessages(items.get(i).getMessages());
            itemModel.setPivot(items.get(i).getPivot());

            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


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
