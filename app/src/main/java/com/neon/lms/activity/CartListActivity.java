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
import com.neon.lms.ResponceModel.NetCartList;
import com.neon.lms.ResponceModel.NetCartListResult;
import com.neon.lms.ResponceModel.NetCartListResultBundles;
import com.neon.lms.ResponceModel.NetCartListResultCourses;
import com.neon.lms.ResponceModel.NetFaqData;
import com.neon.lms.ResponceModel.NetFaqDataResultData;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.adapter.CartListAdapter;
import com.neon.lms.adapter.ShopListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityCartlistBinding;
import com.neon.lms.databinding.ActivityShoplistBinding;
import com.neon.lms.model.CartListModel;
import com.neon.lms.model.CartListModel;
import com.neon.lms.model.CartModel;
import com.neon.lms.model.FaqModel;
import com.neon.lms.model.ShopModel;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CartListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private CartListModel model;
    private ActivityCartlistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cartlist);
        model = new CartListModel();
        model.setArrayList(new ArrayList<CartModel>());
        binding.setCartListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.cart));

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
        cartListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CartListAdapter(CartListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                switch (type) {
                    case Constants.DELETE:
                        removeFromCart(model.getArrayList().get(position).getId(),
                                model.getArrayList().get(position).isCourse());

                }


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


    public void cartListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        model.getArrayList().clear();
        RetrofitClient.getInstance().getRestOkClient().
                getCartList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetCartList cartList = (NetCartList) object;
            if (cartList.getStatus().equalsIgnoreCase("success")) {
                fillBundleArrayList(cartList.getResult().getBundles());
                fillArrayList(cartList.getResult().getCourses());


            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            binding.progressBar.setVisibility(View.VISIBLE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetCartListResultCourses> items) {

        CartModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CartModel();
            itemModel.setId(items.get(i).getId() + "");
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setFree(items.get(i).getFree());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setCourse(true);

            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }

    private void fillBundleArrayList(List<NetCartListResultBundles> items) {
        binding.progressBar.setVisibility(View.GONE);


        CartModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CartModel();
            itemModel.setId(items.get(i).getId() + "");
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setFree(items.get(i).getFree());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setCourse(false);

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

    public void removeFromCart(String id, boolean type) {

        RetrofitClient.getInstance().getRestOkClient().
                removeCart(type ? "course" : "bundle",
                        id,
                        callbackRemove);
    }

    private final retrofit.Callback callbackRemove = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CartListActivity.this, "Item Remove From Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartListActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


}
