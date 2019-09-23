/*
package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import androidx.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetMessageDataThreadsMessages;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.adapter.MessageDetailAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityMessageDetailBinding;
import com.neon.lms.model.MessageModel;

import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private MessageModel model;
    private ActivityMessageDetailBinding binding;

    String threadId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message_detail);
        model = new MessageModel();
        model.setMessages(new ArrayList<NetMessageDataThreadsMessages>());
        binding.setMessageModel(model);

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
        threadId = getIntent().getStringExtra(getString(R.string.id));
        binding.txtSend.setOnClickListener(this);
        ArrayList<NetMessageDataThreadsMessages> models = getIntent().getParcelableArrayListExtra(getString(R.string.detail));
        if (models != null) {
            model.setMessages(models);
        } else {
            model.setMessages(new ArrayList<NetMessageDataThreadsMessages>());
        }

        initRecycler();


    }


    */
/*
     *  initialize Reacycler view
     *//*

    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MessageDetailAdapter(MessageDetailActivity.this,
                model.getMessages(), new OnRecyclerItemClick() {
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
            case R.id.txtSend:
                if (binding.etChatMessage.getText().length() > 0)
                    replayMessage();
                break;

        }
    }

    //    replay Message api call
    public void replayMessage() {

        RetrofitClient.getInstance().getRestOkClient().
                replayMassage(threadId,
                        binding.etChatMessage.getText().toString(),
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netAboutData = (NetSuccess) object;
            if (netAboutData != null) {
                NetMessageDataThreadsMessages itemModel = new NetMessageDataThreadsMessages();
                itemModel.setBody(binding.etChatMessage.getText().toString());
                model.getMessages().add(itemModel);
                binding.recyclerView.getAdapter().notifyDataSetChanged();

                binding.etChatMessage.setText("");


            } else {
                Toast.makeText(MessageDetailActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(MessageDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


}
*/
