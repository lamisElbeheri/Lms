package com.neon.lms.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetSingleLession;
import com.neon.lms.adapter.LessionListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityLessionlistBinding;
import com.neon.lms.model.LessionListModel;
import com.neon.lms.model.LessionModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LessionListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private LessionListModel model;
    private ActivityLessionlistBinding binding;
    SimpleExoPlayer player;
    public static final String LESSION_ID ="lessionId";

String lessionId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lessionlist);
        model = new LessionListModel();
        model.setArrayList(new ArrayList<LessionModel>());
        binding.setLessionListModel(model);

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
         lessionId =getIntent().getStringExtra(LESSION_ID);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);

        initRecycler();
        singleLessionDetail();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(LessionListActivity.this, trackSelector);
        binding.player.setPlayer(player);




    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LessionListAdapter(LessionListActivity.this,
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
    public void singleLessionDetail() {
        RetrofitClient.getInstance().getRestOkClient().
                getSingleLession(lessionId,
                        courseCallback);
    }

    private final retrofit.Callback courseCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSingleLession netSingleLession = (NetSingleLession) object;
            if (netSingleLession.getStatus().equalsIgnoreCase("success")) {
                binding.txtTitle.setText(netSingleLession.getResult().getLesson().getTitle());
                binding.txtDes.setText(netSingleLession.getResult().getLesson().getFull_text());
//                fillArrayList(netSingleLession.getResult().getLesson());

                // Produces DataSource instances through which media data is loaded.

                if (netSingleLession.getResult().getLesson().getMedia_video() !=null){
//                if (netSingleLession.getResult().getLesson().getMedia_video().getType().equalsIgnoreCase("youtube")) {
//

//                }
//                else {
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(LessionListActivity.this,
                            Util.getUserAgent(LessionListActivity.this, getResources().getString(R.string.app_name)));
                    // This is the MediaSource representing the media to be played.
                    MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse("https://vimeo.com/218815513.mp4"));
                    // Prepare the player with the source.
                    player.prepare(videoSource);
                    player.setPlayWhenReady(true);
                    player.addListener(new Player.DefaultEventListener() {
                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                            if (playWhenReady) {
//                    progressBar.setVisibility(View.GONE);
                            }
                            super.onPlayerStateChanged(playWhenReady, playbackState);
                        }
                    });
                }
            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);

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

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        super.onDestroy();
    }




}
