package com.neon.lms.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neon.lms.R;
import com.neon.lms.callBack.OnRecyclerItemClick;
import com.neon.lms.databinding.RowOrderDetailItemBinding;
import com.neon.lms.databinding.RowOrderItemBinding;
import com.neon.lms.model.OrderModel;
import com.neon.lms.util.Constants;

import java.util.ArrayList;

public class OrderListDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<OrderModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public OrderListDetailAdapter(Context context, ArrayList<OrderModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
//        h.binding.setOrderModel(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowOrderDetailItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }


        }
    }


}
