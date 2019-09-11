package com.neon.lms.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neon.lms.BaseAppClass;
import com.neon.lms.R;
import com.neon.lms.ResponceModel.NetCartList;
import com.neon.lms.ResponceModel.NetCartListResultBundles;
import com.neon.lms.ResponceModel.NetCartListResultCourses;
import com.neon.lms.ResponceModel.NetSuccess;
import com.neon.lms.adapter.CartListAdapter;
import com.neon.lms.basecomponent.BaseActivity;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.ActivityCartlistBinding;
import com.neon.lms.db.CartDbAdapter;
import com.neon.lms.model.CartListModel;
import com.neon.lms.model.CartModel;
import com.neon.lms.net.GetPromoSyncTask;
import com.neon.lms.net.OnApiCalled;
import com.neon.lms.net.RetrofitClient;
import com.neon.lms.util.AppConstant;
import com.neon.lms.util.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CartListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private CartListModel model;
    private ActivityCartlistBinding binding;

    CartDbAdapter dbAdapter;
    private GetPromoSyncTask getPromoSyncTask;
    String total;


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
        binding.txtPromoCode.setOnClickListener(this);
        binding.txtApply.setOnClickListener(this);
        dbAdapter = new CartDbAdapter(CartListActivity.this);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        binding.btnCheckout.setOnClickListener(this);
        initRecycler();
//        clearCArtList();
        fillArrayList();

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
                        removeLocaly(model.getArrayList().get(position).getId());
                        model.getArrayList().remove(position);
                        binding.recyclerView.getAdapter().notifyDataSetChanged();
//                        removeFromCart(model.getArrayList().get(position).getId(),
//                                model.getArrayList().get(position).isCourse());

                }


            }
        }));


    }


    public void cartListApi() {
        model.getArrayList().clear();
        RetrofitClient.getInstance().getRestOkClient().
                getCartList("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {

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

            case R.id.txtApply:
                if (binding.edtpromoCode.getText().toString().length() > 0)
                    callPromocodeApi();
                else
                    Toast.makeText(this, getString(R.string.promoMessage), Toast.LENGTH_SHORT).show();
                break;

            case R.id.txtPromoCode:
                startActivity(new Intent(this, OfferListActivity.class));
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                break;
            case R.id.btnCheckout:
                Intent buyIntent = new Intent(CartListActivity.this, PaymentOptionActivity.class);
                buyIntent.putExtra("total", total);
                startActivity(buyIntent);
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

    public void clearCArtList() {

        RetrofitClient.getInstance().getRestOkClient().
                clearCart("",
                        callbackClear);
    }

    private final retrofit.Callback callbackClear = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CartListActivity.this, "Cart Clear", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartListActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    private void fillArrayList() {
        model.getArrayList().clear();
        try {
            dbAdapter.open();
            model.getArrayList().addAll(dbAdapter.getCartItem());
            total =dbAdapter.getTotalPrice();
            binding.txtTotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + dbAdapter.getTotalPrice() + "");
            binding.txtsubtotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + dbAdapter.getTotalPrice() + "");
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        binding.recyclerView.getAdapter().notifyDataSetChanged();

    }

    private void removeLocaly(String id) {
        try {
            dbAdapter.open();
            dbAdapter.deleteItem(id);
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void callPromocodeApi() {
        getPromoSyncTask = new GetPromoSyncTask(CartListActivity.this, binding.edtpromoCode.getText().toString(), true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {
                try {
//                    fillArrayList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                model.setApiCallActive(false);
            }

            @Override
            public void onError(String strError) {
                model.setApiCallActive(false);
            }
        });
    }
}
