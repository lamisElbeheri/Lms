package com.neon.lms.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.adapter.OrderListAdapter;
import com.neon.lms.basecomponent.BaseFragment;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.FragmentOrderlistBinding;
import com.neon.lms.model.OrderListModel;
import com.neon.lms.model.OrderModel;

import java.util.ArrayList;


public class OrderListFragment extends BaseFragment {
    public static final String TAG = "Home";
    private OrderListModel model;
    private FragmentOrderlistBinding binding;



    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orderlist, container, false);
        model = new OrderListModel();
        model.setArrayList(new ArrayList<OrderModel>());
        binding.setOrderListModel(model);
        
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
        binding.recyclerView.setAdapter(new OrderListAdapter(getContext(),
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(final int position, int type) {

            }
        }));
    }



}

