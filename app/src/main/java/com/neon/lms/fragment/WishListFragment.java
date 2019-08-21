package com.neon.lms.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.adapter.WishListAdapter;
import com.neon.lms.basecomponent.BaseFragment;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.FragmentWishlistBinding;
import com.neon.lms.model.WishListModel;
import com.neon.lms.model.WishModel;

import java.util.ArrayList;


public class WishListFragment extends BaseFragment {
    public static final String TAG = "Home";
    private WishListModel model;
    private FragmentWishlistBinding binding;



    public WishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist, container, false);
        model = new WishListModel();
        model.setArrayList(new ArrayList<WishModel>());
        binding.setWishListModel(model);
        
        initView();


        return binding.getRoot();

    }

    private void initView() {
        initrecyclerView();

    }


    /*
     *  initialize Reacycler view
     */
    private void initrecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        binding.recyclerView.setAdapter(new WishListAdapter(getContext(),
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(final int position, int type) {

            }
        }));
    }



}

